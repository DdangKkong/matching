package zerobase.matching.announcement.controller;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import zerobase.matching.announcement.service.AnnouncementService;
import zerobase.matching.user.persist.entity.UserEntity;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

  private final AnnouncementService announcementService;
  public static Map<Integer, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

  // 프론트에서 호출하여 사용
  @GetMapping
  public SseEmitter message(@AuthenticationPrincipal UserEntity user) {
    int userId = user.getUserId();
    SseEmitter sseEmitter = announcementService.subscribe(userId);

    return sseEmitter;
  }

}
