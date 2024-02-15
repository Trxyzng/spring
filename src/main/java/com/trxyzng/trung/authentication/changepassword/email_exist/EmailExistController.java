package com.trxyzng.trung.authentication.changepassword.email_exist;

import com.trxyzng.trung.authentication.changepassword.passcode.PasscodeService;
import com.trxyzng.trung.authentication.shared.utility.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class EmailExistController {
    @Autowired
    EmailExistService emailExistService;
    @Autowired
    PasscodeService passcodeService;
    @RequestMapping(value = "/check-email", method = RequestMethod.GET)
    public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
        boolean isUserByEmailExist = emailExistService.isUserByEmailExist(email);
        String response = "{\"emailExist\":\"" + isUserByEmailExist + "\"}";
        System.out.println(response);
        if (isUserByEmailExist) {
            System.out.println("User with this email exist");
            int passcode = passcodeService.createRandomPasscode();
            System.out.println("Create passcode: " + passcode);
            boolean isEmailWithPasscodeExist = passcodeService.isEmailWithPasscodeExist(email);
            if (isEmailWithPasscodeExist) {
                passcodeService.updatePasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
            }
            else {
                passcodeService.savePasscodeEntity(email, passcode, Instant.now().truncatedTo(ChronoUnit.SECONDS));
            }
            String emailSubject = "Your pass-code";
            String emailBody = "<html><body><p>Your pass-code is: </p><b style=\"font-size:40px;\">" + passcode + "</b></body></html>";
            EmailUtils.sendEmail(email, emailSubject, emailBody);
        }
        else {
            System.out.println("No user linked to this email");
        }
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}