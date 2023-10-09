package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.commonDto.HitGettingDto;
import ru.practicum.stats.entity.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query("select new ru.practicum.commonDto.HitGettingDto(eh.app, " +
            "eh.uri, " +
            "case when :unique = true then count(distinct eh.ip) else count(eh.ip) end) " +
            "from EndpointHit as eh " +
            "where eh.timestamp between :start and :endDate and :uris is null or eh.uri in (:uris) " +
            "group by eh.app, eh.uri " +
            "order by 3 desc")
    List<HitGettingDto> findAllStats(@Param("start") LocalDateTime start,
                                     @Param("endDate") LocalDateTime end,
                                     @Param("uris") Collection<String> uris,
                                     @Param("unique") Boolean unique);
    @Query("select count (h) from EndpointHit as h " +
            "where h.uri in :uris")
    boolean existsByUriIn(@Param("uris") Collection<String> uris);
}