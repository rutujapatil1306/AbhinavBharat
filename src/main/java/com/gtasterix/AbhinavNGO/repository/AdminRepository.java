package com.gtasterix.AbhinavNGO.repository;

import com.gtasterix.AbhinavNGO.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

}
