package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.RevenueDay;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RevenueDayRepository extends JpaRepository<RevenueDay, Date> {
    List<RevenueDay> findAll(Specification<RevenueDay> spec);
}
