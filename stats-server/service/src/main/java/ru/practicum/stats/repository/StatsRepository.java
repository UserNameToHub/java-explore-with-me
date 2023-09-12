package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.commonDto.HitGettingDto;
import ru.practicum.stats.entity.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("select new ru.practicum.commonDto.HitGettingDto(eh.app, " +
            "eh.uri, " +
            "case when :unique = true then count(distinct eh.ip) else count(eh.ip) end) " +
            "from EndpointHit as eh " +
            "where eh.timestamp between :start and :end and eh.uri in :uris " +
            "group by eh.uri, eh.uri")
    List<HitGettingDto> findAll(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end,
                                @Param("uris") Collection<String> uris,
                                @Param("unique") boolean unique);
}