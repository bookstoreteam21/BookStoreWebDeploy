package com.team.bookstore.Dtos.Responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevenueYearResponse {
    String year;
    long total_sale;
    long total_import;
    long revenue;
    Date createAt;
    String createBy;
}
