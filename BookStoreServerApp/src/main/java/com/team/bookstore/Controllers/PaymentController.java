package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Entities.Payment;
import com.team.bookstore.Mappers.PaymentMapper;
import com.team.bookstore.Services.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {
    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentMapper  paymentMapper;

    @GetMapping("/all")
    public ResponseEntity<APIResponse<?>> getAllPayments() {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.getAllPayments()).build());
    }

    @GetMapping("/find")
    public ResponseEntity<APIResponse<?>> getAllPayments(@RequestParam String keyword) {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.findPaymentsBy(keyword)).build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/verify")
    public ResponseEntity<APIResponse<?>> verifyPayment(@RequestParam String vnp_ref) {
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(paymentService.verifyPayment(vnp_ref)).build());
    }
    @PostMapping("/payfor")
    public ResponseEntity<APIResponse<?>> payFor(@RequestParam int order_id,
                                                 @RequestParam short method){
        return ResponseEntity.ok(APIResponse.builder().message("OK").code(200).result(paymentService.payForOrder(order_id,method)).build());
    }

}