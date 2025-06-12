package com.project.bcl_back.repository;

import com.project.bcl_back.entity.MatchWaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchWaitingListRepository extends JpaRepository<MatchWaitingList, Long> {
    List<MatchWaitingList> findByTrainer_Id(Long trainerId);

    MatchWaitingList findByMember_Id(Long memberId);
}
