package com.project.bcl_back.repository;

import com.project.bcl_back.entity.TrainerCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerCareerRepository extends JpaRepository<TrainerCareer, Long> {
    List<TrainerCareer> findByTrainerInfoId(Long trainerId);

    TrainerCareer findTopByTrainerInfoIdOrderByCompanyQuitDesc(Long id);
}
