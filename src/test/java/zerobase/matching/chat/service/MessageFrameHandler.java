//package zerobase.matching.chat.service;
//
//import org.springframework.messaging.simp.stomp.StompFrameHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//
//import java.lang.reflect.Type;
//import java.util.concurrent.CompletableFuture;
//
//public class MessageFrameHandler<T> implements StompFrameHandler {
//
//
//    private final CompletableFuture<T> completableFuture = new CompletableFuture<>();
//
//    public CompletableFuture<T> getCompletableFuture(){
//        return this.completableFuture;
//    }
//    private final Class<T> tClass;
//
//    public MessageFrameHandler(Class<T> tClass){
//        this.tClass = tClass;
//    }
//    @Override
//    public Type getPayloadType(StompHeaders headers) {
//        return this.tClass;
//    }
//
//    @Override
//    public void handleFrame(StompHeaders headers, Object payload) {
//        completableFuture.complete((T)payload);
//    }
//}
