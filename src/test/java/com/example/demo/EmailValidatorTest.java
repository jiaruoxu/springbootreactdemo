package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {
    private final EmailValidator underTest = new EmailValidator();
    @Test
    public  void itShouldValidateCorrectEmail(){
        assertThat(underTest.test("hello@hmasil.com")).isTrue();
    }

    @Test
    public  void itShouldValidateInCorrectEmail(){
        assertThat(underTest.test("hello.com")).isFalse();
    }

    @Test
    public  void itShouldValidateInCorrectEmailWithoutDotAtTheEnd(){
        assertThat(underTest.test("hello@hmasil")).isFalse();
    }
}