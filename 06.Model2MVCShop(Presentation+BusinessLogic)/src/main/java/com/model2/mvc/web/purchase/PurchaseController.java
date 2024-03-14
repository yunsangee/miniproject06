package com.model2.mvc.web.purchase;

import java.sql.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;



//==> ȸ������ Controller
@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method ���� ����
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping("/addPurchaseView.do")

	public String addProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {
	    System.out.println("/addPurchaseView.do");
	    System.out.println("prodNo: " + prodNo);

	    
        Product product = purchaseService.getPurchase02(prodNo);
    
        model.addAttribute("product", product);
	    model.addAttribute("tranNo", prodNo);

	    return "forward:/purchase/addPurchaseView.jsp";
	}

			
	@RequestMapping("/addPurchase.do")
	public String addPurchase( HttpServletRequest request,
							   @ModelAttribute("purchase") Purchase purchase, 
							   @RequestParam("prodNo") int prodNo,
							   @RequestParam("buyerId") String userId,
							   Model model) throws Exception {

		System.out.println("/addPurchase.do");
		
		//Business Logic
		HttpSession session=request.getSession();
		
	    User user = userService.getUser(userId);
	    Product product = productService.getProduct(prodNo);
	    purchase.setBuyer(user);
	    purchase.setPurchaseProd(product);
	    
	    purchaseService.addPurchase(purchase);	
	    
	    model.addAttribute("purchase",purchase);
		
		System.out.println("addpurchaseaction : "+purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping("/getPurchase.do")
	public String getPurchase(HttpServletRequest request,
			   @ModelAttribute("purchase") Purchase purchase, 
			   @RequestParam("tranNo") int tranNo,
			   Model model) throws Exception {
		
		System.out.println("/getPurchase.do");
		//Business Logic
		// Model �� View ����
		HttpSession session=request.getSession();
		String userId = (String)session.getAttribute("userId");
	    Product product = productService.getProduct(tranNo);
	  
	  
	    
	    purchaseService.getPurchase(tranNo);
	    purchase = purchaseService.getPurchase(tranNo);
	    model.addAttribute("purchase", purchase);
		
	    return "forward:/purchase/getPurchase.jsp";
        
	}

	@RequestMapping("/updatePurchaseView.do")
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo ,
									HttpServletRequest request,
									Model model ) throws Exception{
			
		System.out.println("/updatePurchaseView.do");
		//Business Logic
	
		Purchase purchase = purchaseService.getPurchase(tranNo);
		System.out.println(purchase);
		model.addAttribute("purchase", purchase);
		return "forward:/purchase/updatePurchaseView.jsp";
		//return null;
	}

	@RequestMapping("/updatePurchase.do")
	public String updatePurchase( HttpServletRequest request, 
								  @ModelAttribute("purchase") Purchase purchase ,
								  Model model ) throws Exception{

		System.out.println("/updatePurchase.do");
		//Business Logic
		HttpSession session=request.getSession();
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setPurchaseProd(new Product());
		
		
		purchaseService.updatePurchase(purchase);
		purchase = purchaseService.getPurchase(purchase.getTranNo());
		System.out.println(request.getParameter("regDate"));
		//purchase.setOrderDate(Date.valueOf(request.getParameter("regDate")));
		//Product getProd = productService.getProduct(tranNo);
		System.out.println("prodno test: " + purchase);
		purchase.setPurchaseProd(productService.getProduct(purchase.getPurchaseProd().getProdNo()));
		System.out.println("/updatePurchase.do");
		//Business Logic
		// Model �� View ����
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}

	@RequestMapping("/listPurchase.do")
	public String listPurchase( @ModelAttribute("Product") Product product,
								@ModelAttribute("search") Search search , 
								Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic ����
		Map<String , Object> map = purchaseService.getPurchaseList(search);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/getPurchaseList.jsp";
	}
	
	@RequestMapping("/updateTranCode.do")
	public String updateTranCodeByProd(@ModelAttribute("Purchase") Purchase purchase,
						@RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateTranCode.do");
		//Business Logic
		
		purchaseService.updateTranCode(prodNo);
		
		System.out.println("/updateTranCodeByProd.do");
		//Business Logic
		// Model �� View ����
			
		return "forward:/listPurchase.do";
	}
}