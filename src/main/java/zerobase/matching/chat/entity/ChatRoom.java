package zerobase.matching.chat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "CHATROOM")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private Long chatroomId;

    @Column(name = "TITLE")
    private String title;

    // 채팅방 등록일자
    @Column(name = "CREATED_AT")
    private Timestamp chatRoomCreateDate;
}
