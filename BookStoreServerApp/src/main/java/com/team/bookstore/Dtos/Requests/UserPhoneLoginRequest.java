package com.team.bookstore.Dtos.Requests;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPhoneLoginRequest {
    String phonenumber;
    @Size(max= 16,min = 12)
    String password;
}
