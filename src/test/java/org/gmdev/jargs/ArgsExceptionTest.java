package org.gmdev.jargs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gmdev.jargs.exception.ErrorCode.*;

class ArgsExceptionTest {

    ArgsException underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldThrowWhenGetErrorMessageAndErrorCodeIsOK() {
        // Given
        underTest = new ArgsException(OK, null);

        // When
        // Then
        assertThatThrownBy(() -> underTest.errorMessage())
                .isInstanceOf(Exception.class)
                .hasMessageContaining("TILT: Should not get here");
    }

    @Test
    void itShouldGetUnexpectedMessage() throws Exception {
        // Given
        underTest = new ArgsException(UNEXPECTED_ARGUMENT, "x", null);

        // When
        // Then
        assertThat(underTest.errorMessage())
                .isEqualTo(String.format("Argument -%c unexpected", 'x'));
    }

    @Test
    void itShouldGetMissingStringMessage() throws Exception {
        // Given
        underTest = new ArgsException(MISSING_STRING, "x", null);

        // When
        // Then
        assertThat(underTest.errorMessage())
                .isEqualTo(String.format("Could not find string parameter for -%c", 'x'));
    }

    @Test
    void itShouldGetMissingIntegerMessage() throws Exception {
        // Given
        underTest = new ArgsException(MISSING_INTEGER, "x", null);

        // When
        // Then
        assertThat(underTest.errorMessage())
                .isEqualTo(String.format("Could not find integer parameter for -%c", 'x'));
    }

    @Test
    void itShouldGetInvalidIntegerMessage() throws Exception {
        // Given
        underTest = new ArgsException(INVALID_INTEGER, "x", "six");

        // When
        // Then
        assertThat(underTest.errorMessage())
                .isEqualTo(String.format("Argument -%c expects an integer but was '%s'", 'x', "six"));
    }

    @Test
    void itShouldGetMissingDoubleMessage() throws Exception {
        // Given
        underTest = new ArgsException(MISSING_DOUBLE, "x", null);

        // When
        // Then
        assertThat(underTest.errorMessage())
                .isEqualTo(String.format("Could not find double parameter for -%c", 'x'));
    }

    @Test
    void itShouldGetInvalidDoubleMessage() throws Exception {
        // Given
        underTest = new ArgsException(INVALID_DOUBLE, "x", "six.three");

        // When
        // Then
        assertThat(underTest.errorMessage())
                .isEqualTo(String.format("Argument -%c expects a double but was '%s'", 'x', "six.three"));
    }
}