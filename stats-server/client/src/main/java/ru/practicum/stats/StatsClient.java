package ru.practicum.stats;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.BaseClient;
import ru.practicum.commonDto.HitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class StatsClient extends BaseClient {
    public StatsClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<Object> create(HitDto hitDto) {
        return post("/hit", hitDto);
    }

    public ResponseEntity<Object> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        StringBuilder urisParam = new StringBuilder();

        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );

        uris.stream().forEach(uri -> {
            urisParam.append("&uris=" + "{" + uri + "}");
        });

        return get("/stats?start={start}&end={end}" + urisParam + "&unique={unique}", parameters);
    }
}