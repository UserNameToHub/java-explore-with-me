package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.user.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User as u " +
            "where (:ids is null or u.id in :ids)")
    Page<User> findAll(List<Integer> ids, Pageable pageable);
}
