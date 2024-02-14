//package zerobase.matching.user.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import zerobase.matching.user.persist.UserRepository;
//import zerobase.matching.user.persist.entity.UserEntity;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailService { // test 시 jwt token 인증 정보 전달 위해
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return new UserDetails(user);
//
//    }
//
//}
