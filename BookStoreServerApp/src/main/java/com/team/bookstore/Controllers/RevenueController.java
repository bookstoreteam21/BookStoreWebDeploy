package com.team.bookstore.Controllers;

import com.team.bookstore.Dtos.Responses.APIResponse;
import com.team.bookstore.Dtos.Responses.RevenueDayResponse;
import com.team.bookstore.Dtos.Responses.RevenueMonthResponse;
import com.team.bookstore.Dtos.Responses.RevenueYearResponse;
import com.team.bookstore.Entities.RevenueDay;
import com.team.bookstore.Services.RevenueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revenue")
@SecurityRequirement(name = "bearerAuth")
public class RevenueController  {
    @Autowired
    RevenueService revenueService;
    @GetMapping("/day/all")
    public ResponseEntity<APIResponse<?>> getAllRevenueDay(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(revenueService.getAllRevenueDay()).build());
    }
    @GetMapping("/month/all")
    public ResponseEntity<APIResponse<?>> getAllRevenueMonth(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(revenueService.getAllRevenueMonth()).build());
    }
    @GetMapping("/year/all")
    public ResponseEntity<APIResponse<?>> getAllRevenueYear(){
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(revenueService.getAllRevenueYear()).build());
    }
    @PostMapping("/day/cal/{date}")
    public ResponseEntity<APIResponse<?>> createDateRevenue(@PathVariable String date){
        RevenueDayResponse result = revenueService.generateDayRevenue(date);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PostMapping("/month/cal/{date}")
    public ResponseEntity<APIResponse<?>> createMonthRevenue(@PathVariable String date){
        RevenueMonthResponse result =
                revenueService.generateMonthRevenue(date);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
    @PostMapping("/year/cal/{date}")
    public ResponseEntity<APIResponse<?>> createYearRevenue(@PathVariable String date){
        RevenueYearResponse result = revenueService.generateYearRevenue(date);
        return ResponseEntity.ok(APIResponse.builder().code(200).message("OK").result(result).build());
    }
}
