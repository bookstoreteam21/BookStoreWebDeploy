package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.RevenueDayResponse;
import com.team.bookstore.Dtos.Responses.RevenueMonthResponse;
import com.team.bookstore.Dtos.Responses.RevenueYearResponse;
import com.team.bookstore.Entities.*;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Mappers.RevenueMapper;
import com.team.bookstore.Repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.ImportSpecification.CreateImportDateSpec;
import static com.team.bookstore.Specifications.OrderSpecification.CreateOrderDateSpec;
import static com.team.bookstore.Specifications.RevenueSpecification.GenerateRevenueDaySpec;
import static com.team.bookstore.Specifications.RevenueSpecification.GenerateRevenueMonthSpec;

@Service
@Log4j2
public class RevenueService {
    @Autowired
    RevenueMapper revenueMapper;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ImportRepository     importRepository;
    @Autowired
    RevenueDayRepository revenueDayRepository;
    @Autowired
    RevenueMonthRepository revenueMonthRepository;
    @Autowired
    RevenueYearRepository revenueYearRepository;
    /*
    public List<RevenueYear> getAllRevenueYear(){
        try{
            return revenueYearRepository.findAll();
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
/*
    public List<RevenueMonth> getAllRevenueMonth(){
        try{
            return revenueMonthRepository.findAll();
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }


 */
    @Secured("ROLE_ADMIN")
    public List<RevenueDayResponse> getAllRevenueDay(){
        try{
            return revenueDayRepository.findAll().stream().map(revenueMapper::toRevenueDayResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<RevenueMonthResponse> getAllRevenueMonth(){
        try{
            return revenueMonthRepository.findAll().stream().map(revenueMapper::toRevenueMonthResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    public List<RevenueYearResponse> getAllRevenueYear(){
        try{
            return revenueYearRepository.findAll().stream().map(revenueMapper::toRevenueYearResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.NOT_FOUND);
        }
    }
    @Secured("ROLE_ADMIN")
    public RevenueDayResponse generateDayRevenue(String date){
        try{
            Specification<Order>  orderSpec  = CreateOrderDateSpec(date);
            Specification<Import> importSpec = CreateImportDateSpec(date);
            List<Order> orders = orderRepository.findAll(orderSpec);
            List<Import>  imports     = importRepository.findAll(importSpec);
            AtomicInteger totalSales  = new AtomicInteger();
            AtomicInteger totalImport = new AtomicInteger();
            orders.forEach(order -> {
                totalSales.addAndGet(order.getTotal_price());
            });
            imports.forEach(_import->{
                totalImport.addAndGet(_import.getImport_total());
            });
            RevenueDay revenueDay = new RevenueDay();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            revenueDay.setDay(sdf.parse(date));
            revenueDay.setTotal_sale(totalSales.longValue());
            revenueDay.setTotal_import(totalImport.longValue());
            revenueDay.setRevenue(totalSales.longValue() - totalImport.longValue());
            return revenueMapper.toRevenueDayResponse(revenueDayRepository.save(revenueDay));
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_CATEGORIED);
        }
    }
    public RevenueMonthResponse generateMonthRevenue(String date){
        try{
            Specification<RevenueDay> spec =  GenerateRevenueDaySpec(date);
            List<RevenueDay> revenueDays = revenueDayRepository.findAll(spec);
            AtomicInteger totalSales  = new AtomicInteger();
            AtomicInteger totalImport = new AtomicInteger();
            revenueDays.forEach(revenueDay -> {
                totalSales.addAndGet((int) revenueDay.getTotal_sale());
                totalImport.addAndGet((int) revenueDay.getTotal_import());
            });
            RevenueMonth revenueMonth = new RevenueMonth();
            revenueMonth.setMonth(revenueDays.get(0).getDay());
            revenueMonth.setTotal_sale(totalSales.longValue());
            revenueMonth.setTotal_import(totalImport.longValue());
            revenueMonth.setRevenue(totalSales.longValue() - totalImport.longValue());
            return revenueMapper.toRevenueMonthResponse(revenueMonthRepository.save(revenueMonth));
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_CATEGORIED);
        }
    }
    public RevenueYearResponse generateYearRevenue(String date){
        try{
           Specification<RevenueMonth> spec = GenerateRevenueMonthSpec(date);
           List<RevenueMonth> revenueMonths =
                   revenueMonthRepository.findAll(spec);
            AtomicInteger totalSales  = new AtomicInteger();
            AtomicInteger totalImport = new AtomicInteger();
            revenueMonths.forEach(revenueMonth -> {
                totalSales.addAndGet((int) revenueMonth.getTotal_sale());
                totalImport.addAndGet((int) revenueMonth.getTotal_import());
            });
            RevenueYear revenueYear = new RevenueYear();
            revenueYear.setYear(revenueMonths.get(0).getMonth());
            revenueYear.setTotal_sale(totalSales.longValue());
            revenueYear.setTotal_import(totalImport.longValue());
            revenueYear.setRevenue(totalSales.longValue() - totalImport.longValue());
            return revenueMapper.toRevenueYearResponse(revenueYearRepository.save(revenueYear));
        } catch(Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.UN_CATEGORIED);
        }
    }
}
