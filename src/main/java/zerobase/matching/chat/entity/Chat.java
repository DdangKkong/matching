package zerobase.matching.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.chat.type.ChatType;

import java.sql.Timestamp;
import zerobase.matching.user.persist.entity.UserEntity;

@Entity
@Table(name = "CHAT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ID")
    private int chatId;

    // 채팅 내용
    // Text는 어떤 자료형이어야하는가
    @Column(name = "CONTENT")
    private String chatContext;

    // 채팅 등록 일자
    @Column(name = "CHAT_AT")
    private Timestamp chatCreateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CHATROOM_ID")
    private UserChatRoom userChatRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHAT_TYPE")
    private ChatType chatType;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

}
