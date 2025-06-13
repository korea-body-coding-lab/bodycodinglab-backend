package com.project.bcl_back.repository;

import com.project.bcl_back.entity.TrainerCareer;
import com.project.bcl_back.entity.TrainerLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerLicenseRepository extends JpaRepository<TrainerLicense, Long> {
    List<TrainerLicense> findByTrainerInfoId(Long trainerId);

    TrainerLicense findTopByTrainerInfoIdOrderByIdDesc(Long id);
}
