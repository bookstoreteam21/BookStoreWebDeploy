package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Language;
import com.team.bookstore.Utilities.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class LanguageSpecification {
    public static Specification<Language> CreateLanguageKeywordSpec(String keyword){
        return new Specification<Language>() {
            @Override
            public Predicate toPredicate(Root<Language> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword =
                        "%" + StringUtils.removeAccents(keyword.toLowerCase()) +
                        "%";
                return criteriaBuilder.like(criteriaBuilder.function(
                                "unaccent", String.class,
                                criteriaBuilder.lower(root.get("language_name"))),
                        likeKeyword);
            }
        };
    }
}
