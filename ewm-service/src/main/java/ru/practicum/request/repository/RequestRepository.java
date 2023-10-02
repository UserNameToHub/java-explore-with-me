package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.common.enumiration.State;
import ru.practicum.request.entity.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Boolean existsByRequesterIdAndEventId(Integer userId, Integer eventId);

    @Query("select count (r) from Request as r " +
            "join r.event as e " +
            "where e.id = :id and r.status = :status")
    Integer findConfirmedRequestsCount(@Param("id") Integer id,
                                       @Param("status") State status);

    @Query("select r from Request as r " +
            "join fetch r.requester as req " +
            "join fetch r.event as eve " +
            "join fetch eve.initiator as i " +
            "where req.id = :requesterId and i.id <> :requesterId")
    List<Request> findAllByUserId(@Param("requesterId") Integer requesterId);
}
