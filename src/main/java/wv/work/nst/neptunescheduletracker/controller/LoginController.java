package wv.work.nst.neptunescheduletracker.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wv.work.nst.neptunescheduletracker.data.LoginInfo;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.security.token.JwtUtil;
import wv.work.nst.neptunescheduletracker.service.LoginService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginController(LoginService loginService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginInfo loginInfo,
            @RequestHeader String Authorization,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginInfo", loginInfo);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Collections.singletonMap("error", "Login was unsuccessful"));
        }

        try {

            //tries to create a token with a new user instance
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword()));
            String email = auth.getName();
            User user = new User(email, loginInfo.getPassword());
            String authorizationToken = jwtUtil.generateToken(user);

            Cookie authCookie = assembleCookie(authorizationToken, true);
            response.addCookie(authCookie);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("authToken", authorizationToken);

            //split token from bearer
            String forwardedToken = Authorization.split(" ")[1];

            //if returned token is null also generate a refresh token
            if (forwardedToken.equals("null")) {
                String refreshToken = jwtUtil.generateExpiryToken(user);
                responseBody.put("refreshToken", refreshToken);
            }

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        } catch (BadCredentialsException e) {
            ResponseEntity<Object> body = getObjectResponseEntity(loginInfo);

            //send the correct status code if the error is caught
            if (body != null) return body;
        } catch (Exception e) {
            //sumn aint right
            return ResponseEntity.status(500).body(Collections.singletonMap("An error has occured: " + e.getMessage(), "error"));
        }
        return null;
    }


    private static Cookie assembleCookie(String token, boolean isHttpOnly) {
        Cookie authCookie = new Cookie("authToken", token);
        authCookie.setHttpOnly(isHttpOnly);
        authCookie.setSecure(isHttpOnly);
        authCookie.setPath("/");
        authCookie.setMaxAge(15 * 60);
        return authCookie;
    }

    //if there is a bad credential exception check if email or password throws it
    private ResponseEntity<Object> getObjectResponseEntity(LoginInfo loginInfo) {
        if (loginService.checkCredentials(loginInfo) == 404) {
            return ResponseEntity.status(404).body(Collections.singletonMap("Email not found", "error"));
        } else if (loginService.checkCredentials(loginInfo) == 401) {
            return ResponseEntity.status(401).body(Collections.singletonMap("Password not matching", "error"));
        }
        return null;
    }
}
