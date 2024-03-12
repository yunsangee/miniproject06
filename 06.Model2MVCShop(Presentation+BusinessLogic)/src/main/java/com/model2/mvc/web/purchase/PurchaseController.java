package com.model2.mvc.web.purchase;

import java.sql.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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



//==> 회원관리 Controller
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
	//setter Method 구현 않음
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
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
	/*
	@RequestMapping("/getProduct.do")
	public String getProduct(HttpServletResponse response, HttpServletRequest request, @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		
		String history = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("history")) {
                    history = cookie.getValue();
                    break;
                }
            }
        }
        String newProdNo = request.getParameter("prodNo");
        if (newProdNo != null && !newProdNo.isEmpty()) {
            if (history == null) {
                history = ":" + newProdNo;
            } else {
                history += ":" + newProdNo;
            }

            Cookie historyCookie = new Cookie("history", history);
            response.addCookie(historyCookie);
        }
            if (request.getParameter("menu").equals("search")) {
                return "forward:/product/readProduct.jsp";
            } else {
                return "forward:/product/getProduct.jsp";
            }
        
	}

	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProductView.jsp";
	}

	@RequestMapping("/updateProduct.do")
	public String updateProduct( HttpServletRequest request, @ModelAttribute("prodNo") Product product , Model model ) throws Exception{

		System.out.println("/updateProduct.do");
		//Business Logic
		productService.updateProduct(product);
		product.setRegDate(Date.valueOf(request.getParameter("regDatee")));
		System.out.println("/Product.do");
		//Business Logic
		// Model 과 View 연결
		model.addAttribute("product", product);
		
		return "forward:/product/updateProduct.jsp";
	}

	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/product/listProduct.jsp";
	}*/
}