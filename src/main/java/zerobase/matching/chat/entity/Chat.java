package zerobase.matching.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import zerobase.matching.chat.entity.dto.ChatDto;
import zerobase.matching.chat.entity.type.ChatType;

import java.lang.reflect.Type;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    // 채팅 내용
    // Text는 어떤 자료형이어야하는가
    private String chatContext;

    // 채팅 등록 일자
    @CreatedDate
    private Date chatCreateDate;

    // User import 해야됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    private ChatType chatType;

}
