package ru.practicum.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.JSONException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.BaseClient;
import ru.practicum.event.dto.HitGettingDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.util.Constants.TIME_PATTERN;

public class StatsClient extends BaseClient {
    public StatsClient(RestTemplate rest) {
        super(rest);
    }

    public void create(String app, String uri, String ip, LocalDateTime timestamp) {
        JSONObject hitJsonObject = new JSONObject();
        try {
            hitJsonObject.put("app", app);
            hitJsonObject.put("uri", uri);
            hitJsonObject.put("ip", ip);
            hitJsonObject.put("timestamp", timestamp.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        post("/hit", hitJsonObject.toString(), String.class);
    }

    public List<HitGettingDto> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) throws JSONException {
        Map<String, Object> parameters = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        parameters.put("start", start.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        parameters.put("end", end.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
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

        List<Object> objects = get(url, parameters, Object.class).getBody();
        List<HitGettingDto> hitGettingDtos = objects.stream()
                .map(e -> mapper.convertValue(e, HitGettingDto.class))
                .collect(Collectors.toList());

        return hitGettingDtos;
    }
}