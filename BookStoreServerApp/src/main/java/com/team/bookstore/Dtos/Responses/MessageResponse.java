package com.team.bookstore.Dtos.Responses;

import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    int id;
    int sender_id;
    String sender_name;
    byte[] sender_avatar;
    int receiver_id;
    String receiver_name;
    byte[] receiver_avatar;
    String message_content;
    int message_status;
    Date createAt;
    String createBy;
}


