package org.gmdev.jargs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gmdev.jargs.ArgsException.ErrorCode.INVALID_INTEGER;
import static org.gmdev.jargs.ArgsException.ErrorCode.UNEXPECTED_ARGUMENT;

class ArgsTest {

    Args underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldBeValidIfNoSchemaAndNoArguments() throws ArgsException {
        // Given
        String schema = "";
        String[] args = new String[0];

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.cardinality()).isEqualTo(0);
        assertThat(underTest.isValid()).isTrue();
        assertThat(underTest.usage()).isBlank();
    }

    @Test
    void itShouldThrowIfNoSchemaAndOneArgument() {
        // Given
        String schema = "";
        String[] args = {"-x"};

        try {
            // When
            underTest = new Args(schema, args);
        } catch (ArgsException e) {
            // Then
            assertThat(e.getErrorCode()).isEqualTo(UNEXPECTED_ARGUMENT);
            assertThat(e.getErrorArgumentId()).isEqualTo('x');
        }
    }

    @Test
    void itShouldThrowIfNoSchemaAndMultipleArguments() {
        // Given
        String schema = "";
        String[] args = {"-x", "-y"};

        try {
            // When
            underTest = new Args(schema, args);
        } catch (ArgsException e) {
            // Then
            assertThat(e.getErrorCode()).isEqualTo(UNEXPECTED_ARGUMENT);
            assertThat(e.getErrorArgumentId()).isEqualTo('x');
        }
    }

    @Test
    void itShouldThrowIfSchemaElementIsNotALetter() {
        // Given
        String schema = "1";
        String[] args = {"-1"};

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .hasMessageContaining(
                        String.format("Bad character: %s in Args format: %s", "1", schema));
    }

    @Test
    void itShouldThrowIfArgumentIdDoesNotMatchSchemaElement() {
        // Given
        String schema = "l";
        String[] args = {"-x"};

        ArgsException e = new ArgsException(
                ArgsException.ErrorCode.UNEXPECTED_ARGUMENT,
                'x',
                null
        );

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfStringElementIsMissing() {
        // Given
        String schema = "n*";
        String[] args = {"-n"};

        ArgsException e = new ArgsException(ArgsException.ErrorCode.MISSING_STRING);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetBooleanValue() throws ArgsException {
        // Given
        String schema = "l";
        String[] args = {"-l"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getBoolean('l')).isTrue();
        assertThat(underTest.isValid()).isTrue();
    }

    @Test
    void itShouldSetStringValue() throws ArgsException {
        // Given
        String name = "gians";
        String schema = "n*";
        String[] args = {"-n", name};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getString('n')).isEqualTo(name);
        assertThat(underTest.isValid()).isTrue();
    }

    @Test
    void itShouldIThrowIfIntegerIsNotANumber() {
        // Given
        String schema = "n#";
        String[] args = {"-n", "a"};

        ArgsException e = new ArgsException(INVALID_INTEGER, "a");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetIntegerValue() throws ArgsException {
        // Given
        String giveNumber = "99";
        int expectedNumber = 99;
        String schema = "n#";
        String[] args = {"-n", giveNumber};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getInt('n')).isEqualTo(expectedNumber);
        assertThat(underTest.isValid()).isTrue();
    }

    @Test
    void itShouldReturnTheCorrectArgumentsNumber() throws ArgsException {
        // Given
        String schema = "f*,s*";
        String[] args = {"-f", "first", "-s", "second"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.cardinality()).isEqualTo(2);
        assertThat(underTest.usage()).contains(schema);
        assertThat(underTest.has('f')).isTrue();
    }

    @Test
    void itShouldThrowWhenCallErrorMessageWithNoErrors() throws Exception {
        String schema = "";
        String[] args = {};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThatThrownBy(() -> underTest.errorMessage())
                .isInstanceOf(Exception.class)
                .hasMessageContaining("TILT: Should not get here");
    }

}