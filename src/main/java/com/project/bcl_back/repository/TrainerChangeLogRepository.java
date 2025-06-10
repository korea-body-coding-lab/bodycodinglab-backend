package com.project.bcl_back.repository;

import com.project.bcl_back.entity.TrainerChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerChangeLogRepository extends JpaRepository<TrainerChangeLog, Long> {
}
