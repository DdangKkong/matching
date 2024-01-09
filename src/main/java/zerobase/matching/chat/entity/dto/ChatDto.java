package zerobase.matching.chat.entity.dto;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.chat.entity.Chat;
import zerobase.matching.chat.entity.ChatRoom;
import zerobase.matching.chat.entity.User;
import zerobase.matching.chat.entity.type.ChatType;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {


    private Long userId;
    private Long chatRoomId;
    private String chatContext;
    private ChatType chatType;

    public static ChatDto toDto(Chat chat){
        return ChatDto.builder()
                .userId(chat.getUser().getUserId())
                .chatRoomId(chat.getChatRoom().getChatroomId())
                .chatContext(chat.getChatContext())
                .chatType(chat.getChatType())
                .build();
    }
}
