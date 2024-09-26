package com.authentication.service.logics;

import com.authentication.dto.LoginCredentials;
import com.authentication.entity.User;
import com.authentication.exception.UnauthorizedException;
import com.authentication.exception.UserAlreadyExistsException;
import com.authentication.exception.UserNotFoundException;
import com.authentication.repository.UserRepository;
import com.authentication.service.interfaces.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//service logic layers...
@Service
public class UserServiceImpl implements UserService {

    //repo layer...
    @Autowired
    private UserRepository urepo;

    //jwt service...
    @Autowired
    private JwtService jwtService;

    //password encoder...
    @Autowired
    private PasswordEncoder passwordEncoder;

    //authentication manager...
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User register(User u) {
        //if user already exists...
        if(urepo.findByEmail(u.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User already exists with this mail id!!!");
        }
        //encrypt password...
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return urepo.save(u);
    }

    @Override
    public String login(LoginCredentials lc) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(lc.getEmail(), lc.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(lc.getEmail());
        } else {
            throw new UserNotFoundException("User not found with given credentials!!!");
        }
    }

    @Override
    public User update(String fullToken, User u) {
        //fetch email from jwt...
        String email=jwtService.getEmailFromJwt(fullToken.substring(7));

        User exeu=urepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found with given jwt token. Please provide correct token!!!"));

        //update those fields value which are provided for update...
        //role can't be updated...
        if(u.getName()!=null){
            exeu.setName(u.getName());
        }
        if(u.getPassword()!=null){
            exeu.setPassword(u.getPassword());
        }
        if(u.getEmail()!=null){
            exeu.setEmail(u.getEmail());
        }

        return urepo.save(exeu);
    }

    @Override
    public String delete(String fullToken) {
        //fetch email from jwt...
        String email=jwtService.getEmailFromJwt(fullToken.substring(7));

        User exeu=urepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found with given jwt token. Please provide correct token!!!"));

        urepo.delete(exeu);

        return "deleted!!!";
    }

    @Override
    public User findSelf(String fullToken) {
        //fetch email from jwt...
        String email=jwtService.getEmailFromJwt(fullToken.substring(7));

        User exeu=urepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found with given jwt token. Please provide correct token!!!"));

        return exeu;
    }

    @Override
    public List<User> findAll(String fullToken) {
        //fetch email from jwt...
        String email=jwtService.getEmailFromJwt(fullToken.substring(7));

        User exeu=urepo.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found with given jwt token. Please provide correct token!!!"));

        if(!exeu.getRole().equalsIgnoreCase("admin")){
            throw new UnauthorizedException("You're not authorized/admin to see all user accounts!!!");
        }

        return urepo.findAll();
    }


}
