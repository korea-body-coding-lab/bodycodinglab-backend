package com.project.bcl_back.repository;

import com.project.bcl_back.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // 받은 쪽지
    List<Note> findByNoteReceiver(Long receiverId);

    // 보낸 쪽지
    List<Note> findByNoteWriter(Long writerId);
}
