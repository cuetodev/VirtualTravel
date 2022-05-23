package com.cuetodev.backempresa.User.infrastructure.controller;

import com.cuetodev.backempresa.User.application.port.UserPort;
import com.cuetodev.backempresa.User.domain.User;
import com.cuetodev.backempresa.shared.ErrorHandling.ErrorOutPutDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v0")
public class UserController {

    @Autowired
    UserPort userPort;

    @GetMapping("/token")
    public ResponseEntity<?> getToken(@RequestHeader("email") String email, @RequestHeader("password") String password ) {
        User userWantedToFind = userPort.findUserByEmailAndByPassword(email, password);
        if (userWantedToFind == null) return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        if (!userWantedToFind.getEmail().trim().equals(email) || !userWantedToFind.getPassword().equals(password))
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);

        String rol = userWantedToFind.getRole().toUpperCase().trim();
        return new ResponseEntity<>(getJWTToken(email, rol), HttpStatus.OK);
    }

    private String getJWTToken(String email, String rol) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1800000)) // Half hour
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

}
