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
    void isShouldThrowIfSchemaIsNull() throws ArgsException {
        // Given
        String schema = null;
        String[] args = {};

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .hasMessageContaining("FATAL: Not null violation error");
    }

    @Test
    void isShouldThrowIfArgumentArrayIsNull() throws ArgsException {
        // Given
        String schema = "";
        String[] args = null;

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .hasMessageContaining("FATAL: Not null violation error");
    }

    @Test
    void itShouldDoNothingIfNoSchemaAndNoArguments() throws ArgsException {
        // Given
        String schema = "";
        String[] args = new String[0];

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.cardinality()).isEqualTo(0);
    }

    @Test
    void isShouldThrowIfSchemaHasNotElementName() {
        // Given
        String schema = "*";
        String[] args = new String[0];

        ArgsException e = new ArgsException(EMPTY_SCHEMA_ELEMENT_NAME, "*");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfNoSchemaAndOneArgumentName() {
        // Given
        String schema = "";
        String[] args = {"-id"};

        try {
            // When
            underTest = new Args(schema, args);
        } catch (ArgsException e) {
            // Then
            assertThat(e.getErrorCode()).isEqualTo(UNEXPECTED_ARGUMENT);
            assertThat(e.getErrorArgumentName()).isEqualTo("id");
        }
    }

    @Test
    void itShouldThrowIfNoSchemaAndMultipleArgumentsName() {
        // Given
        String schema = "";
        String[] args = {"-id1", "-id2"};

        try {
            // When
            underTest = new Args(schema, args);
        } catch (ArgsException e) {
            // Then
            assertThat(e.getErrorCode()).isEqualTo(UNEXPECTED_ARGUMENT);
            assertThat(e.getErrorArgumentName()).isEqualTo("id1");
        }
    }

    @Test
    void isShouldThrowIfArgumentNameDoesNotStartWithHyphen() {
        // Given
        String schema = "name*";
        String[] args = {"name", "gians"};

        ArgsException e = new ArgsException(INVALID_ARGUMENT_NAME, "name", null);

        // When
        // Then
        assertThatThrownBy(() -> underTest = new Args(schema, args))
            .isInstanceOf(ArgsException.class)
            .isEqualToComparingFieldByField(e);
    }


    @Test
    void itShouldThrowIfSchemaElementContainsOtherThanALetter() {
        // Given
        String schema = "_id*";
        String[] args = new String[0];

        ArgsException e = new ArgsException(
                INVALID_SCHEMA_ELEMENT_NAME, "_id*");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfSchemaElementFormatIsInvalid() {
        // Given
        String schema = "id&";
        String[] args = new String[0];

        ArgsException e = new ArgsException(
                ArgsException.ErrorCode.INVALID_SCHEMA_ELEMENT_TYPE, "&");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfArgumentNameDoesNotMatchElementName() {
        // Given
        String schema = "id%";
        String[] args = {"-idA"};

        ArgsException e = new ArgsException(
                ArgsException.ErrorCode.UNEXPECTED_ARGUMENT, "idA", null);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetBooleanValue() throws ArgsException {
        // Given
        String schema = "bool%";
        String[] args = {"-bool"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getBoolean("bool")).isTrue();
        assertThat(underTest.cardinality()).isEqualTo(1);
        assertThat(underTest.has("bool")).isTrue();
    }

    @Test
    void itShouldThrowIfStringArgumentValueIsMissing() {
        // Given
        String schema = "string*";
        String[] args = {"-string"};

        ArgsException e = new ArgsException(MISSING_STRING, "string", null);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetStringValues() throws ArgsException {
        // Given
        String schema = "name*, job*";
        String[] args = {"-name", "gians", "-job", "developer"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getString("name")).isEqualTo("gians");
        assertThat(underTest.has("name")).isTrue();
        assertThat(underTest.getString("job")).isEqualTo("developer");
        assertThat(underTest.has("job")).isTrue();
        assertThat(underTest.cardinality()).isEqualTo(2);
    }

    @Test
    void itShouldIThrowIfIntegerArgumentValueIsNotANumber() {
        // Given
        String schema = "int#";
        String[] args = {"-int", "six"};

        ArgsException e = new ArgsException(INVALID_INTEGER, "int", "six");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldIThrowIfIntegerArgumentValueIsMissing() {
        // Given
        String schema = "int#";
        String[] args = {"-int"};

        ArgsException e = new ArgsException(MISSING_INTEGER, "int", null);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetIntegerValue() throws ArgsException {
        // Given
        String schema = "int#";
        String[] args = {"-int", "99"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getInt("int")).isEqualTo(99);
        assertThat(underTest.cardinality()).isEqualTo(1);
        assertThat(underTest.has("int")).isTrue();
    }

    @Test
    void itShouldThrowIfDoubleArgumentValueIsNotANumber() {
        // Given
        String schema = "double@";
        String[] args = {"-double", "six"};

        ArgsException e = new ArgsException(INVALID_DOUBLE, "double", "six");

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldIThrowIfDoubleArgumentValueIsMissing() {
        // Given
        String schema = "double@";
        String[] args = {"-double"};

        ArgsException e = new ArgsException(MISSING_DOUBLE, "double", null);

        // When
        // Then
        assertThatThrownBy(() -> new Args(schema, args))
                .isInstanceOf(ArgsException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetDoubleValue() throws ArgsException {
        // Given
        String schema = "double@";
        String[] args = {"-double", "99.05"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.getDouble("double")).isEqualTo(99.05);
        assertThat(underTest.cardinality()).isEqualTo(1);
        assertThat(underTest.has("double")).isTrue();
    }

    @Test
    void itShouldReturnTheCorrectArgumentsNumber() throws ArgsException {
        // Given
        String schema = "first*, second*";
        String[] args = {"-first", "first-value", "-second", "second-value"};

        // When
        underTest = new Args(schema, args);

        // Then
        assertThat(underTest.cardinality()).isEqualTo(2);
        assertThat(underTest.has("first")).isTrue();
        assertThat(underTest.has("second")).isTrue();
    }

}