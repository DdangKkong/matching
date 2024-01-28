//package zerobase.matching.chat.service;
//
//
//import org.junit.jupiter.api.Test;
//import zerobase.matching.chat.dto.ChatDto;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//
//
//public class StompTest extends ChatTest{
//    @Test
//    public void findUser() throws ExecutionException, InterruptedException, TimeoutException {
//        MessageFrameHandler<ChatDto> handler = new MessageFrameHandler<>(ChatDto.class);
//        this.stompSession.subscribe("/sub/chat/message",handler);
//
//        this.stompSession.send("/pub/chat","aaa");
//
//        ChatDto chat = handler.getCompletableFuture().get(3, TimeUnit.SECONDS);
//
//        assertThat(chat.getChatContext(), is("aaa"));
//    }
//}
