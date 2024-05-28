package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.OrderDetailRequest;
import com.team.bookstore.Dtos.Requests.OrderRequest;
import com.team.bookstore.Dtos.Responses.OrderDetailResponse;
import com.team.bookstore.Dtos.Responses.OrderResponse;
import com.team.bookstore.Entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "status_trans",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "price",ignore = true)
    @Mapping(target = "total_dis",ignore = true)
    @Mapping(target = "total_price",ignore = true)
    @Mapping(target = "order_detail",source = "order_details",
            qualifiedByName = "toOrderDetail")
    @Mapping(target = "payment",ignore = true)
    @Mapping(target = "customerId",ignore = true)

    Order toOrder(OrderRequest orderRequest);
    @Named("toOrderDetail")
    default Set<Order_Detail> toOrderDetail(Set<OrderDetailRequest> order_details){
        Set<Order_Detail> new_order_details = new HashSet<>();
        order_details.forEach(order_detail -> {
            Order_Detail orderDetail = new Order_Detail();
            Book book = new Book();
            book.setId(order_detail.getBook_id());
            orderDetail.setBook(book);
            orderDetail.setDiscount(book.getDiscount() * order_detail.getQuantity());
            orderDetail.setQuantity(order_detail.getQuantity());
            new_order_details.add(orderDetail);
        });
        return new_order_details;
    }
    @Mapping(target = "method_payment",source = "payment",qualifiedByName =
            "toMethod_payment")
    @Mapping(target = "orderDetailResponse",source = "order_detail",
            qualifiedByName = "toOrderDetailResponse")
    OrderResponse toOrderResponse(Order order);
    @Named("toMethod_payment")
    default int toMethod_payment(Payment payment){
        return payment.getMethod_payment();
    }
    @Named("toOrderDetailResponse")
    default List<OrderDetailResponse> toOrderDetailResponse(Set<Order_Detail> order_detail){
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        order_detail.forEach(orderDetail->{
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setTitle(orderDetail.getBook().getTitle());
            orderDetailResponse.setQuantity(orderDetail.getQuantity());
            orderDetailResponse.setPrice(orderDetail.getPrice());
            orderDetailResponse.setDiscount(orderDetail.getDiscount());
            orderDetailResponse.setTotal_price(orderDetail.getTotal_money());
            orderDetailResponses.add(orderDetailResponse);
        });
        return orderDetailResponses;
    }
}
