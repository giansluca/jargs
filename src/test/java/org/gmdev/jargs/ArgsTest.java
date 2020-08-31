package org.gmdev.jargs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.gmdev.jargs.ArgsException.ErrorCode.*;

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
        assertThat(underTest.usage()).isBlank();
    }

    @Test
    void itShouldThrowIfNoSchemaAndOneArgumentId() {
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
    void itShouldThrowIfNoSchemaAndMultipleArgumentsId() {
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
    void itShouldThrowIfSchemaArgumentIdIsNotALetter() {
        // Given
        String schema = "*";
        String[] args = new String[0];

        ArgsException e = new ArgsException(
                ArgsException.ErrorCode.INVALID_ARGUMENT_NAME, '*', null);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfSchemaFormatIsInvalid() {
        // Given
        String schema = "f-";
        String[] args = new String[0];

        ArgsException e = new ArgsException(
                ArgsException.ErrorCode.INVALID_FORMAT, 'f', "-");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfArgumentIdNotMatchingSchemaArgumentId() {
        // Given
        String schema = "l";
        String[] args = {"-x"};

        ArgsException e = new ArgsException(
                ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, 'x', null);

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
        assertThat(underTest.cardinality()).isEqualTo(1);
    }

    @Test
    void itShouldThrowIfStringArgumentValueIsMissing() {
        // Given
        String schema = "n*";
        String[] args = {"-n"};

        ArgsException e = new ArgsException(MISSING_STRING, 'n', null);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
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
        assertThat(underTest.cardinality()).isEqualTo(1);
        assertThat(underTest.has('n')).isTrue();
    }

    @Test
    void itShouldIThrowIfIntegerIsNotANumber() {
        // Given
        String schema = "n#";
        String[] args = {"-n", "six"};

        ArgsException e = new ArgsException(INVALID_INTEGER, 'n', "six");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldIThrowIfIntegerIsMissing() {
        // Given
        String schema = "n#";
        String[] args = {"-n"};

        ArgsException e = new ArgsException(MISSING_INTEGER, 'n', null);

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
        assertThat(underTest.cardinality()).isEqualTo(1);
        assertThat(underTest.has('n')).isTrue();
    }

    @Test
    void itShouldThrowIfDoubleIsNotANumber() {
        // Given
        String schema = "n##";
        String[] args = {"-n", "six"};

        ArgsException e = new ArgsException(INVALID_DOUBLE, 'n', "six");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldIThrowIfDoubleIsMissing() {
        // Given
        String schema = "n##";
        String[] args = {"-n"};

        ArgsException e = new ArgsException(MISSING_DOUBLE, 'n', null);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetDoubleValue() throws ArgsException {
        // Given
        String giveNumber = "99.05";
        double expectedNumber = 99.05;
        String schema = "n##";
        String[] args = {"-n", giveNumber};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getDouble('n')).isEqualTo(expectedNumber);
        assertThat(underTest.cardinality()).isEqualTo(1);
        assertThat(underTest.has('n')).isTrue();
    }

    @Test
    void itShouldReturnTheCorrectArgumentsNumber() throws ArgsException {
        // Given
        String schema = "f*, s*";
        String[] args = {"-f", "first", "-s", "second"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.cardinality()).isEqualTo(2);
        assertThat(underTest.usage()).contains(schema);
        assertThat(underTest.has('f')).isTrue();
    }

}