package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.RevenueMonth;
import com.team.bookstore.Entities.RevenueYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RevenueYearRepository extends JpaRepository<RevenueYear,
        Date> {
}
