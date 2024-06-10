package com.team.bookstore.Dtos.Responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VNPAYResponse {
    String vnp_TxnRef;
    String vnp_Amount;
    String vnp_OrderInfo;
    String vnp_ResponseCode;
    String vnp_TransactionNo;
    String vnp_BankCode;
    String vnp_PayDate;
    String vnp_TransactionStatus;
}
