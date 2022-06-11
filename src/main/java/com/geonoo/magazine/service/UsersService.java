//package com.geonoo.magazine.service;
//
//import com.geonoo.magazine.dto.UsersDto;
//import com.geonoo.magazine.model.Boards;
//import com.geonoo.magazine.model.Users;
//import com.geonoo.magazine.repository.UsersRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UsersService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UsersRepository usersRepository;
//
//    @Transactional
//    public Users saveUser(UsersDto usersDto){
//        Optional<Users> findUser = usersRepository.findByEmail(usersDto.getEmail());
//        if (findUser.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자 email 이 존재합니다.");
//        }
//
//        //비밀번호 인코딩
//        String password = passwordEncoder.encode(usersDto.getPassword());
//        Users users = Users.builder()
//                .email(usersDto.getEmail())
//                .password(password)
//                .build();
//
//        return usersRepository.save(users);
//    }
//}
