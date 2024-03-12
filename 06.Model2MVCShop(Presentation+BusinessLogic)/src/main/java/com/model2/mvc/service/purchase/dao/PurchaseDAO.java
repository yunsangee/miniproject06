package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.*;


public class PurchaseDAO {
	
	public PurchaseDAO(){
	}

	public void insertPurchase(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction (tran_no, payment_option, receiver_name, receiver_phone, demailaddr, dlvy_request, dlvy_date, tran_status_code, prod_no, buyer_id, order_data) "
		           + "VALUES (seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate)";


		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setString(7, purchase.getTranCode());
		stmt.setInt(8, purchase.getPurchaseProd().getProdNo());
		stmt.setString(9, purchase.getBuyer().getUserId());
		
		stmt.executeUpdate();
		System.out.println("purchaseDAO insert :" + purchase );
		con.close();
	}
	public Product findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from Product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		System.out.println(prodNo);
		ResultSet rs = stmt.executeQuery();
		System.out.println("productdao"+prodNo);
		
		
		Product Product = new Product();
		while (rs.next()) {
			
			Product.setProdNo(prodNo);
			Product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			Product.setPrice(rs.getInt("PRICE"));
			Product.setProdDetail(rs.getString("PROD_DETAIL"));
			Product.setProdName(rs.getString("PROD_NAME"));
			Product.setRegDate(rs.getDate("reg_date"));
		}
		con.close();
		return Product;
	}
	
	public Purchase findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		System.out.println(tranNo);
		ResultSet rs = stmt.executeQuery();
		System.out.println("purchasedao findPurchase"+tranNo);
		
		
		Purchase purchase = new Purchase();
		while (rs.next()) {
			
			purchase.setTranNo(tranNo);
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
		}
		con.close();
		return purchase;
	}
public Map<String , Object> getPurchaseList(Search search) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		// Original Query 구성
		String sql = "select * from TRANSACTION";
				
		sql += " order by TRAN_NO";
		PreparedStatement stmt = con.prepareStatement(sql);
	
		
		System.out.println("PurchaseDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);

		List<Purchase> list = new ArrayList<Purchase>();
		while(rs.next()){
			Purchase purchase = new Purchase();
			User user = new User();
			
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			list.add(purchase);
		}
		System.out.println("list map"+ list);
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		System.out.println("listtest : "+map.put("list", list));
		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	public void updatePurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?, PROD_DETAIL=?, MANUFACTURE_DAY=?,PRICE=?, IMAGE_FILE=? where prod_No =?";
		System.out.println("업데이트" +purchase);
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getProdName());
		stmt.setString(2, purchase.getProdDetail());
		stmt.setString(3, purchase.getManuDate());
		stmt.setInt(4, purchase.getPrice());
		stmt.setString(5, purchase.getFileName());
		stmt.setInt(6, purchase.getProdNo());
		
		if(stmt.executeUpdate() > 0) {
			System.out.println("success");
		}else {
			System.out.println("fail");
		}
		
		System.out.println("업데이트" +purchase);
		con.close();
	}
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		System.out.println("ProductDAO :: make SQL :: "+search);	
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
