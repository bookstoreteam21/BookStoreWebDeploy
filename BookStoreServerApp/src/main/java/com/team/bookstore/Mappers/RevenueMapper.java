package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Responses.RevenueDayResponse;
import com.team.bookstore.Dtos.Responses.RevenueMonthResponse;
import com.team.bookstore.Dtos.Responses.RevenueYearResponse;
import com.team.bookstore.Entities.RevenueDay;
import com.team.bookstore.Entities.RevenueMonth;
import com.team.bookstore.Entities.RevenueYear;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;

@Mapper(componentModel = "spring")
public interface RevenueMapper {
    @Mapping(target = "day",source = "day",qualifiedByName = "toDay")
    RevenueDayResponse toRevenueDayResponse(RevenueDay revenueDay);
    @Named("toDay")
    default String toDay(Date day){
        StringJoiner stringJoiner = new StringJoiner("-");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        stringJoiner.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        stringJoiner.add(String.valueOf(calendar.get(Calendar.MONTH)+1));
        stringJoiner.add(String.valueOf(calendar.get(Calendar.YEAR)));
        return stringJoiner.toString();
    }
    @Mapping(target = "month",source = "month",qualifiedByName = "toMonth")
    RevenueMonthResponse toRevenueMonthResponse(RevenueMonth revenueMonth);
    @Named("toMonth")
    default String toMonth(Date month){
        StringJoiner stringJoiner = new StringJoiner("-");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        stringJoiner.add(String.valueOf(calendar.get(Calendar.MONTH)+1));
        stringJoiner.add(String.valueOf(calendar.get(Calendar.YEAR)));
        return stringJoiner.toString();
    }
    @Mapping(target = "year",source = "year",qualifiedByName = "toYear")
    RevenueYearResponse toRevenueYearResponse(RevenueYear revenueYear);
    @Named("toYear")
    default String toYear(Date year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(year);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
