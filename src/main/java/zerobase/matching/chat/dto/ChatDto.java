package zerobase.matching.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.matching.chat.type.ChatType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private int userId;
    // 보내는 사람의 RoomId, roomId 와 겹칠 수 있음
    private int senderRoomId;
    // 구독한 채팅방 (들어간 채팅방의 Id)
    private int roomId;
    private String chatContext;
    private ChatType chatType;

}
