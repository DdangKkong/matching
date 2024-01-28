package zerobase.matching.project.mapping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.mapping.domain.MappingProjectRecruit;
import zerobase.matching.project.mapping.repository.MappingRepository;
import zerobase.matching.project.recruitment.domain.Recruitment;
import zerobase.matching.project.recruitment.repository.RecruitmentRepository;
import zerobase.matching.project.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class MappingService {

  private final RecruitmentRepository recruitmentRepository;

  private final ProjectRepository projectRepository;

  private final MappingRepository mappingRepository;

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
        .orElseThrow(() -> new RuntimeException("프로젝트 구인 글의 정보가 알맞지 않습니다."));
  }

  private Recruitment getRecruitment(int recruitmentId) {
    return recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new RuntimeException("모집현황의 정보가 알맞지 않습니다."));
  }

}





