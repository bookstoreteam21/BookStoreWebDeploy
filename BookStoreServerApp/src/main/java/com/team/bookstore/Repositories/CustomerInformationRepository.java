package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.CustomerInformation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInformationRepository extends JpaRepository<CustomerInformation,Integer>, JpaSpecificationExecutor<CustomerInformation> {
    List<CustomerInformation> findAll(Specification<CustomerInformation> spec);
    CustomerInformation findCustomerInformationById(int id);
    Boolean existsCustomerInformationById(int id);
    CustomerInformation findCustomerInformationByPhonenumber(String phoneNumber);

    CustomerInformation findCustomerInformationByEmail(String email);
    boolean existsCustomerInformationByPhonenumber(String phoneNumber);
    boolean existsCustomerInformationByEmail(String email);
}
