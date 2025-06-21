package com.project.bcl_back.repository;

import com.project.bcl_back.common.enums.trainerInfo.TrainerStatus;
import com.project.bcl_back.entity.TrainerListView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TrainerListViewRepository extends CrudRepository<TrainerListView, Long> {
    Page<TrainerListView> findAll(Pageable pageable);
    Page<TrainerListView> findByStatus(TrainerStatus status, Pageable pageable);
}
