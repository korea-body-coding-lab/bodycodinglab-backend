package com.project.bcl_back.repository;

import com.project.bcl_back.entity.OneDayTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayTicketRepository extends JpaRepository<OneDayTicket, Long> {
    List<OneDayTicket> findByMemberId(Long id);
}
