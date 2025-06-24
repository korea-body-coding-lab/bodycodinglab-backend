package com.project.bcl_back.repository;

import com.project.bcl_back.common.enums.coupon.CouponStatus;
import com.project.bcl_back.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<List<Coupon>> findByStatus(CouponStatus status);

    List<Coupon> findByExpirationPeriodBeforeAndStatus(LocalDate date, CouponStatus status);


    List<Coupon> findByStatusAndUsedDateBefore(CouponStatus status, LocalDateTime usedDate);


    List<Coupon> findByStatusAndExpirationPeriodBefore(CouponStatus status, LocalDate date);
}
