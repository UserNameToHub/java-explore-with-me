package ru.practicum.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.entity.Comment;
import ru.practicum.common.enumiration.State;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    boolean existsByIdAndOwnerId(Integer commentId, Integer userId);

    List<Comment> findAllByEventIdAndStateIs(Integer commentId, State state);
}
