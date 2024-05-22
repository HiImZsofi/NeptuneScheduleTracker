package wv.work.nst.neptunescheduletracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wv.work.nst.neptunescheduletracker.data.LoginInfo;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.security.token.JwtUtil;
import wv.work.nst.neptunescheduletracker.service.LoginService;

import java.util.Collections;


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
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
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
            String token = jwtUtil.generateToken(user);

            //return both status code and token if it runs through
            return ResponseEntity.status(200).body(Collections.singletonMap("Logged in", token));
        } catch (BadCredentialsException e) {
            ResponseEntity<Object> body = getObjectResponseEntity(loginInfo);

            //send the correct status code if the error is caught
            if (body != null) return body;
        } catch (Exception e) {
            //lmao
            return ResponseEntity.status(500).body(Collections.singletonMap("An error has occured: " + e.getMessage(), "error"));
        }
        return null;
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
