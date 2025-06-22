package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Member;
import com.project.bcl_back.entity.MemberForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberFormRepository extends JpaRepository<MemberForm, Long> {

}
