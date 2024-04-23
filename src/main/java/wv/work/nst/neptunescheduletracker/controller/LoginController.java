package wv.work.nst.neptunescheduletracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wv.work.nst.neptunescheduletracker.data.LoginInfo;
import wv.work.nst.neptunescheduletracker.service.LoginService;

import java.util.Collections;

@RestController
@CrossOrigin("*")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
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

        if (loginService.checkCredentials(loginInfo) == 404) {
            return ResponseEntity.status(404).body(Collections.singletonMap("Email not found", "error"));
        } else if (loginService.checkCredentials(loginInfo) == 401) {
            return ResponseEntity.status(401).body(Collections.singletonMap("Password not matching", "error"));
        }
        return new ResponseEntity<>(202, HttpStatus.OK); //todo jwt
    }
}
