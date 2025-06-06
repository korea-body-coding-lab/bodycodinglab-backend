package com.project.bcl_back.repository;

import com.project.bcl_back.entity.MatchWaitingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchWaitingListRepository extends JpaRepository<MatchWaitingList, Long> {
}
