package zerobase.matching.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.matching.comment.dto.CreateComment;
import zerobase.matching.comment.dto.DeleteComment;
import zerobase.matching.comment.dto.ReadComment;
import zerobase.matching.comment.dto.Recomment;
import zerobase.matching.comment.dto.UpdateComment;
import zerobase.matching.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maching/projects/comments")
public class CommentController {

  private final CommentService commentService;

  // 댓글 작성
  @PostMapping
  public ResponseEntity<CreateComment.Response> createComment(
      @RequestBody @Valid CreateComment.Request request){
    CreateComment.Response response = CreateComment.Response.fromEntity(
        commentService.createComment(request
        )
    );
    return ResponseEntity.ok(response);
  }

  // 대댓글 작성
  @PostMapping("/recomments")
  public ResponseEntity<Recomment.Response> recomment(
      @RequestBody @Valid Recomment.Request request) {
    Recomment.Response response = Recomment.Response.fromEntity(
        commentService.recomment(request)
    );
    return ResponseEntity.ok(response);
  }

  // 댓글 읽기
  @GetMapping
  public ResponseEntity<ReadComment.Response> readComment(
      @RequestParam(value = "commentId") int commentId
  ){
    ReadComment.Response response = ReadComment.Response.fromEntity(
        commentService.readComment(commentId)
    );
    return ResponseEntity.ok(response);
  }

  // 댓글 수정
  @PutMapping
  public ResponseEntity<UpdateComment.Response> updateComment(
      @RequestParam(value = "commentId") int commentId,
      @RequestBody @Valid UpdateComment.Request request
  ){
    UpdateComment.Response response = UpdateComment.Response.fromEntity(
        commentService.updateComment(commentId, request)
    );
    return ResponseEntity.ok(response);
  }

  // 댓글 삭제
  @DeleteMapping
  public ResponseEntity<DeleteComment.Response> deleteComment(
      @RequestParam int commentId,
      @RequestParam int userId
  ){
    DeleteComment.Response response = DeleteComment.Response.fromEntity(
        commentService.deleteComment(commentId, userId)
    );
    return ResponseEntity.ok(response);
  }

}
