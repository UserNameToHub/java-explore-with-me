package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.common.exception.NotFoundException;
import ru.practicum.mapper.ModelMapper;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.entity.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

import static ru.practicum.util.Constants.ORDER_BY_ID_ASC;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> findAll(List<Integer> ids, Integer from, Integer size) {
        log.info("Get users with ids {}", ids);
        return modelMapper.doListMapping(userRepository.findAll(ids,
                PageRequest.of(from, size, ORDER_BY_ID_ASC)).toList(), UserDto.class);
    }

    @Override
    @Transactional
    public UserDto create(NewUserRequest newUserDto) {
        log.info("Creating user with name {} and email {}", newUserDto.getName(), newUserDto.getEmail());
        return modelMapper.doMapping(userRepository.save(modelMapper.doMapping(newUserDto, new User())),
                UserDto.builder().build());
    }

    @Override
    @Transactional
    public void delete(Integer userId) {
        log.info("Deleting user with id {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id={}} was not found", userId))
        );

        userRepository.delete(user);
    }
}