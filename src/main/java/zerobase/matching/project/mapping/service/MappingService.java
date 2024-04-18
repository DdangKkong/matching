package zerobase.matching.project.mapping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.mapping.domain.MappingProjectRecruit;
import zerobase.matching.project.mapping.repository.MappingRepository;
import zerobase.matching.project.recruitment.domain.Recruitment;
import zerobase.matching.project.recruitment.repository.RecruitmentRepository;
import zerobase.matching.project.repository.ProjectRepository;
import zerobase.matching.user.exception.CustomException;
import zerobase.matching.user.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class MappingService {

  private final RecruitmentRepository recruitmentRepository;

  private final ProjectRepository projectRepository;

  private final MappingRepository mappingRepository;

  // 매핑 하기
  public void createMapping(int projectId, int recruitmentId){
    Project project = getProject(projectId);
    Recruitment recruitment = getRecruitment(recruitmentId);

    mappingRepository.save(
            MappingProjectRecruit.builder()
                    .project(project)
                    .recruitment(recruitment)
                    .build());
  }


  private Project getProject(int projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECTID_INVALID));
  }

  private Recruitment getRecruitment(int recruitmentId) {
    return recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new CustomException(ErrorCode.RECRUITMENTID_INVALID));
  }

}





