package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByStatus(String status);

    @Modifying
    @Query("UPDATE Coupon c SET c.status = 'EXPIRED' WHERE c.expirationPeriod < :today AND c.status = 'NOT_USED'")
    void expireOldCoupons(@Param("today") LocalDate today);
}
