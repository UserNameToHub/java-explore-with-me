package ru.practicum.request;

import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;

@RestController
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    @GetMapping
    private ParticipationRequestDto getAllById(@PathVariable("userId") Integer userId) {
        return null;
    }

    @PostMapping
    private ParticipationRequestDto create(@PathVariable("userId") Integer userId,
                                           @RequestParam("eventId") Integer eventId) {
        return null;
    }

    @PatchMapping("/{requestId}/cancel")
    private ParticipationRequestDto editState(@PathVariable("userId") Integer userId,
                                              @PathVariable("requestId") Integer requestId) {
        return null;
    }
}
