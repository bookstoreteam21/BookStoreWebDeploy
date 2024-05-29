package com.team.bookstore.Repositories;

import com.team.bookstore.Entities.StaffInformation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffInformationRepository extends JpaRepository<StaffInformation,Integer>, JpaSpecificationExecutor<StaffInformation> {
    List<StaffInformation> findAll(Specification<StaffInformation> spec);
    StaffInformation findStaffInformationById(int id);
    Boolean existsStaffInformationById(int id);
    StaffInformation findStaffInformationByPhonenumber(String phoneNumber);
    StaffInformation findStaffInformationByEmail(String email);
    boolean existsStaffInformationByPhonenumber(String phoneNumber);
    boolean existsStaffInformationByEmail(String email);
}
