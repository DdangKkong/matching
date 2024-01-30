package zerobase.matching.chat.dto;

import lombok.*;

public class IdDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserRequest{

        private int userId;
        private int chatRoomId;
        private String name;
    }
}
