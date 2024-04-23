package wv.work.nst.neptunescheduletracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wv.work.nst.neptunescheduletracker.exceptions.EmailIsTakenException;
import wv.work.nst.neptunescheduletracker.data.RegistrationInfo;
import wv.work.nst.neptunescheduletracker.security.validate.ValidateRegistry;
import wv.work.nst.neptunescheduletracker.service.RegistrationService;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ValidateRegistry validateRegistry;

    @Autowired
    public RegistrationController(RegistrationService registrationService, ValidateRegistry validateRegistry) {
        this.registrationService = registrationService;
        this.validateRegistry = validateRegistry;
    }

    @RequestMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody RegistrationInfo registrationInfo,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationInfo", registrationInfo);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Collections.singletonMap("error", "Registration was unsuccessful"));
        }

        try {
            //return 409 if user repository found the same email in db
            validateRegistry.validator().validate(registrationInfo, bindingResult);
        } catch (EmailIsTakenException |
                 IncorrectResultSizeDataAccessException e) { //todo miért dobja el előbb??????????????
            return ResponseEntity.status(409).body(Collections.singletonMap("Ez az email cím már foglalt", "error"));
        }

        registrationService.register(registrationInfo);
        redirectAttributes.addFlashAttribute("alertSuccess", "A regisztráció sikerült");
        return ResponseEntity.ok(Collections.singletonMap("message", "Registration successful"));
    }


}
