package zerobase.matching.chat.dto;

import lombok.*;
import zerobase.matching.chat.type.ChatType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private int userId;
    private int roomId;
    private String sender;
    private String chatContext;
    private ChatType chatType;

//    public static ChatDto toDto(Chat chat){
//        return ChatDto.builder()
//                .userId(chat.getUser().getUserId())
//                .chatRoomId(chat.getChatRoom().getChatroomId())
//                .chatContext(chat.getChatContext())
//                .chatType(chat.getChatType())
//                .build();
//    }
}
