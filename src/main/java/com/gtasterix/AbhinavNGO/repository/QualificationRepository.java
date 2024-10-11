package com.gtasterix.AbhinavNGO.repository;

import com.gtasterix.AbhinavNGO.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface QualificationRepository extends JpaRepository<Qualification, Integer> {
    Qualification findByStandardAndUniversityAndPassingYear(String standard, String university, String passingYear);
}
