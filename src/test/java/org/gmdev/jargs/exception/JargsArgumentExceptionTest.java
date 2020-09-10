package org.gmdev.jargs.exception;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gmdev.jargs.exception.ErrorCode.*;

class JargsArgumentExceptionTest {

    JargsArgumentException underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldThrowWhenGetErrorMessageAndErrorCodeIsOK() {
        // Given
        underTest = new JargsArgumentException(OK);

        // When
        // Then
        assertThatThrownBy(() -> underTest.getErrorMessage())
                .isInstanceOf(Exception.class)
                .hasMessageContaining("TILT: Should not get here.");
    }

    @Test
    void itShouldGetUnexpectedArgumentMessage() throws Exception {
        // Given
        underTest = new JargsArgumentException(UNEXPECTED_ARGUMENT, "arg", null);

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("Argument name -%s unexpected.", "arg"));
    }

    @Test
    void itShouldGetInvalidArgumentNameMessage() throws Exception {
        // Given
        underTest = new JargsArgumentException(INVALID_ARGUMENT_NAME, "arg", null);

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("'%s' is not a valid argument name.", "arg"));
    }


    @Test
    void itShouldGetMissingStringMessage() throws Exception {
        // Given
        underTest = new JargsArgumentException(MISSING_STRING, "arg", null);

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("Could not find string argument value for -%s.", "arg"));
    }

    @Test
    void itShouldGetMissingIntegerMessage() throws Exception {
        // Given
        underTest = new JargsArgumentException(MISSING_INTEGER, "arg", null);

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("Could not find integer argument value for -%s.", "arg"));
    }

    @Test
    void itShouldGetInvalidIntegerMessage() throws Exception {
        // Given
        underTest = new JargsArgumentException(INVALID_INTEGER, "arg", "six");

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("Argument name -%s expects an integer but was '%s'.", "arg", "six"));
    }

    @Test
    void itShouldGetMissingDoubleMessage() throws Exception {
        // Given
        underTest = new JargsArgumentException(MISSING_DOUBLE, "arg", null);

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("Could not find double argument value for -%s.", "arg"));
    }

    @Test
    void itShouldGetInvalidDoubleMessage() throws Exception {
        // Given
        underTest = new JargsArgumentException(INVALID_DOUBLE, "arg", "six.three");

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("Argument name -%s expects a double but was '%s'.", "arg", "six.three"));
    }
}