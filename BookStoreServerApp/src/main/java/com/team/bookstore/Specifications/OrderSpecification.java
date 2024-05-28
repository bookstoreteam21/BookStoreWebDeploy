package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Order;
import com.team.bookstore.Utilities.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Log4j2
public class OrderSpecification {
    public static Specification<Order> CreateOrderKeywordSpec(String keyword){
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword =
                        "%" + StringUtils.removeAccents(keyword.toLowerCase()) + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.function(
                                        "unaccent", String.class,
                                        criteriaBuilder.lower(root.get(
                                                "order_note"))),
                                likeKeyword),
                        criteriaBuilder.like(criteriaBuilder.function(
                                        "unaccent", String.class,
                                        criteriaBuilder.lower(root.get(
                                                "address"))),
                                likeKeyword)
                );
            }
    };
    }
    public static Specification<Order> CreateOrderDateSpec(String date){
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(date.length()!=10 && criteriaBuilder!=null){
                    return criteriaBuilder.conjunction();
                }
                SimpleDateFormat sdf       = new SimpleDateFormat("yyyy-MM-dd");
                Timestamp        startTime = null;
                try {
                    startTime = new Timestamp(sdf.parse(date).getTime());
                } catch (Exception e) {
                    log.info(e);
                }
                assert startTime != null;
                Timestamp endTime =
                        new Timestamp(startTime.getTime() + 24*60*60*1000);

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(
                                "createAt"),startTime),
                        criteriaBuilder.lessThan(root.get("createAt"),endTime)
                );
            }
        };
    }
    public static Specification<Order> createRevenueMonthSpec(String year_month) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (year_month.length() != 7) {
                    return criteriaBuilder.conjunction();
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date startDate = null;
                Date endDate = null;

                try {
                    startDate = sdf.parse(year_month);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startDate);
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    endDate = calendar.getTime();
                } catch (Exception e) {
                    e.printStackTrace();
                    return criteriaBuilder.conjunction();
                }

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(
                                "createAt"),
                                startDate),
                        criteriaBuilder.lessThanOrEqualTo(root.get("createAt"),
                                endDate)
                );
            }
        };
    }
    public static Specification<Order> createRevenueYearSpec(String year) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root,
                                         CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (year.length() != 4) {
                    return criteriaBuilder.conjunction();
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date startDate = null;
                Date endDate = null;

                try {
                    startDate = sdf.parse(year);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startDate);
                    calendar.set(Calendar.MONTH, Calendar.JANUARY);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = calendar.getTime();

                    calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                    calendar.set(Calendar.DAY_OF_MONTH, 31);
                    endDate = calendar.getTime();
                } catch (Exception e) {
                    e.printStackTrace();
                    return criteriaBuilder.conjunction();
                }

                return criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(
                                "createAt"), startDate),
                        criteriaBuilder.lessThanOrEqualTo(root.get("createAt"),
                                endDate)
                );
            }
        };
    }
}
