package io.github.giansluca.jargs.exception;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static io.github.giansluca.jargs.exception.ErrorCode.*;

class JargsSchemaExceptionTest {

    JargsSchemaException underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldGetInvalidSchemaElementTypeMessage() throws Exception {
        // Given
        underTest = new JargsSchemaException(INVALID_SCHEMA_ELEMENT_TYPE, "?");

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("'%s' is not a valid schema element type.", "?"));
    }

    @Test
    void itShouldGetEmptySchemaElementNameMessage() throws Exception {
        // Given
        underTest = new JargsSchemaException(EMPTY_SCHEMA_ELEMENT_NAME, "?");

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("'%s' is not a valid schema, element name cannot be empty.", "?"));
    }

    @Test
    void itShouldGetInvalidSchemaElementNameMessage() throws Exception {
        // Given
        underTest = new JargsSchemaException(INVALID_SCHEMA_ELEMENT_NAME, "?");

        // When
        // Then
        assertThat(underTest.getMessage())
                .isEqualTo(String.format("'%s' is not a valid schema element name.", "?"));
    }

}