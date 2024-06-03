package com.team.bookstore.Controllers;

import com.team.bookstore.Services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/result")
public class ResultController {
   @Autowired
    PaymentService paymentService;
    @GetMapping("/vnpay-result")
    public String vnpayResult(HttpServletRequest request,
                              @RequestParam String vnp_TxnRef, Model model){
        if(Objects.equals(request.getParameter(
                "vnp_TransactionStatus"), "00")) {
            paymentService.verifyPayment(vnp_TxnRef);
        }
        model.addAttribute("vnp_TxnRef",vnp_TxnRef);
        model.addAttribute("vnp_Amount",request.getParameter("vnp_Amount"));
        model.addAttribute("vnp_OrderInfo",request.getParameter(
                "vnp_OrderInfo"));
        model.addAttribute("vnp_ResponseCode",request.getParameter(
                "vnp_ResponseCode"));
        model.addAttribute("vnp_TransactionNo",request.getParameter(
                "vnp_TransactionNo"));
        model.addAttribute("vnp_BankCode",request.getParameter("vnp_BankCode"));
        model.addAttribute("vnp_PayDate",request.getParameter("vnp_PayDate"));
        model.addAttribute("vnp_TransactionStatus",request.getParameter(
                "vnp_TransactionStatus"));
        ////

        return "vnpay_return";
    }
}
