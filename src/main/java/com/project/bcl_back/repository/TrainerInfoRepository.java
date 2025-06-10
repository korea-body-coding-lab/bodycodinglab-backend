package com.project.bcl_back.repository;

import com.project.bcl_back.common.enums.trainerInfo.Status;
import com.project.bcl_back.entity.TrainerInfo;
import com.project.bcl_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerInfoRepository extends JpaRepository<TrainerInfo, Long> {
}
