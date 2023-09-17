package ru.practicum.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    @GetMapping
    public List<UserDto> getAll(@RequestParam(value = "ids", required = false) List<Integer> ids,
                                @RequestParam(value = "from", defaultValue = "0") Integer from,
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return null;
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserShortDto userShor) {
        return null;
    }

    @DeleteMapping("/{userId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") Integer userId) {
    }
}
