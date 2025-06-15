package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByCategoryId(int categoryId);
//    @Query("SELECT b FROM Board b JOIN FETCH b.writer WHERE b.id = :id")
//    Optional<Board> findByIdWithWriter(@Param("id") Long id);
}
