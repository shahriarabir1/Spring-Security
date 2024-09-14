package com.App.SpringSecurity.services;

import com.App.SpringSecurity.model.Users;
import com.App.SpringSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo repo;


    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
      return repo.save(user);
    }

    public String verify(Users user) {
        Authentication auth=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (user.getUsername(),user.getPassword()));

        if(auth.isAuthenticated()){
            return jwtService.getToken(user.getUsername());
        }
        return "Failed";
    }
}
