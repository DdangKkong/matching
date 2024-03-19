package zerobase.matching.announcement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Announcement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "announcement_id")
  private int id;


}
