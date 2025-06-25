package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // 받은 쪽지
    Page<Note> findByNoteReceiver(Long receiverId, Pageable pageable);

    // 보낸 쪽지
    Page<Note> findByNoteWriter(Long writerId, Pageable pageable);

    Page<Note> findByNoteWriterOrNoteReceiver(Long noteWriter, Long noteReceiver, Pageable pageable);

}
