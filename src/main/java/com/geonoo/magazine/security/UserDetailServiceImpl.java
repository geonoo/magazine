package com.geonoo.magazine.security;

import com.geonoo.magazine.exception.MsgEnum;
import com.geonoo.magazine.model.Users;
import com.geonoo.magazine.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(MsgEnum.userNotFound.getMsg()));

        return new UserDetailsImpl(users.getEmail(), users.getRoles(), users.getPassword());
    }
}
