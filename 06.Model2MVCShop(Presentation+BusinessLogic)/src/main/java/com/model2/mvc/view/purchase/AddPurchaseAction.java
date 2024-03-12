package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		Purchase purchase=new Purchase();
		
		HttpSession session=request.getSession();
		
		 String userId = (String)session.getAttribute("userId");
	      User user = (User)session.getAttribute("user");


        int prodNo = Integer.parseInt(request.getParameter("prodNo"));
        
        System.out.println(prodNo);
        ProductService services = new ProductServiceImpl();
        Product vo = services.getProduct(prodNo);
        
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("divyAddr"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		purchase.setDivyRequest(request.getParameter("divyRequest"));
		purchase.setBuyer(user);
		purchase.setPurchaseProd(vo);
		purchase.setTranCode("0");
				
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		request.setAttribute("purchase",purchase);
		request.setAttribute("tranNo",prodNo);
		System.out.println("addpurchaseaction : "+purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
}