package zerobase.matching.announcement.service;

import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import zerobase.matching.announcement.repository.AnnouncementRepository;
import zerobase.matching.announcement.controller.AnnouncementController;
import zerobase.matching.announcement.domain.Announcement;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.repository.ProjectRepository;
import zerobase.matching.user.exception.CustomException;
import zerobase.matching.user.exception.ErrorCode;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;
  private final AnnouncementRepository announcementRepository;

  // 클라이언트에서 서버의 이벤트를 구독하기 위한 요청을 보냄
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

  // 채팅 알림 - 채팅방에 issue 가 있을 때, 채팅방에 들어와 있는 회원에게
  public void chatAnnounce (int userId){

    UserEntity user = userRepository.findByUserId(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USERID_INVALID));

    int receiverId = user.getUserId();

    if (AnnouncementController.sseEmitterMap.containsKey(receiverId)) {
      SseEmitter sseEmitterChat = AnnouncementController.sseEmitterMap.get(receiverId);
      // 알림 메세지 전송 및 종료
      try {
        sseEmitterChat.send(SseEmitter.event().name("inviteChat").data("새로운 채팅이 있습니다."));
      } catch (Exception e) {
        AnnouncementController.sseEmitterMap.remove(receiverId);
      }
      // 알림 저장
      announcementRepository.save(Announcement.builder()
          .content("새로운 채팅이 있습니다.")
          .announcedTime(LocalDateTime.now())
          .user(user)
          .build());
    }
  }

  // 댓글 알림 - 게시글 작성자에게
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
      // 알림 저장
      announcementRepository.save(Announcement.builder()
            .content("댓글이 달렸습니다")
            .announcedTime(LocalDateTime.now())
            .user(project.getUser())
            .build());

    }
  }

}
