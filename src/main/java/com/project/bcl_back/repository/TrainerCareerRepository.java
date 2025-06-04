package com.project.bcl_back.repository;

import com.project.bcl_back.entity.TrainerCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerCareerRepository extends JpaRepository<TrainerCareer, Long> {
}
