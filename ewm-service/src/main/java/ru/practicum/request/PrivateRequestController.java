package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping
    private List<ParticipationRequestDto> getAllById(@PathVariable("userId") Integer userId) {
        return requestService.findAllById(userId);
    }

    @PostMapping
    private ParticipationRequestDto create(@PathVariable("userId") Integer userId,
                                           @RequestParam("eventId") Integer eventId) {
        return requestService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    private ParticipationRequestDto editState(@PathVariable("userId") Integer userId,
                                              @PathVariable("requestId") Integer requestId) {
        return requestService.edit(userId, requestId);
    }
}
