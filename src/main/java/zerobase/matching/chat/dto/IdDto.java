package zerobase.matching.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class IdDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserRequest{

        @NotNull
        private int userId;
        @NotNull
        private int chatRoomId;
        @NotBlank
        private String name;
    }
}
