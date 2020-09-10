package org.gmdev.jargs.exception;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gmdev.jargs.exception.ErrorCode.*;

class JargsSchemaExceptionTest {

    JargsSchemaException underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldThrowWhenGetErrorMessageAndErrorCodeIsOK() {
        // Given
        underTest = new JargsSchemaException(OK, null);

        // When
        // Then
        assertThatThrownBy(() -> underTest.getErrorMessage())
                .isInstanceOf(Exception.class)
                .hasMessageContaining("TILT: Should not get here.");
    }

    @Test
    void itShouldGetInvalidSchemaElementTypeMessage() throws Exception {
        // Given
        underTest = new JargsSchemaException(INVALID_SCHEMA_ELEMENT_TYPE, "?");

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("'%s' is not a valid schema element type.", "?"));
    }

    @Test
    void itShouldGetEmptySchemaElementNameMessage() throws Exception {
        // Given
        underTest = new JargsSchemaException(EMPTY_SCHEMA_ELEMENT_NAME, "?");

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("'%s' is not a valid schema, element name cannot be empty.", "?"));
    }

    @Test
    void itShouldGetInvalidSchemaElementNameMessage() throws Exception {
        // Given
        underTest = new JargsSchemaException(INVALID_SCHEMA_ELEMENT_NAME, "?");

        // When
        // Then
        assertThat(underTest.getErrorMessage())
                .isEqualTo(String.format("'%s' is not a valid schema element name.", "?"));
    }

}