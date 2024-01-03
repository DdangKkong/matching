package zerobase.matching.user.persist.consist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MembershipLevel {
  beginner("beginner"),
  intermediate("intermediate"),
  advanced("advanced");

  final String value;
}
