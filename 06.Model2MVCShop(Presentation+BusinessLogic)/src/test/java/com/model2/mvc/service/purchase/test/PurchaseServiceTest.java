package com.model2.mvc.service.purchase.test;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-*.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

//	@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		Product product = new Product();
		User user = new User();
		
		user.setUserId("user19");
		purchase.setBuyer(user);
		product.setProdNo(10001);
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption("1");
		purchase.setReceiverName("������");
		purchase.setReceiverPhone("010-3422-4660");
		purchase.setDivyAddr("���� ����");
		purchase.setDivyRequest("������");
		purchase.setDivyDate("2024-03-24");		
		purchase.setTranCode("1");
		
		purchaseService.addPurchase(purchase);
		
		//==> console Ȯ��
		System.out.println(purchase);
		
		//==> API Ȯ��
	
		Assert.assertEquals("1", purchase.getPaymentOption());
		Assert.assertEquals("������", purchase.getReceiverName());
		Assert.assertEquals("010-3422-4660", purchase.getReceiverPhone());
		Assert.assertEquals("���� ����", purchase.getDivyAddr());
		Assert.assertEquals("������", purchase.getDivyRequest());
		Assert.assertEquals("2024-03-24", purchase.getDivyDate());
	
	}

	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(10007);
		
		//==> console Ȯ��
		System.out.println(purchase);
		
		//==> API Ȯ��
		
		Assert.assertEquals("1", purchase.getPaymentOption().trim());
		Assert.assertEquals("������", purchase.getReceiverName());
		Assert.assertEquals("010-3422-4660", purchase.getReceiverPhone());
		Assert.assertEquals("���� ����", purchase.getDivyAddr());
		Assert.assertEquals("������", purchase.getDivyRequest());
		Assert.assertEquals("2024-03-24", purchase.getDivyDate());
	}

	//@Test
	 public void testUpdatePurchase() throws Exception{
		
		System.out.println("start");
		Purchase purchase = purchaseService.getPurchase(10007);
		System.out.println(purchase);
		User user = new User();
				
		
		Assert.assertEquals("user19", purchase.getBuyer().getUserId());
		Assert.assertEquals("������", purchase.getReceiverName());
		Assert.assertEquals("010-3422-4660", purchase.getReceiverPhone());
		Assert.assertEquals("���� ����", purchase.getDivyAddr());
		Assert.assertEquals("������", purchase.getDivyRequest());
		Assert.assertEquals("2024-03-24", purchase.getDivyDate());

		user.setUserId("user19");
		purchase.setBuyer(user);
		purchase.setReceiverName("change");
		purchase.setReceiverPhone("010-7191-1225");
		purchase.setDivyAddr("��õ ������");
		purchase.setDivyRequest("change");
		purchase.setDivyDate("2024-03-04");
		
		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(Integer.parseInt("10001"));
		Assert.assertNotNull(purchase);
		
		//==> console Ȯ��
		System.out.println(purchase);
			
		//==> API Ȯ��
		Assert.assertEquals("change", purchase.getReceiverName());
		Assert.assertEquals("010-7191-1225",purchase.getReceiverPhone());
		Assert.assertEquals("��õ ������", purchase.getDivyAddr());
		Assert.assertEquals("change", purchase.getDivyRequest());
		Assert.assertEquals("2024-03-04", purchase.getDivyDate());
	 
	}
	
	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 @Test
	 public void testGetPurchaseListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = purchaseService.getPurchaseList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(2, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = purchaseService.getPurchaseList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	//System.out.println(list);
	 	System.out.println(list);
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 /*
	 //@Test
	 public void testGetUserListByUserId() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("admin");
	 	Map<String,Object> map = userService.getUserList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = userService.getUserList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	 @Test
	 public void testGetUserListByUserName() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword("SCOTT");
	 	Map<String,Object> map = userService.getUserList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("1");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = userService.getUserList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }	 */
}