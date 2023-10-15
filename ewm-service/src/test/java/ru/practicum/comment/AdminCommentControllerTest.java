package ru.practicum.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.comment.dto.CommentFullDto;
import ru.practicum.comment.service.CommentService;
import ru.practicum.common.enumiration.State;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminCommentController.class)
@ActiveProfiles("test")
class AdminCommentControllerTest {
    private static final String REST_URL = "/admin/comments/{commentId}";

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mvc;

    private static CommentFullDto commentFull;

    @BeforeEach
    void setUp() {
        commentFull = CommentFullDto.builder()
                .id(1)
                .state(State.CONFIRMED)
                .createdOn(LocalDateTime.now())
                .isPositive(true)
                .text("Some tesding text.")
                .build();
    }

    @Test
    public void testPatchWhenPatternStateIsCorrect() throws Exception {
        when(commentService.update(1, State.CONFIRMED))
                .thenReturn(commentFull);

        mvc.perform(patch(REST_URL + "/confirmation", 1)
                        .param("state", "CONFIRMED")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testPatchWhenParamIsNull() throws Exception {
        when(commentService.update(1, State.CONFIRMED))
                .thenReturn(commentFull);

        mvc.perform(patch(REST_URL + "/confirmation", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPatchWhenPatternStateIsNotCorrect() throws Exception {
        when(commentService.update(1, State.CONFIRMED))
                .thenReturn(commentFull);

        mvc.perform(patch(REST_URL + "/confirmation", 1)
                        .param("state", "Some")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}