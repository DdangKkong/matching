package zerobase.matching.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {
    public Long userId;

    public User(Long userId) {
        this.userId = userId;
    }
}
