package zerobase.matching.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import zerobase.matching.user.persist.entity.UserEntity;

@Entity
@Table(name = "USER_CHATROOM")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_CHATROOM_ID")
    private int userChatRoomId;

    // 둘 다 단방향 설계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

}
