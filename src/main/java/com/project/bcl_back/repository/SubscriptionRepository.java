package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByMemberId(Long memberId);
}
