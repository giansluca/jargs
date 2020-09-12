package io.github.giansluca.jargs.exception;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JargsArgumentExceptionTest {

    JargsArgumentException underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldGetUnexpectedArgumentMessage() {
        // Given
        underTest = new JargsArgumentException(ErrorCode.UNEXPECTED_ARGUMENT, "arg", null);

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("Argument name -%s unexpected.", "arg"));
    }

    @Test
    void itShouldGetInvalidArgumentNameMessage() {
        // Given
        underTest = new JargsArgumentException(ErrorCode.INVALID_ARGUMENT_NAME, "arg", null);

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("'%s' is not a valid argument name.", "arg"));
    }


    @Test
    void itShouldGetMissingStringMessage() {
        // Given
        underTest = new JargsArgumentException(ErrorCode.MISSING_STRING, "arg", null);

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("Could not find string argument value for '-%s' .", "arg"));
    }

    @Test
    void itShouldGetMissingIntegerMessage() {
        // Given
        underTest = new JargsArgumentException(ErrorCode.MISSING_INTEGER, "arg", null);

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("Could not find integer argument value for '-%s' .", "arg"));
    }

    @Test
    void itShouldGetInvalidIntegerMessage() {
        // Given
        underTest = new JargsArgumentException(ErrorCode.INVALID_INTEGER, "arg", "six");

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("Argument name '-%s' expects an integer but was '%s' .", "arg", "six"));
    }

    @Test
    void itShouldGetMissingDoubleMessage() {
        // Given
        underTest = new JargsArgumentException(ErrorCode.MISSING_DOUBLE, "arg", null);

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("Could not find double argument value for '-%s' .", "arg"));
    }

    @Test
    void itShouldGetInvalidDoubleMessage() {
        // Given
        underTest = new JargsArgumentException(ErrorCode.INVALID_DOUBLE, "arg", "six.three");

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("Argument name '-%s' expects a double but was '%s' .", "arg", "six.three"));
    }
}