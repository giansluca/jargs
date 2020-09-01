package org.gmdev.jargs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gmdev.jargs.ArgsException.ErrorCode.UNEXPECTED_ARGUMENT;

class ArgsExceptionTest {

    ArgsException underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldGetUnexpectedMessage() throws Exception {
        // Given
        underTest = new ArgsException(UNEXPECTED_ARGUMENT, 'x', null);

        // When
        // Then
        assertThat(underTest.errorMessage())
                .isEqualTo(String.format("Argument -%c unexpected", 'x'));
    }
}