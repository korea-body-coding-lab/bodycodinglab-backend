package com.project.bcl_back.repository;

import com.project.bcl_back.entity.MatchWaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchWaitingListRepository extends JpaRepository<MatchWaitingList, Long> {
    Optional<List<MatchWaitingList>> findByTrainer_Id(Long trainerId);

    Optional<MatchWaitingList> findByMember_Id(Long memberId);
}
