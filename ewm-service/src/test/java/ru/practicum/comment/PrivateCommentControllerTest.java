package ru.practicum.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.service.CommentService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrivateCommentController.class)
@ActiveProfiles("test")
class PrivateCommentControllerTest {
    private static final String REST_URL = "/users/{userId}/comments";

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    private static CommentFullDto commentFullDto;
    private static CommentDto normalCommentDto;
    private static CommentDto fullerTextCommentDto;
    private static CommentDto lessMinTextComment;
    private static CommentDto blankTextComment;
    private static CommentDto nullPositiveField;
    private static CommentDto nullTextField;

    @BeforeAll
    static void setUp() {

        commentFullDto = CommentFullDto.builder()
                .text("Some text for test.").build();

        normalCommentDto = CommentDto.builder()
                .text("Test comment text.")
                .isPositive(true)
                .build();

        fullerTextCommentDto = CommentDto.builder()
                .text("Описание событий – это один из важных элементов журналистского текста, который помогает читателю " +
                        "получить представление о происходящем. Каждый элемент описания должен быть продуман и структурирован, " +
                        "чтобы описание в целом было логичным и понятным. Перед началом написания описания нужно определить " +
                        "цель и аудиторию текста. Что нужно передать читателю и каким образом это сделать, чтобы описание " +
                        "было максимально понятным? Не забывайте учитывать возможный уровень сведений читателя по теме, " +
                        "о которой пишете. Следующий шаг – сформулировать главную мысль. Это должно быть самое важное и " +
                        "интересное, что происходит на мероприятии. Главная мысль должна быть краткой и содержательной," +
                        " чтобы заинтересовать читателя и показать, что происходящее имеет важность. При написании описания " +
                        "события старайтесь использовать живые и эмоциональные слова и формировать красочные образы. " +
                        "Необходимо описывать события в хронологическом порядке, чтобы у читателя не было путаницы. " +
                        "Используйте списки, таблицы и другие способы наглядности для описания фактов и деталей, " +
                        "чтобы сделать прочтение более увлекательным и понятным.")
                .isPositive(true)
                .build();

        lessMinTextComment = CommentDto.builder()
                .text("Описание")
                .isPositive(true)
                .build();

        blankTextComment = CommentDto.builder()
                .text("         ")
                .isPositive(true)
                .build();

        nullPositiveField = CommentDto.builder()
                .text("Описание событий – это...")
                .build();

        nullTextField = CommentDto.builder()
                .isPositive(true)
                .build();
    }

    @Test
    void testCreateWhenDtoIsOk() throws Exception {
        when(commentService.create(any(), any(), any()))
                .thenReturn(commentFullDto);

        mvc.perform(post(REST_URL + "/{eventId}", 1, 1)
                        .content(objectMapper.writeValueAsString(normalCommentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value(commentFullDto.getText()));
    }

    @Test
    void testCreateWhenLengthMore500() throws Exception {
        when(commentService.create(any(), any(), any()))
                .thenReturn(commentFullDto);

        mvc.perform(post(REST_URL + "/{eventId}", 1, 1)
                        .content(objectMapper.writeValueAsString(fullerTextCommentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateWhenLengthLess10() throws Exception {
        when(commentService.create(any(), any(), any()))
                .thenReturn(commentFullDto);

        mvc.perform(post(REST_URL + "/{eventId}", 1, 1)
                        .content(objectMapper.writeValueAsString(lessMinTextComment))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateWhenTextIsBlank() throws Exception {
        when(commentService.create(any(), any(), any()))
                .thenReturn(commentFullDto);

        mvc.perform(post(REST_URL + "/{eventId}", 1, 1)
                        .content(objectMapper.writeValueAsString(blankTextComment))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateWhenTextIsNull() throws Exception {
        when(commentService.create(any(), any(), any()))
                .thenReturn(commentFullDto);

        mvc.perform(post(REST_URL + "/{eventId}", 1, 1)
                        .content(objectMapper.writeValueAsString(nullTextField))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateWhenPositiveIsNull() throws Exception {
        when(commentService.create(any(), any(), any()))
                .thenReturn(commentFullDto);

        mvc.perform(post(REST_URL + "/{eventId}", 1, 1)
                        .content(objectMapper.writeValueAsString(nullPositiveField))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEdit() throws Exception {
        commentFullDto.setText("Updated text.");

        when(commentService.update(any(), any(), any()))
                .thenReturn(commentFullDto);

        mvc.perform(patch(REST_URL + "/{commentId}", 1, 1)
                        .content(objectMapper.writeValueAsString(normalCommentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(commentFullDto.getText()));
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(delete(REST_URL + "/{commentId}", 1, 1)
                        .content(objectMapper.writeValueAsString(normalCommentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(print());

        verify(commentService, times(1))
                .delete(1, 1);
    }
}