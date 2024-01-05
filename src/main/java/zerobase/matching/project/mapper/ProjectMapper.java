//package zerobase.matching.mapper;
//
//import java.util.List;
//import org.mapstruct.Mapper;
//import org.springframework.context.annotation.Primary;
//import zerobase.matching.domain.Project;
//import zerobase.matching.dto.ProjectResponseDto;
//
//@Mapper(componentModel = "spring")
//public interface ProjectMapper {
//
//  default ProjectResponseDto projectToProjectResponseDto(Project project) {
//    ProjectResponseDto projectResponseDto = new ProjectResponseDto();
//    projectResponseDto.setProjectId(project.getProjectId());
//    projectResponseDto.setUserId(project.getUser().getUserId());
//    projectResponseDto.setTitle(project.getTitle());
//    projectResponseDto.setContent(project.getContent());
//    projectResponseDto.setPlace(project.getPlace());
//    projectResponseDto.setNumberOfRecruit(project.getNumberOfRecruit());
//    projectResponseDto.setCurrentRecruit(project.getCurrentRecruit());
//    projectResponseDto.setProjectOnOffline(project.getProjectOnOffline());
//    projectResponseDto.setCreateTime(project.getCreateTime());
//    projectResponseDto.setUpdateTime(project.getUpdateTime());
//    projectResponseDto.setDueDate(project.getDueDate());
//
//    return projectResponseDto;
//  }
//
//  // List 타입으로 사용
//  List<ProjectResponseDto> projectsToProjectResponseDtos(List<Project> projects);
//
//}
