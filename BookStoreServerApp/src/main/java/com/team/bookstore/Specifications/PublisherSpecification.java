package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Publisher;
import com.team.bookstore.Utilities.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PublisherSpecification {
    public static Specification<Publisher> CreatePublisherKeywordSpec(String keyword){
        return new Specification<Publisher>() {
            @Override
            public Predicate toPredicate(Root<Publisher> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword =
                        "%" + StringUtils.removeAccents(keyword.toLowerCase()) + "%";
                return criteriaBuilder.like(criteriaBuilder.function(
                                "unaccent", String.class,
                                criteriaBuilder.lower(root.get(
                                        "publisher_name"))),
                        likeKeyword);
            }
        };
    }
}
