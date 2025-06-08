package com.project.bcl_back.repository;

import com.project.bcl_back.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByTargetTypeAndBoard_Id(String targetType, Long BoardId);
}
