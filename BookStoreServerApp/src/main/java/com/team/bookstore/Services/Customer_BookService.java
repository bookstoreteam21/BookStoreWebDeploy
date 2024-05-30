package com.team.bookstore.Services;

import com.team.bookstore.Entities.ComposeKey.CustomerBookKey;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.Customer_Book;
import com.team.bookstore.Entities.Order_Detail;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Enums.Object;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Exceptions.ObjectException;
import com.team.bookstore.Repositories.BookRepository;
import com.team.bookstore.Repositories.CustomerInformationRepository;
import com.team.bookstore.Repositories.Customer_BookRepository;
import com.team.bookstore.Repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.team.bookstore.Specifications.Customer_BookSpecification.CreateCustomerBookByCustomerIDSpec;

@Service
@Log4j2
public class Customer_BookService {
    @Autowired
    Customer_BookRepository customerBookRepository;
    @Autowired
    CustomerInformationRepository customerInformationRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @Secured("ROLE_ADMIN")
    public void updateCustomer_Book(int customer_id,
                                    Set<Order_Detail> order_details){
        try{
            if(!customerInformationRepository.existsById(customer_id)){
                throw new ObjectException(Object.CUSTOMERINF.getName(),
                        ErrorCodes.NOT_EXIST);
            }
            CustomerInformation customer =
                    customerInformationRepository.findCustomerInformationById(customer_id);
            Set<Customer_Book> customer_books = new HashSet<>();
            order_details.forEach(order_detail -> {
                if(!bookRepository.existsById(order_detail.getBook().getId())){
                    throw new ObjectException(Object.BOOK.getName(),
                            ErrorCodes.NOT_EXIST);
                }
                Customer_Book customer_book = new Customer_Book();
                customer_book.setBook(order_detail.getBook());
                customer_book.setCustomer_information(customer);
                if(!customerBookRepository.existsCustomer_BookById(new CustomerBookKey(customer_id,order_detail.getBook().getId()))) {
                    customer_books.add(customer_book);
                }
            });
            customerBookRepository.saveAll(customer_books);
        } catch (Exception e){
            throw new ObjectException(Object.MY_BOOK.getName(),ErrorCodes.CANNOT_UPDATE);
        }
    }
}
