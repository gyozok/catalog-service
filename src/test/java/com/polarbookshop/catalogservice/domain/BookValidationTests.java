package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenAllFieldsCorrectThenSucceed() {
        //GIVEN
        var book = new Book(null, "1234567890", "Title", "Author", 9.90, "Publisher", null, null,0);

        //WHEN
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        //THEN
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenIsbnDefinedButIncorrect_ThenFail() {
        //GIVEN
        var book = new Book(null, "a23456789", "Title", "Author", 9.90, "Publisher", null, null,0);

        //WHEN
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        //THEN
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }
}
