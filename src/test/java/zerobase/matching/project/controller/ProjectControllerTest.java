package zerobase.matching.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.matching.project.domain.Department;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.domain.ProjectOnOffline;
import zerobase.matching.project.dto.CreateProject;
import zerobase.matching.project.dto.ProjectDto;
import zerobase.matching.project.recruitment.domain.Recruitment;
import zerobase.matching.project.service.ProjectService;
import zerobase.matching.security.JwtAuthenticationFilter;
import zerobase.matching.security.JwtUtil;
import zerobase.matching.security.SecurityConfig;
import zerobase.matching.user.persist.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProjectController.class,
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)
})
public class ProjectControllerTest {

    @MockBean
    private ProjectService projectService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    void createUser () {
//        UserEntity userEntity = UserEntity.builder()
//                .userLoginId("userLoginId").password("password").nickname("nickname").email("email@zerobase.com")
//                .git("git@github.com").phoneNumber("01011112222").birthDate(LocalDate.ofEpochDay(1993-1-1)).gender(Gender.male)
//                .residence("Incheon").onOffline(OnOffline.both).portfolio("www.portfolio.com").role(Role.member)
//                .membershipLevel(MembershipLevel.beginner)
//                .build();
//
//        UserEntity user = userRepository.save(userEntity);
//
//    }

//    @AfterEach
//    void teardown(){
//        projectRepository.deleteAll();
//        userRepository.deleteAll();
//    }

    @Test
    @WithMockUser // 인증된 사용자를 만들지 않이도 test 요청 가능
    @DisplayName("게시글 생성 호출")
    public void createProjectTest() throws Exception {
        // given
        UserEntity user = UserEntity.builder().userId(1).build();
        Project project = Project.builder().projectId(1).build();
        Recruitment recruitment1 = Recruitment.builder().department(Department.backend).totalNum(3).build();
        List<Recruitment> recruitmentList = new ArrayList<>();
        recruitmentList.add(recruitment1);

        CreateProject.Request request = CreateProject.Request.builder()
                .userId(user.getUserId()).title("제목").content("내용").projectOnOffline(ProjectOnOffline.both)
                .place("Incheon").dueDate(LocalDate.of(2024,2,23))
                .recruitmentNum(1).departmentOne(Department.backend).totalNumOne(3)
                .build();
        ProjectDto response = ProjectDto.builder()
                        .projectId(project.getProjectId()).userId(user.getUserId())
                        .title("제목").content("내용").projectOnOffline(ProjectOnOffline.both.toString())
                        .place("Incheon").createdTime(LocalDateTime.now()).dueDate(LocalDate.of(2024,2,23))
                        .recruitmentNum(1).recruitmentList(recruitmentList)
                        .build();

        given(projectService.createProject(any())).willReturn(response); // request 가 아니라 any 를 써줘야함 (다른 객체로 인식한다, nullPointerException 뜸)

        // when & then
        mockMvc.perform(post("/board/projects")
                        .with(csrf()) // 이거 없으면 403 에러 뜸
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // 상태코드 200을 예상한다 (isCreated 는 상태코드 201)
                .andExpect(jsonPath("$.projectId").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.projectOnOffline").value(ProjectOnOffline.both.toString()))
                .andExpect(jsonPath("$.place").value("Incheon"))
                .andExpect(jsonPath("$.dueDate").value(LocalDate.of(2024,2,23).toString())) // toString 을 붙여줘야 에러 안남
                .andExpect(jsonPath("$.recruitmentNum").value(1))
                .andDo(print());


    }


}