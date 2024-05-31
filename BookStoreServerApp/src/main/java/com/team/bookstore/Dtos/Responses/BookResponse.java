package com.team.bookstore.Dtos.Responses;

import com.team.bookstore.Entities.*;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    int id;
    String title;
    short num_pages;
    Date publication_date;
    int bookQuantity;
    int price;
    int discount;
    String description;
    boolean hot;
    int total_pay;
    boolean available;
    Integer readingsession;
    Date createAt;
    Date updateAt;
    String createBy;
    String updateBy;
    int catgory_id;
    Set<Author> authors;
    Publisher publisher;
    Language language;
    Set<GalleryManage> galleryManage;
    Provider               provider;
    List<FeedbackResponse> feedback;
}
