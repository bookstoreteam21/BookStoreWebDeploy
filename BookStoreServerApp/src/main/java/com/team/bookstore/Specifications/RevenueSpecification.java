package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.RevenueDay;
import com.team.bookstore.Entities.RevenueMonth;
import com.team.bookstore.Entities.RevenueYear;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RevenueSpecification {
    public static Specification<RevenueDay> GenerateRevenueDaySpec(String yearMonth) {
        return new Specification<RevenueDay>() {
            @Override
            public Predicate toPredicate(Root<RevenueDay> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (yearMonth == null || yearMonth.length() != 7) {
                    return criteriaBuilder.conjunction(); // return all records if yearMonth is invalid
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                Date             startDate;
                try {
                    startDate = dateFormat.parse(yearMonth);
                } catch (ParseException e) {
                    throw new RuntimeException("Invalid date format. Expected yyyy-MM", e);
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                Date endDate = calendar.getTime();

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("day"), startDate),
                        criteriaBuilder.lessThan(root.get("day"), endDate)
                );
            }
        };
    }
    public static Specification<RevenueMonth> GenerateRevenueMonthSpec(String year){
        return new Specification<RevenueMonth>() {
            @Override
            public Predicate toPredicate(Root<RevenueMonth> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (year == null || year.length() != 4) {
                    return criteriaBuilder.conjunction(); // return all records if year is invalid
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
                Date startDate;
                try {
                    startDate = dateFormat.parse(year);
                } catch (ParseException e) {
                    throw new RuntimeException("Invalid date format. Expected yyyy", e);
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, 11);
                Date endDate = calendar.getTime();

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("month"),
                                startDate),
                        criteriaBuilder.lessThan(root.get("month"), endDate));
            }
        };
    }
}
