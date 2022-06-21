package com.application.backend.services;

import com.application.backend.entities.User;
import com.application.backend.repository.user.UserRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.codec.digest.DigestUtils;

public class UserService {

    @Inject
    UserRepository userRepository;

    public String login(String email, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);
        User user = this.userRepository.findUser(email);

        if (user == null || !user.getPassword().equals(hashedPassword) || user.getStatus() == 0) {
            return null;
        }
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000);
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withClaim("type", user.getType())
//                .withClaim("status", user.getStatus())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        String email = jwt.getSubject();
        User user = this.userRepository.findUser(email);
        if (user == null) {
            return false;
        }
        return true;
    }

    public List<User> allUser() {
        return this.userRepository.allUser();
    }

    public User addUser(User user) {
        return this.userRepository.addUser(user);
    }

    public User findUser(String name) {
        return this.userRepository.findUser(name);
    }

    public void userActivity(String email) {
        this.userRepository.userActivity(email);
    }

    public User updateUser(User user, String email) {
        return this.userRepository.updateUser(user, email);
    }

    public void deleteUser(String email) {
        this.userRepository.deleteUser(email);
    }

}
