package zerobase.matching.chat.dto;

import lombok.*;

public class IdDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserRequest{

        private Long userId;
        private Long chatRoomId;
        private String name;
    }
}
