package com.model2.mvc.service.product.test;

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
import com.model2.mvc.service.product.ProductService;

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
public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

/*	@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
	
		product.setProdName("testProdName");
		String manuDate = "2024-03-19";
		product.setManuDate(manuDate.replaceAll("-",""));
		product.setPrice(Integer.parseInt("1111"));
		product.setProdDetail("testProdDetail");
		Date currentDate = new Date(System.currentTimeMillis());
		product.setRegDate(new java.sql.Date(currentDate.getTime()));
		product.setFileName("testgood");
		
		productService.addProduct(product);
		
		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
	
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals(Integer.parseInt("1111"), product.getPrice());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testgood", product.getFileName());
		Assert.assertEquals(new java.sql.Date(currentDate.getTime()), product.getRegDate());
	}

	@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		//==> �ʿ��ϴٸ�...
		product.setProdNo(10011);
		product.setProdName("testProdName");
		String manuDate = "2024-03-19";
		product.setManuDate(manuDate.replaceAll("-",""));
		product.setPrice(Integer.parseInt("1111"));
		product.setProdDetail("testProdDetail");
		Date currentDate = new Date(System.currentTimeMillis());
		product.setRegDate(new java.sql.Date(currentDate.getTime()));
		product.setFileName("testgood");
		

		//==> console Ȯ��
		System.out.println(product);
		
		//==> API Ȯ��
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals(Integer.parseInt("1111"), product.getPrice());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("testgood", product.getFileName());
		Assert.assertEquals(new java.sql.Date(currentDate.getTime()), product.getRegDate());

	}
	
	@Test
	 public void testUpdateProduct() throws Exception{
		 
		Product product = productService.getProduct(Integer.parseInt("10011"));
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals(Integer.parseInt("1111"), product.getPrice());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20240319", product.getManuDate());
		Assert.assertEquals("testgood", product.getFileName());

		product.setProdName("change");
		product.setPrice(Integer.parseInt("2222"));
		product.setManuDate("20240304");
		product.setProdDetail("change");
		product.setFileName("changefile");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(Integer.parseInt("10011"));
		Assert.assertNotNull(product);
		
		//==> console Ȯ��
		System.out.println(product);
			
		//==> API Ȯ��
		Assert.assertEquals("change", product.getProdName());
		Assert.assertEquals(Integer.parseInt("2222"), product.getPrice());
		Assert.assertEquals("20240304", product.getManuDate());
		Assert.assertEquals("change", product.getProdDetail());
		Assert.assertEquals("changefile", product.getFileName());
	 
	}
	*/
	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 @Test
	 public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	//System.out.println(list);
	 	System.out.println(list);
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	
	 //@Test
	 public void testGetProductListByProdNo() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("10001");
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(1, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	System.out.println(list);
	 	System.out.println("=======================================");
	 	
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword(""+System.currentTimeMillis());
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(0, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
	/* @Test
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