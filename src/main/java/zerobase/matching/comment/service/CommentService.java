package zerobase.matching.comment.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.matching.announcement.service.AnnouncementService;
import zerobase.matching.comment.domain.Comment;
import zerobase.matching.comment.dto.CommentDto;
import zerobase.matching.comment.dto.CreateComment;
import zerobase.matching.comment.dto.Recomment;
import zerobase.matching.comment.dto.UpdateComment;
import zerobase.matching.comment.repository.CommentRepository;
import zerobase.matching.project.domain.Project;
import zerobase.matching.project.repository.ProjectRepository;
import zerobase.matching.user.exception.CustomException;
import zerobase.matching.user.exception.ErrorCode;
import zerobase.matching.user.persist.UserRepository;
import zerobase.matching.user.persist.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  private final ProjectRepository projectRepository;

  private final UserRepository userRepository;

  private final AnnouncementService announcementService;

  // 댓글 작성
  public CommentDto createComment(CreateComment.Request request) {
    Project project = getProject(request.getProjectId());
    UserEntity user = getUser(request.getUserId());

    Comment comment = commentRepository.save(
        Comment.builder()
            .project(project).user(user)
            .content(request.getContent())
            .level(0) // level 은 0 부터 시작
            .build());

    announcementService.announceComment(request.getProjectId());

    return CommentDto.fromEntity(comment);
  }

  // 대댓글 작성
  public CommentDto recomment(Recomment.Request request) {
    Comment comment = getComment(request.getParentId());

    return CommentDto.fromEntity(commentRepository.save(
        Comment.builder()
            .project(comment.getProject()).user(comment.getUser())
            .content(request.getContent())
            .parentId(request.getParentId())
            .createdTime(LocalDateTime.now())
            .level(comment.getLevel() + 1) // 상위 댓글보다 level + 1
            .build()
    ));
  }

  // 댓글 읽기
  public CommentDto readComment(int commentId) {
    Comment comment = getComment(commentId);

    return CommentDto.fromEntity(comment);
  }

  // 댓글 수정
  @Transactional
  public CommentDto updateComment(int commentId, UpdateComment.Request request) {
    Comment comment = getComment(commentId);
    UserEntity user = getUser(request.getUserId());
    int writerId = comment.getUser().getUserId();

    // 작성자가 아닌 경우 댓글 수정 불가
    if (!Objects.equals(writerId, user.getUserId())) {
      throw new CustomException(ErrorCode.USERID_INVALID);
    }

    comment.setContent(request.getContent());
    comment.setUpdatedTime(LocalDateTime.now());

    return CommentDto.fromEntity(commentRepository.save(comment));

  }

  // 댓글 삭제
  @Transactional
  public CommentDto deleteComment(int commentId, int userId) {
    Comment comment = getComment(commentId);
    UserEntity user = getUser(userId);
    int writerId = comment.getUser().getUserId();

    // 작성자가 아닌 경우 댓글 삭제 불가
    if (!Objects.equals(writerId, user.getUserId())) {
      throw new CustomException(ErrorCode.USERID_INVALID);
    }

    comment.setContent("deleted");
    comment.setDeletedTime(LocalDateTime.now());

    return CommentDto.fromEntity(commentRepository.save(comment));
  }

  private UserEntity getUser(int userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USERID_INVALID));
  }

  private Project getProject(int projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECTID_INVALID));
  }

  private Comment getComment(int commentId) {
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENTID_INVALID));
  }

}





