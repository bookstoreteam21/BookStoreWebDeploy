package com.team.bookstore.Specifications;

import com.team.bookstore.Entities.Category;
import com.team.bookstore.Utilities.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> CreateCategoryKeywordSpec(String keyword){
        return new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                String likeKeyword =
                        "%" + StringUtils.removeAccents(keyword.toLowerCase()) + "%";
                return criteriaBuilder.like(criteriaBuilder.function(
                                        "unaccent", String.class,
                                        criteriaBuilder.lower(root.get("name"))),
                                likeKeyword);
            }
        };
    }
}
