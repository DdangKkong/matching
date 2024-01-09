package zerobase.matching.user.persist.consist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
  member("member"),
  administrator("administrator");

  final String value;
}
