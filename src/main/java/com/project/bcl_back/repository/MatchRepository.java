package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findByMemberId(Long memberId);

    Optional<Match> findByTrainerId(Long trainerId);
}
