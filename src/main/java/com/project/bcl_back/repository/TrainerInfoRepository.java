package com.project.bcl_back.repository;

import com.project.bcl_back.entity.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerInfoRepository extends JpaRepository<TrainerInfo, Long> {
}
