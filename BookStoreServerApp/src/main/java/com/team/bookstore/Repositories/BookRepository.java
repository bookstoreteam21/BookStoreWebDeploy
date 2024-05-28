package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findBookById(int id);
    List<Book> findAll(Specification<Book> spec);
    List<Book> findBooksByAvailable(boolean available);
}
