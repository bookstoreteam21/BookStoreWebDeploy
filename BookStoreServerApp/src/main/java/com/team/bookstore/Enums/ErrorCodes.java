package com.team.bookstore.Enums;

import lombok.Getter;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCodes {
    UN_CATEGORIED(10,"Lỗi không xác định!",HttpStatus.INTERNAL_SERVER_ERROR),
    REGISTER_DENIED(11,"Đăng ký không thành công!",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(12,"Tài khoản không tồn tại!",HttpStatus.NOT_FOUND),
    ACCESS_DENIED(13,"Truy cập bị từ chối",
            HttpStatus.NOT_ACCEPTABLE),
    UN_AUTHORISED(14,"Bạn không có quyền truy cập vào nội dung này!",
            HttpStatus.UNAUTHORIZED),
    UN_AUTHENTICATED(15,"Bạn chưa được xác thực!",HttpStatus.NOT_ACCEPTABLE),
    USER_HAS_BEEN_EXIST(16,"Tài khoản đã tồn tại!",
            HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USER(17,"Tài khoản không hợp lệ!",HttpStatus.BAD_REQUEST),
    OBJECT_NOT_EXIST(18,"Your required object is not exist!",
            HttpStatus.NOT_FOUND),
    OBJECT_HAS_BEEN_EXISTING(19,"This object has been existing!",
            HttpStatus.BAD_REQUEST),
    INVALID_OBJECT(20,"Your input object is invalid!",HttpStatus.BAD_REQUEST),
    NOT_FOUND(23,"Not found this object!",HttpStatus.NOT_FOUND),
    IS_EXPIRED(26,"This object is expired!",HttpStatus.INTERNAL_SERVER_ERROR),
    NULL_FIELD(27,"A field of your object is null!",HttpStatus.BAD_REQUEST),
    PURCHASED(28,"Your payment has been purchased!",HttpStatus.BAD_REQUEST),
    UN_CATEGORIZED(100,"Lỗi không xác định!",HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_EXIST(101," không tồn tại!",HttpStatus.NOT_FOUND),
    HAS_BEEN_EXIST(102," đã tồn tại!",HttpStatus.BAD_REQUEST),
    CANNOT_CREATE(103," không thể khởi tạo!",HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_UPDATE(104," không thể cập nhật!",HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_DELETE(105," không thể xóa!",HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_VERIFY(106," không thể xác nhận!",HttpStatus.INTERNAL_SERVER_ERROR),
    HAS_BEEN_VERIFIED(107," đã được xác nhận!",HttpStatus.BAD_REQUEST),
    EXPIRED(108," đã hết hạn!",HttpStatus.BAD_REQUEST),
    CANNOT_PURCHASE(109," không thể thanh toán!",
            HttpStatus.INTERNAL_SERVER_ERROR),
    HAS_BEEN_PURCHASED(110," đã được thanh toán!",HttpStatus.BAD_REQUEST),
    NOT_BLANK(111," không được bỏ trống!",HttpStatus.BAD_REQUEST),
    MALFORMED(112," không đúng định dạng dữ liệu!",HttpStatus.BAD_REQUEST),
    UNFIT(113," không đúng độ dài quy định!",HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(114,"Sai mật khẩu!",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(115,"Bạn chưa được xác thực!",HttpStatus.NOT_ACCEPTABLE),
    UNAUTHORISED(116,"Bạn không có quyền truy cập vào nội dung này!",
            HttpStatus.UNAUTHORIZED),
    REGISTER_FAILD(117, "Đăng ký thất bại!",HttpStatus.INTERNAL_SERVER_ERROR),
    CANCLE(118," không thể hủy!",HttpStatus.INTERNAL_SERVER_ERROR);
    final int code;
    final String message;
    final HttpStatus httpStatus;
    ErrorCodes(int code,String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
