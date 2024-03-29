package zerobase.matching.announcement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.matching.user.persist.entity.UserEntity;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ANNOUNCEMENT")
public class Announcement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ANNOUNCEMENT_ID")
  private int id;

  @Column(name = "CONTENT")
  private String content;

  @Column(name = "ANNOUNCED_TIME")
  private LocalDateTime announcedTime;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private UserEntity user;
}
