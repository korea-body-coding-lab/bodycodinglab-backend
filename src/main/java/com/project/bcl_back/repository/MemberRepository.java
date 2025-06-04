package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
