package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.entity.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.dto.EventCommentDto;
import ru.practicum.comment.entity.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.common.enumiration.State;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.event.entity.Event;
import ru.practicum.event.entity.Location;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.request.entity.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.entity.User;
import ru.practicum.user.repository.UserRepository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql"})
class CommentServiceImplTest {
    @Autowired
    private EntityManager em;

    @MockBean
    private RequestRepository requestRepo;

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private CommentService comServ;

    private CommentDto commentDto;
    private Comment comment;
    private User owner;
    private User initiator;
    private User requester;
    private Category category;
    private Location location;
    private Event event;
    private Event eventAfterNow;
    private Request request;

    @BeforeEach
    void setUp() {
        User ownerNotSaved = User.builder()
                .name("Some owner comments")
                .email("someEmail@yandex.ru")
                .build();

        User initiatorNotSaved = User.builder()
                .name("Some initiator")
                .email("someEmail@yandex.ru")
                .build();

        requester = User.builder()
                .name("Some requester")
                .email("someEmail@yandex.ru")
                .build();

        Category categoryNotSaved = Category.builder()
                .name("Some category")
                .build();

        Location locationNotSaved = Location.builder()
                .lat(12.2f)
                .lon(22.3f)
                .build();

        owner = userRepo.save(ownerNotSaved);
        initiator = userRepo.save(initiatorNotSaved);
        category = categoryRepo.save(categoryNotSaved);
        location = locationRepo.save(locationNotSaved);

        Event eventNotSaved = Event.builder()
                .eventDate(LocalDateTime.of(
                        2023, 03, 20, 12, 50, 00))
                .createdOn(LocalDateTime.of(
                        2023, 02, 20, 12, 50, 00))
                .paid(true)
                .category(category)
                .participantLimit(300)
                .initiator(initiator)
                .location(location)
                .annotation("Some annotation.")
                .description("Some description.")
                .title("Some title")
                .state(State.PUBLISHED)
                .publishedOn(LocalDateTime.of(
                        2023, 02, 20, 14, 50, 00))
                .requestModeration(true)
                .build();

        Event eventNotSavedAfterNow = Event.builder()
                .eventDate(LocalDateTime.of(
                        2024, 03, 20, 12, 50, 00))
                .createdOn(LocalDateTime.of(
                        2023, 02, 20, 12, 50, 00))
                .paid(true)
                .category(category)
                .participantLimit(300)
                .initiator(initiator)
                .location(location)
                .annotation("Some annotation.")
                .description("Some description.")
                .title("Some title")
                .state(State.PUBLISHED)
                .publishedOn(LocalDateTime.of(
                        2023, 02, 20, 14, 50, 00))
                .requestModeration(true)
                .build();

        commentDto = CommentDto.builder()
                .text("Text for new comment.")
                .isPositive(true)
                .build();

        request = Request.builder()
                .id(1)
                .requester(requester)
                .event(event)
                .status(State.CONFIRMED)
                .created(LocalDateTime.of(
                        2023, 02, 20, 12, 50, 00))
                .build();


        event = eventRepo.save(eventNotSaved);
        eventAfterNow = eventRepo.save(eventNotSavedAfterNow);

        Comment commentNotSaved = Comment.builder()
                .text("Some test for a comment from some user.")
                .isPositive(true)
                .owner(owner)
                .event(event)
                .state(State.CONFIRMED)
                .createdOn(LocalDateTime.of(
                        2023, 02, 20, 12, 50, 00))
                .publishedOn(LocalDateTime.of(
                        2023, 02, 20, 18, 50, 00))
                .build();

        comment = commentRepo.save(commentNotSaved);
    }

    @Test
    void testCreate() {
        doReturn(true).when(requestRepo).existsByEventIdAndRequesterIdAndStatusIs(anyInt(), anyInt(), any());
        Integer id = initiator.getId();

        CommentFullDto commentFullDto = comServ.create(commentDto, initiator.getId(), event.getId());

        Comment commentDB = em.createQuery("select c from Comment as c where c.id = :id", Comment.class)
                .setParameter("id", commentFullDto.getId()).getSingleResult();

        assertThat(commentDB.getId(), notNullValue());
        assertThat(commentDB.getText(), equalTo("Text for new comment."));
        assertThat(commentDB.getIsPositive(), equalTo(true));
    }

    @Test
    void testCreateWhenUserIsNotParticipant() {
        ConflictException ce = assertThrows(ConflictException.class, () ->
                comServ.create(commentDto, 1, 100));

        assertEquals("User with id 1 is not a participant in the event with id 100", ce.getMessage());
    }

    @Test
    void testCreateWhenEventIsNotOver() {
        when(requestRepo.existsByEventIdAndRequesterIdAndStatusIs(any(), any(), any()))
                .thenReturn(true);

        ConflictException ce = assertThrows(ConflictException.class, () ->
                comServ.create(commentDto, owner.getId(), eventAfterNow.getId()));

        assertEquals(String.format("Event with id %d is not over yet.", eventAfterNow.getId()), ce.getMessage());
    }

    @Test
    void testCreateWhenUserIsNotFound() {
        when(requestRepo.existsByEventIdAndRequesterIdAndStatusIs(any(), any(), any()))
                .thenReturn(true);

        ConflictException ce = assertThrows(ConflictException.class, () ->
                comServ.create(commentDto, 1000, event.getId()));

        assertEquals(String.format("User with id %d is not found.", 1000), ce.getMessage());
    }

    @Test
    void testUpdateWhenCommentNotFound() {
        UserRepository mock = mock(UserRepository.class);
        when(mock.existsById(any()))
                .thenReturn(true);

        ConflictException ce = assertThrows(ConflictException.class, () ->
                comServ.update(commentDto, 1, 100));

        assertEquals(String.format("Comment with id %d is not found.", 100), ce.getMessage());
    }

    @Test
    void testUpdateWhenUserIsNotOwner() {
        UserRepository mock = mock(UserRepository.class);
        when(mock.existsById(any()))
                .thenReturn(true);

        ConflictException ce = assertThrows(ConflictException.class, () ->
                comServ.update(commentDto, initiator.getId(), comment.getId()));
        assertEquals(String.format("User with id %d is not a owner for comment with id %d.", initiator.getId(), comment.getId()),
                ce.getMessage());
    }

    @Test
    void testUpdateWhenEditDateIsOver() {
        UserRepository mock = mock(UserRepository.class);
        when(mock.existsById(any()))
                .thenReturn(true);

        ConflictException ce = assertThrows(ConflictException.class, () ->
                comServ.update(commentDto, owner.getId(), comment.getId()));
        assertEquals(String.format("Edit time is over.", initiator.getId(), comment.getId()),
                ce.getMessage());
    }

    @Test
    void testUpdateByOwner() {
        UserRepository mock = mock(UserRepository.class);
        when(mock.existsById(any()))
                .thenReturn(true);

        Comment commentLocal = Comment.builder()
                .text("Some test for a comment from some user.")
                .isPositive(true)
                .owner(owner)
                .event(event)
                .state(State.CONFIRMED)
                .createdOn(LocalDateTime.now().minusDays(3))
                .publishedOn(LocalDateTime.now())
                .build();

        CommentDto commentDtoLocal = CommentDto.builder()
                .text("Updating text.")
                .build();


        Comment savedCommentLocal = commentRepo.save(commentLocal);

        CommentFullDto updatedComment = comServ.update(commentDtoLocal, owner.getId(), savedCommentLocal.getId());
        assertThat(updatedComment.getText(), equalTo("Updating text."));
    }

    @Test
    void deleteWhenUserIsNotOwner() {
        ConflictException ce = assertThrows(ConflictException.class, () ->
                comServ.delete(1000, comment.getId()));

        assertEquals(String.format("User with id %d is not a owner for comment with id %d.", 1000, comment.getId()),
                ce.getMessage());
    }


    @Test
    void testUpdateByAdmin() {
        Comment commentLocal = Comment.builder()
                .text("Some test for a comment from some user.")
                .isPositive(true)
                .owner(owner)
                .event(event)
                .state(State.PENDING)
                .createdOn(LocalDateTime.now().minusDays(3))
                .publishedOn(LocalDateTime.now())
                .build();

        Comment savedComment = commentRepo.save(commentLocal);
        CommentFullDto updatedComment = comServ.update(savedComment.getId(), State.CONFIRMED);

        assertThat(updatedComment.getState(), equalTo(State.CONFIRMED));
    }

    @Test
    void testGetAll() {
        EventCommentDto allComment = comServ.getAll(owner.getId(), comment.getId());

        assertThat(allComment.getComments().size(), equalTo(1));
    }
}