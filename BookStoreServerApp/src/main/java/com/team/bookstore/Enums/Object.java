package com.team.bookstore.Enums;

import lombok.Getter;

@Getter
public enum Object {
    BOOK("Sách"),
    AUTHOR("Tác giả"),
    KEYWORD("Từ khóa"),
    CATEGORY("Thể loại"),
    CUSTOMERINF("Thông tin khách hàng"),
    FEEDBACK("Phản hồi"),
    GALLERY("Ảnh"),
    IMPORT("Phiếu nhập"),
    IMPORT_DETAIL("Chi tiết phiếu nhập"),
    TOKEN("Mã token"),
    LANGUAGE("Ngôn ngữ"),
    MESSAGE("Tin nhắn"),
    ORDER("Đơn hàng"),
    ORDER_DETAIL("Chi tiết đơn hàng"),
    PAYMENT("Phiếu thanh toán"),
    PERMISSION("Quyền truy cập"),
    PROVIDER("Nhà cung cấp"),
    PUBLISHER("Nhà xuất bản"),
    REVENUEDAY("Thống kê doanh thu ngày"),
    REVENUEMONTH("Thống kê doanh thu tháng"),
    REVENUEYEAR("Doanh thu năm"),
    ROLE("Vai trò"),
    SHIFT("Ca làm"),
    STAFFINF("Thông tin nhân viên"),
    USER("Tài khoản"),
    DELETED_BOOK("Sách đã xóa"),
    MY_BOOK("Sách đã mua"),
    MY_ORDER("Đơn hàng của tôi"),
    MY_IMPORT("Phiếu nhập của tôi"),
    MY_INF("Thông tin cá nhân"),
    RECEIVER("Người nhận"),
    SENDER("Người gửi"),
    PAYMENTMETHOD("Phương thức thanh toán"),
    SCHEDULE("Lịch làm việc"),
    PASSWORD("Mật khẩu");
    final String name;
    Object(String name){
        this.name = name;
    }
}
