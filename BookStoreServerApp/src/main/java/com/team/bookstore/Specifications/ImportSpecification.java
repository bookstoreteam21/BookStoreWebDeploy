package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Import;
import com.team.bookstore.Utilities.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Log4j2
public class ImportSpecification {
    public static Specification<Import> CreateImportKeywordSpec(String keyword){
        return new Specification<Import>() {
            @Override
            public Predicate toPredicate(Root<Import> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword =
                        "%" + StringUtils.removeAccents(keyword.toLowerCase()) + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.function(
                                        "unaccent", String.class,
                                        criteriaBuilder.lower(root.get(
                                                "create_by"))),
                                likeKeyword),
                        criteriaBuilder.like(root.get("import_total").as(String.class),likeKeyword),
                        criteriaBuilder.like(root.get("import_status").as(String.class),likeKeyword)
                );
            }
        };
    }
    public static Specification<Import> CreateImportDateSpec(String date){
        return new Specification<Import>() {
            @Override
            public Predicate toPredicate(Root<Import> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
}
