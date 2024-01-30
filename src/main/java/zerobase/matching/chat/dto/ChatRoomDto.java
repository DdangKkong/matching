package zerobase.matching.chat.dto;

import lombok.*;
import zerobase.matching.chat.entity.ChatRoom;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {

    private int chatroomId;
    private String title;

    public static ChatRoomDto toDto(ChatRoom chatroom){
        return ChatRoomDto.builder()
                .chatroomId(chatroom.getChatroomId())
                .title(chatroom.getTitle())
                .build();
    }
}
