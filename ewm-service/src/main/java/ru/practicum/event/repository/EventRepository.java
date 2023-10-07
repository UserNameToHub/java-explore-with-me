package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.entity.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    boolean existsEventByCategoryId(Integer id);

    boolean existsByIdAndStateIs(Integer id, State status);

    Page<Event> findAllByInitiatorId(Integer userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Integer eventId, Integer userId);

    Optional<Event> findByIdAndStateIs(Integer id, State state);



    @Query("select e from Event as e " +
            "join e.category as c " +
            "where e.state = 'PUBLISHED' and (:text is null or (upper(e.annotation) like concat('%', upper(:text), '%'))) or " +
            "(:text is null or (upper(e.description) like concat('%', upper(:text), '%'))) and " +
            "(:categories is null or c.id in :categories) and (:paid is null or e.paid = :paid) and " +
            "(cast(:rangeStart as date) is null or e.eventDate >= :rangeStart) and (cast(:rangeEnd as date) is null or e.eventDate <= :rangeEnd) and " +
            "(:onlyAvailable is false or e.participantLimit < (select count(r) from Request as r " +
            "join r.event as ev " +
            "where e.id = ev.id and r.status = 'CONFIRMED')) ")
    Page<Event> findAll(@Param("text") String text,
                        @Param("categories") List<Integer> categories,
                        @Param("paid") Boolean paid,
                        @Param("rangeStart") LocalDateTime rangeStart,
                        @Param("rangeEnd") LocalDateTime rangeEnd,
                        @Param("onlyAvailable") Boolean onlyAvailable,
                        Pageable pageable);

    @Query("select e from Event as e " +
            "join e.initiator as i " +
            "join e.category as c " +
            "where (:usersId is null or i.id in :usersId) and (:stats is null or e.state in :stats) and " +
            "(:categories is null or c.id in :categories) and (cast(:rangeStart as date) is null or e.eventDate >= :rangeStart) and " +
            "(cast(:rangeEnd as date) is null or e.eventDate <= :rangeEnd)")
    Page<Event> findAll(@Param("usersId") List<Integer> usersId,
                        @Param("stats") List<State> stats,
                        @Param("categories") List<Integer> categories,
                        @Param("rangeStart") LocalDateTime rangeStart,
                        @Param("rangeEnd") LocalDateTime rangeEnd,
                        Pageable pageable);
}
