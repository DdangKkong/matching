package zerobase.matching.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.matching.chat.type.ChatType;
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
    @Column(name = "CHAT_TIME")
    private LocalDateTime chatCreateDate;

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
