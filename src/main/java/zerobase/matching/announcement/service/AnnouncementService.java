package zerobase.matching.announcement.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import zerobase.matching.announcement.controller.AnnouncementController;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.repository.ProjectRepository;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;

  // 클라이언트에서 서버의 이벤트를 구독하기 위한 요청을 보냄, user 가 생성될 때 실행하면 될 듯 하다
  public SseEmitter subscribe(int userId) {

    // 객체 생성
    SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

    // 연결
    try {
      sseEmitter.send(SseEmitter.event().name("connect"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 저장
    AnnouncementController.sseEmitterMap.put(userId, sseEmitter);

    // 연결 종료
    sseEmitter.onCompletion(() -> AnnouncementController.sseEmitterMap.remove(userId));
    sseEmitter.onTimeout(() -> AnnouncementController.sseEmitterMap.remove(userId));
    sseEmitter.onError((e) -> AnnouncementController.sseEmitterMap.remove(userId));

    return sseEmitter;
  }

  // 채팅 알림 - 채팅에 초대된 회원에게
  public void chatAnnounce (String phoneNumber){

    UserEntity user = userRepository.findByPhoneNumber(phoneNumber);

    int userId = user.getUserId();

    if (AnnouncementController.sseEmitterMap.containsKey(userId)) {
      SseEmitter sseEmitterChat = AnnouncementController.sseEmitterMap.get(userId);
      // 알림 메세지 전송 및 종료
      try {
        sseEmitterChat.send(SseEmitter.event().name("inviteChat").data("새로운 채팅이 있습니다."));
      } catch (Exception e) {
        AnnouncementController.sseEmitterMap.remove(userId);
      }
    }
  }

  // 댓글 알림 - 게시글 및 댓글 작성자에게
  public void announceComment(int projectId){
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

    int userId = project.getUser().getUserId();

    if (AnnouncementController.sseEmitterMap.containsKey(userId)) {
      SseEmitter sseEmitterComment = AnnouncementController.sseEmitterMap.get(userId);

      // 알림 메세지 전송 및 종료
      try {
        sseEmitterComment.send(SseEmitter.event().name("addComment").data("댓글이 달렸습니다"));
      } catch (Exception e) {
        AnnouncementController.sseEmitterMap.remove(userId);
      }
    }
  }


}
