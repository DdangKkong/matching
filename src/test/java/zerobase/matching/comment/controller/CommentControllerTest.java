package zerobase.matching.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.matching.comment.repository.CommentRepository;
import zerobase.matching.comment.service.CommentService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;




}