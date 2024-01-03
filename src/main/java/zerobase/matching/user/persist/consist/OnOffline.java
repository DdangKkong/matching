package zerobase.matching.user.persist.consist;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OnOffline {
  online("online"),
  offline("offline"),
  both("both");

  final String value;
}
