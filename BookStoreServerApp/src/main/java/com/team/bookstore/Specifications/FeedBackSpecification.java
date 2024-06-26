package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Feedback;
import com.team.bookstore.Utilities.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class FeedBackSpecification {
    public static Specification<Feedback> CreateFeedbackKeywordSpec(String keyword){
        return new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword =
                        "%" + StringUtils.removeAccents(keyword.toLowerCase()) + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.function(
                                        "unaccent", String.class,
                                        criteriaBuilder.lower(root.get(
                                                "feedback_comment"))),
                                likeKeyword),
                        criteriaBuilder.like(root.get("rating").as(String.class),likeKeyword)
                );
            }
        };
    }
}
