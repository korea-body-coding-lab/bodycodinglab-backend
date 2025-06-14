package com.project.bcl_back.repository;

import com.project.bcl_back.entity.TrainerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerInfoRepository extends JpaRepository<TrainerInfo, Long> {
    @Query("SELECT t FROM TrainerInfo t JOIN t.user u WHERE u.name LIKE %:name%")
    List<TrainerInfo> findTrainerByName(@Param("name") String name);

    @Query("SELECT t FROM TrainerInfo t WHERE t.jobAddress LIKE %:jobAddress%")
    List<TrainerInfo> findTrainerByAddress(@Param("jobAddress") String jobAddress);
}
