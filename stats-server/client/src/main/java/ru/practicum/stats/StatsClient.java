package ru.practicum.stats;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.BaseClient;
import ru.practicum.commonDto.HitDto;
import ru.practicum.commonDto.HitGettingDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StatsClient extends BaseClient {
    public StatsClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<String> create(HitDto hitDto) {
        return post("/hit", hitDto, String.class);
    }

    public ResponseEntity<List<HitGettingDto>> getAll(String start, String end, List<String> uris, boolean unique) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start);
        parameters.put("end", end);
        parameters.put("unique", unique);

        String url = "/stats?start={start}&end={end}&unique{unique}";

        if (Objects.nonNull(uris)) {
            if (uris.size() == 1) {
                parameters.put("uris", uris.get(0));
            } else {
                parameters.put("uris", uris);
            }
            url = "/stats?start={start}&end={end}&uris={uris}&unique{unique}";

        }

        return get(url, parameters, HitGettingDto.class);
    }
}