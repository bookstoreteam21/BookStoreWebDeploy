package com.team.bookstore.Dtos.Requests;

import io.micrometer.common.lang.NonNullFields;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffInformationRequest{
    String fullname;
    Boolean gender;
    Date birthday;
    String phonenumber;
    String email;
    String address;
    Date initiate_time;
    int salary;
}
