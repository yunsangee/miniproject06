package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int tranNo = Integer.parseInt(request.getParameter("tranNo"));
        System.out.println("들어와라 tranNo"+tranNo);

        PurchaseService service = new PurchaseServiceImpl();
        Purchase purchase = service.getPurchase02(tranNo);

        request.setAttribute("purchase", purchase);
        
    	HttpSession session=request.getSession();
		String userId = (String)session.getAttribute("userId");
  
        System.out.println("getproductAction tranNo::" + tranNo);
        request.setAttribute("tranNo",tranNo);
      
            return "forward:/purchase/getPurchase.jsp";

    }
}
