package com.kbtg.bootcamp.posttest.authentication;

import com.kbtg.bootcamp.posttest.user.UserDetail;
import com.kbtg.bootcamp.posttest.user.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserDetail> optionalUserDetail = userRepository.findByName(name);
        if (optionalUserDetail.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDetail userDetail = optionalUserDetail.get();
        return User.withUsername(userDetail.getName()).password(userDetail.getPassword()).roles(userDetail.getRole()).build();
    }
}
