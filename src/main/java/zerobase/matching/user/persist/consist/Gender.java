package zerobase.matching.user.persist.consist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
  male("male"),
  female("female");

  final String value;
}
