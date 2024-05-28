package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Keyword;
import com.team.bookstore.Utilities.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
public class KeywordSpecification {
    public static Specification<Keyword> CreateKeywordKeywordSpec(String keyword){
        return new Specification<Keyword>() {
            @Override
            public Predicate toPredicate(Root<Keyword> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword =
                        "%" + StringUtils.removeAccents(keyword.toLowerCase()) +
                        "%";
                return criteriaBuilder.like(criteriaBuilder.function(
                                "unaccent", String.class,
                                criteriaBuilder.lower(root.get("name"))),
                        likeKeyword);
            }
        };
    }
}
