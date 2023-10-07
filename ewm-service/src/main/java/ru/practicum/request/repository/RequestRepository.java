package ru.practicum.request.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.common.enumiration.State;
import ru.practicum.event.entity.Event;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.entity.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("select r from Request as r " +
            "join r.event as e " +
            "where e.id = :eventId and r.id in :ids")
    List<Request> findAllByIdInAndEventId(List<Integer> ids, Integer eventId);

    @Query("select new ru.practicum.request.dto.ParticipationRequestDto(r.created, e.id, r.id, u.id, r.status) " +
            "from Request as r " +
            "join r.requester as u " +
            "join r.event as e " +
            "join e.initiator as i " +
            "where i.id = :userId and e.id = :eventId")
    Page<ParticipationRequestDto> findAllByRequesterIdAndEventId(@Param("userId") Integer userId,
                                                                 @Param("eventId") Integer eventId,
                                                                 Pageable pageable);

    @Query("select new ru.practicum.request.dto.ParticipationRequestDto(r.created, e.id, r.id, u.id, r.status) " +
            "from Request as r " +
            "join r.requester as u " +
            "join r.event as e " +
            "where e.id = :eventId and r.status = :state")
    Page<ParticipationRequestDto> findAllByEventIdAndStatus(@Param("eventId") Integer eventId,
                                                            @Param("state") State state,
                                                            Pageable pageable);

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
