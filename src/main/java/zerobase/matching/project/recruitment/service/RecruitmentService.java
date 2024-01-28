package zerobase.matching.project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.dto.UpdateProject;
import zerobase.matching.project.mapping.domain.MappingProjectRecruit;
import zerobase.matching.project.mapping.repository.MappingRepository;
import zerobase.matching.project.recruitment.domain.Recruitment;
import zerobase.matching.project.recruitment.dto.*;
import zerobase.matching.project.recruitment.repository.RecruitmentRepository;
import zerobase.matching.project.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

  private final RecruitmentRepository recruitmentRepository;

  private final ProjectRepository projectRepository;

  private final MappingRepository mappingRepository;

  // 모집 현황 작성 ( 구인 게시글 작성시 함께 만들어짐 )
    // 모집 현황 1개 일 때
  public List<Recruitment> createRecruitmentOne(RequestRecruitmentOne.Request request) {
    List<Recruitment> recruitmentList = new ArrayList<>();

    Recruitment recruitment = recruitmentRepository.save(
            Recruitment.builder()
                    .department(request.getDepartmentOne())
                    .totalNum(request.getTotalNumOne())
                    .currentNum(0)
                    .build()
    );
    recruitmentList.add(recruitment);
    return recruitmentList;
  }
  // 모집 현황 2개 일 때
  public List<Recruitment> createRecruitmentTwo(RequestRecruitmentTwo.Request request) {
    Recruitment recruitmentOne =  recruitmentRepository.save(Recruitment.builder()
                                                      .department(request.getDepartmentOne())
                                                      .totalNum(request.getTotalNumOne())
                                                      .currentNum(0)
                                                      .build());
    Recruitment recruitmentTwo =  recruitmentRepository.save(Recruitment.builder()
                                                      .department(request.getDepartmentTwo())
                                                      .totalNum(request.getTotalNumTwo())
                                                      .currentNum(0)
                                                      .build());


    List<Recruitment> recruitmentList = new ArrayList<>();
    recruitmentList.add(recruitmentOne);
    recruitmentList.add(recruitmentTwo);

    return recruitmentList;
  }
  // 모집 현황 3개 일 때
  public List<Recruitment> createRecruitmentThr(RequestRecruitmentThr.Request request) {
    Recruitment recruitmentOne =  recruitmentRepository.save(Recruitment.builder()
                                                      .department(request.getDepartmentOne())
                                                      .totalNum(request.getTotalNumOne())
                                                      .currentNum(0)
                                                      .build());
    Recruitment recruitmentTwo =  recruitmentRepository.save(Recruitment.builder()
                                                      .department(request.getDepartmentTwo())
                                                      .totalNum(request.getTotalNumTwo())
                                                      .currentNum(0)
                                                      .build());
    Recruitment recruitmentThr =  recruitmentRepository.save(Recruitment.builder()
                                                      .department(request.getDepartmentThr())
                                                      .totalNum(request.getTotalNumThr())
                                                      .currentNum(0)
                                                      .build());

    List<Recruitment> recruitmentList = new ArrayList<>();
    recruitmentList.add(recruitmentOne);
    recruitmentList.add(recruitmentTwo);
    recruitmentList.add(recruitmentThr);

    return recruitmentList;
  }


  // 모집 현황 수정
    // 모집 현황 1개일 때
  public List<Recruitment> updateRecruitmentOne(UpdateProject.Request request) {

    Project project = getProject(request.getProjectId());
    List<MappingProjectRecruit> List = mappingRepository.findAllByProjectProjectId(request.getProjectId());

    // 모집 현황 리스트 불러옴
    List<Recruitment> recruitmentList = getRecruitmentList(project, List);

    Recruitment recruitment1 = recruitmentList.get(0);

    // 수정된 모집인원이 현재 모집된 인원보다 적은경우 에러
    if (request.getTotalNumOne() < recruitment1.getCurrentNum()) {
      throw new RuntimeException("모집인원이 모집된 인원보다 적습니다");
    }

    recruitmentList.clear();
    recruitment1.setDepartment(request.getDepartmentOne());
    recruitment1.setTotalNum(request.getTotalNumOne());
    recruitmentList.add(recruitment1);

    return recruitmentList;

  }
  // 모집 현황 2개일 때
  public List<Recruitment> updateRecruitmentTwo(UpdateProject.Request request) {

    Project project = getProject(request.getProjectId());
    List<MappingProjectRecruit> List = mappingRepository.findAllByProjectProjectId(request.getProjectId());

    // 모집 현황 리스트 불러옴
    List<Recruitment> recruitmentList = getRecruitmentList(project, List);
    Recruitment recruitment1 = recruitmentList.get(0);
    Recruitment recruitment2 = recruitmentList.get(1);

    // 수정된 모집인원이 현재 모집된 인원보다 적은경우 에러
    if (request.getTotalNumOne() < recruitment1.getCurrentNum()
        || request.getTotalNumTwo() < recruitment2.getCurrentNum()) {
      throw new RuntimeException("모집인원이 모집된 인원보다 적습니다");
    }

    recruitmentList.clear();
    recruitment1.setDepartment(request.getDepartmentOne());
    recruitment1.setTotalNum(request.getTotalNumOne());
    recruitmentList.add(recruitment1);
    recruitment2.setDepartment(request.getDepartmentTwo());
    recruitment2.setTotalNum(request.getTotalNumTwo());
    recruitmentList.add(recruitment2);

    return recruitmentList;

  }
  // 모집 현황 3개일 때
  public List<Recruitment> updateRecruitmentThr(UpdateProject.Request request) {

    Project project = getProject(request.getProjectId());
    List<MappingProjectRecruit> List = mappingRepository.findAllByProjectProjectId(request.getProjectId());

    // 모집 현황 리스트 불러옴
    List<Recruitment> recruitmentList = getRecruitmentList(project, List);
    Recruitment recruitment1 = recruitmentList.get(0);
    Recruitment recruitment2 = recruitmentList.get(1);
    Recruitment recruitment3 = recruitmentList.get(2);

    // 수정된 모집인원이 현재 모집된 인원보다 적은경우 에러
    if (request.getTotalNumOne() < recruitment1.getCurrentNum()
        || request.getTotalNumTwo() < recruitment2.getCurrentNum()
        || request.getTotalNumThr() < recruitment3.getCurrentNum()) {
      throw new RuntimeException("모집인원이 모집된 인원보다 적습니다");
    }

    recruitmentList.clear();
    recruitment1.setDepartment(request.getDepartmentOne());
    recruitment1.setTotalNum(request.getTotalNumOne());
    recruitmentList.add(recruitment1);
    recruitment2.setDepartment(request.getDepartmentTwo());
    recruitment2.setTotalNum(request.getTotalNumTwo());
    recruitmentList.add(recruitment2);
    recruitment3.setDepartment(request.getDepartmentThr());
    recruitment3.setTotalNum(request.getTotalNumThr());
    recruitmentList.add(recruitment3);

    return recruitmentList;

  }

  // 모집 현황 삭제
    // 모집 현황 1개일 때
  public List<Recruitment> DeleteRecruitmentOne(List<Recruitment> recruitmentList, List<MappingProjectRecruit> List){
    mappingRepository.delete(List.get(0));
    Recruitment recruitment1 = recruitmentList.get(0);
    recruitmentRepository.delete(recruitment1);
    recruitmentList.clear();
    return recruitmentList;
  }
  // 모집 현황 2개일 때
  public List<Recruitment> DeleteRecruitmentTwo(List<Recruitment> recruitmentList, List<MappingProjectRecruit> List){
    mappingRepository.delete(List.get(0));
    mappingRepository.delete(List.get(1));
    Recruitment recruitment1 = recruitmentList.get(0);
    Recruitment recruitment2 = recruitmentList.get(1);
    recruitmentRepository.delete(recruitment1);
    recruitmentRepository.delete(recruitment2);
    recruitmentList.clear();
    return recruitmentList;
  }
  // 모집 현황 3개일 때
  public List<Recruitment> DeleteRecruitmentThr(List<Recruitment> recruitmentList, List<MappingProjectRecruit> List){
    mappingRepository.delete(List.get(0));
    mappingRepository.delete(List.get(1));
    mappingRepository.delete(List.get(2));
    Recruitment recruitment1 = recruitmentList.get(0);
    Recruitment recruitment2 = recruitmentList.get(1);
    Recruitment recruitment3 = recruitmentList.get(3);
    recruitmentRepository.delete(recruitment1);
    recruitmentRepository.delete(recruitment2);
    recruitmentRepository.delete(recruitment3);
    recruitmentList.clear();
    return recruitmentList;
  }

  // 모집 인원에서 현재 인원 추가 ( 구인 게시글에서 팀원을 뽑았을 때 )
  public RecruitmentDto plusMember(PlusMember.Request request) {
    Project project = getProject(request.getProjectId());
    List<MappingProjectRecruit> List = mappingRepository.findAllByProjectProjectId(request.getProjectId());

    // 모집 현황 리스트 불러옴
    List<Recruitment> recruitmentList = getRecruitmentList(project, List);

    int recruitmentNum = project.getRecruitmentNum();
    Recruitment recruitment = new Recruitment();
    int indexNum = -1;

    for (int i = 0; i < recruitmentNum; i++) {
      Recruitment recruitmentFromList = recruitmentList.get(i);
      if (recruitmentFromList.getDepartment().equals(request.getDepartment())) {
        recruitment = recruitmentFromList;
        indexNum = i;
      }
    }

    // department 가 일치하는 모집 현황이 없으면 에러 발생
    if (indexNum == -1) {
      throw new RuntimeException("해당 구인 글에서 일치하는 모집군이 없습니다.");
    }

    int currentNum = recruitment.getCurrentNum();

    // 현재 인원이 모집하려는 총 인원보다 많을 수 없음
    if (recruitment.getTotalNum() == currentNum) {
      throw new RuntimeException("모집 인원이 이미 다 찼습니다.");
    }

    currentNum++;
    recruitment.setCurrentNum(currentNum);
    recruitmentRepository.save(recruitment);
    project.setUpdateTime(LocalDateTime.now());
    projectRepository.save(project);

    return RecruitmentDto.fromEntity(recruitment);

  }

  // 모집 인원에서 현재 인원 빼기 ( 구인 게시글에서 팀원 뽑은것을 취소할 때 )
  public RecruitmentDto MinusMember(MinusMember.Request request) {
    Project project = getProject(request.getProjectId());
//    List<Integer> recruitmentIdList = mappingRepository.findAllByProjectProjectId(request.getProjectId());
    List<MappingProjectRecruit> List = mappingRepository.findAllByProjectProjectId(request.getProjectId());

    // 모집 현황 리스트 불러옴
    List<Recruitment> recruitmentList = getRecruitmentList(project, List);

    int recruitmentNum = project.getRecruitmentNum();
    Recruitment recruitment = new Recruitment();
    int indexNum = -1;

    for (int i = 0; i < recruitmentNum; i++) {
      Recruitment recruitmentFromList = recruitmentList.get(i);
      if (recruitmentFromList.getDepartment().equals(request.getDepartment())) {
        recruitment = recruitmentFromList;
        indexNum = i;
      }
    }

    // department 가 일치하는 모집 현황이 없으면 에러 발생
    if (indexNum == -1) {
      throw new RuntimeException("해당 구인 글에서 일치하는 모집군이 없습니다.");
    }

    int currentNum = recruitment.getCurrentNum();

    // 현재 인원이 0 보다 작을 수 없음
    if (currentNum == 0) {
      throw new RuntimeException("모집 인원은 음수가 될 수 없습니다.");
    }

    currentNum--;
    recruitment.setCurrentNum(currentNum);
    recruitmentRepository.save(recruitment);
    project.setUpdateTime(LocalDateTime.now());
    projectRepository.save(project);

    return RecruitmentDto.fromEntity(recruitment);

  }

  private List<Recruitment> getRecruitmentList(Project project, List<MappingProjectRecruit> List) {
    List<Recruitment> recruitmentList = new ArrayList<>();
    if (project.getRecruitmentNum() == 1) {
      int recruitmentId1 = List.get(0).getRecruitment().getRecruitmentId();
      Recruitment recruitment1 = getRecruitment(recruitmentId1);
      recruitmentList.add(recruitment1);
    } else if (project.getRecruitmentNum() == 2) {
      int recruitmentId1 = List.get(0).getRecruitment().getRecruitmentId();
      int recruitmentId2 = List.get(1).getRecruitment().getRecruitmentId();
      Recruitment recruitment1 = getRecruitment(recruitmentId1);
      Recruitment recruitment2 = getRecruitment(recruitmentId2);
      recruitmentList.add(recruitment1);
      recruitmentList.add(recruitment2);
    } else if (project.getRecruitmentNum() == 3) {
      int recruitmentId1 = List.get(0).getRecruitment().getRecruitmentId();
      int recruitmentId2 = List.get(1).getRecruitment().getRecruitmentId();
      int recruitmentId3 = List.get(2).getRecruitment().getRecruitmentId();
      Recruitment recruitment1 = getRecruitment(recruitmentId1);
      Recruitment recruitment2 = getRecruitment(recruitmentId2);
      Recruitment recruitment3 = getRecruitment(recruitmentId3);
      recruitmentList.add(recruitment1);
      recruitmentList.add(recruitment2);
      recruitmentList.add(recruitment3);
    }

    return recruitmentList;
  }

  private Project getProject(int projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("프로젝트 구인 글의 정보가 알맞지 않습니다."));
  }

  private Recruitment getRecruitment(int recruitmentId) {
    return recruitmentRepository.findById(recruitmentId)
            .orElseThrow(() -> new RuntimeException("모집 현황이 없습니다."));
  }

}





