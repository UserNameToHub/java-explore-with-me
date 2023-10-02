package ru.practicum.event;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.client.BaseClient;
import ru.practicum.event.dto.HitGettingDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.practicum.util.Constants.TIME_PATTERN;

public class StatsClient extends BaseClient {
    public StatsClient(RestTemplate rest) {
        super(rest);
    }

    public void create(String app, String uri, String ip, LocalDateTime timestamp) throws JSONException {
        JSONObject hitJsonObject = new JSONObject();
        hitJsonObject.put("app", app);
        hitJsonObject.put("uri", uri);
        hitJsonObject.put("ip", ip);
        hitJsonObject.put("timestamp", timestamp.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
//
//        HttpEntity<String> request = new HttpEntity<>(hitJsonObject.toString(), headers);
//        post("/hit", request, Void.class);
        post("/hit", hitJsonObject.toString(), Void.class);
    }

    public List<HitGettingDto> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique,
                                      HttpServletRequest request) throws JSONException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start", start.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        parameters.put("end", end.format(DateTimeFormatter.ofPattern(TIME_PATTERN)));
        parameters.put("unique", unique);

        String url = "/stats?start={start}&end={end}&unique{unique}";

        if (uris != null) {
            parameters.put("uris", uris);
            url = "/stats?start={start}&end={end}&uris={uris}&unique{unique}";
        }

        List<HitGettingDto> hitGettingDtos = get(url, parameters, HitGettingDto.class).getBody();

        if (hitGettingDtos.isEmpty()) {
            return hitGettingDtos;
        }

        hitGettingDtos.stream().forEach(e ->
        {
            try {
                create("ewm-service", e.getUri(), request.getRemoteAddr(), LocalDateTime.now());
            } catch (JSONException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });

        return hitGettingDtos;
    }
}