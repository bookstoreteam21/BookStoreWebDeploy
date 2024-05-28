package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.RevenueDay;
import com.team.bookstore.Entities.RevenueMonth;
import com.team.bookstore.Entities.RevenueYear;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RevenueMonthRepository extends JpaRepository<RevenueMonth, Date> {
    List<RevenueMonth> findAll(Specification<RevenueMonth> spec);
}
