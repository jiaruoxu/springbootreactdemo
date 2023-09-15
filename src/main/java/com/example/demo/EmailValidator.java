package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements Predicate<String> {
    public static final Predicate<String> IS_EMAIL_VALID =
            Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$",
                    Pattern.CASE_INSENSITIVE).asPredicate();

    @Override
    public boolean test(String email){
        return IS_EMAIL_VALID.test(email);
    }
}
