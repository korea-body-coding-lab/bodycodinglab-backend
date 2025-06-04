package com.project.bcl_back.repository;

import com.project.bcl_back.entity.TrainerLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerLicenseRepository extends JpaRepository<TrainerLicense, Long> {
}
