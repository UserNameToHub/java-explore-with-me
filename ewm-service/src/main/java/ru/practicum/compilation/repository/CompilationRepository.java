package ru.practicum.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.compilation.entity.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    @Query("select c from Compilation as c " +
            "where (:pinned is null or c.pinned = :pinned)")
    Page<Compilation> findAll(Boolean pinned, Pageable pageable);
}
