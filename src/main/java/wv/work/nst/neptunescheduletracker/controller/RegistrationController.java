package wv.work.nst.neptunescheduletracker.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wv.work.nst.neptunescheduletracker.entity.User;
import wv.work.nst.neptunescheduletracker.register.RegistrationInfo;
import wv.work.nst.neptunescheduletracker.service.RegistrationService;

@RestController
@CrossOrigin(origins = "*")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping("/")
    public String form(Model model) {
        model.addAttribute("registrationInfo", new RegistrationInfo());
        return "registration/form";
    }

    @RequestMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegistrationInfo registrationInfo,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationInfo", registrationInfo);
            return ResponseEntity.unprocessableEntity().body("Registration was unsuccessful");
        }

        registrationService.register(registrationInfo);
        redirectAttributes.addFlashAttribute("alertSuccess", "A regisztráció sikerült");
        return ResponseEntity.ok().body("Registration successful");
    }


}
