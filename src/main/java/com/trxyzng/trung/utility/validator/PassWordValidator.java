package com.trxyzng.trung.utility.validator;

import com.trxyzng.trung.utility.constraint.PassWordConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassWordValidator implements ConstraintValidator<PassWordConstraint, String> {
    public void initialize(PassWordConstraint constraintAnnotation) {}
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.equals("")) {
            return false;
        }
        String regex= "^[0-9a-zA-Z ~`!@#$%^&*()_+={[}]|\\:;\"'<,>.?/-]{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
