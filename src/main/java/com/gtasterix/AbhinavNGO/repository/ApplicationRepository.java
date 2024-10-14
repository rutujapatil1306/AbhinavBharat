package com.gtasterix.AbhinavNGO.repository;

import com.gtasterix.AbhinavNGO.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Optional<Application> findByFirstName(String firstName);
    Optional<Application> findByMailID(String mailID);
    Optional<Application> findByAdharCard(String AdharCard);
    Optional<Application> findByPanCardNo(String panCardNo);
}
