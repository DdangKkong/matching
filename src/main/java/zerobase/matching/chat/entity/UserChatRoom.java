package zerobase.matching.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.user.persist.entity.UserEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userChatRoomId;

    // 둘 다 단방향 설계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    // User import 해야함
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;


}
