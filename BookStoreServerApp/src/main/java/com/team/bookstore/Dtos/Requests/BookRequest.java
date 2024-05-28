package com.team.bookstore.Dtos.Requests;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.bookstore.Entities.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    @NotNull
    String title;
    @Max(10000)
    short num_pages;
    Date publication_date;
    int bookQuantity;
    int price;
    int discount;
    String description;
    boolean hot;
    boolean available;
    int language_id;
    int category_id;
    int publisher_id;
    int provider_id;
    Set<Integer> gallery_ids;
    Set <Integer> author_ids;
}