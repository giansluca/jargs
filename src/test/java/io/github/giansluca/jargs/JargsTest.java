package io.github.giansluca.jargs;

import io.github.giansluca.jargs.exception.ErrorCode;
import io.github.giansluca.jargs.exception.JargsArgumentException;
import io.github.giansluca.jargs.exception.JargsException;
import io.github.giansluca.jargs.exception.JargsSchemaException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

class JargsTest {

    Jargs underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isShouldThrowIfSchemaIsNull() {
        // Given
        String schema = null;
        String[] args = {};

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsException.class)
                .hasMessageContaining("FATAL: Not null violation error");
    }

    @Test
    void isShouldThrowIfArgumentArrayIsNull() {
        // Given
        String schema = "";
        String[] args = null;

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsException.class)
                .hasMessageContaining("FATAL: Not null violation error");
    }

    @Test
    void itShouldDoNothingIfNoSchemaAndNoArguments() throws Exception {
        // Given
        String schema = "";
        String[] args = new String[0];

        // When
        underTest = new Jargs(schema, args);

        // Then
        assertThat(underTest.cardinality()).isEqualTo(0);
    }

    @Test
    void isShouldThrowIfSchemaHasNotElementName() {
        // Given
        String schema = "*";
        String[] args = new String[0];

        JargsSchemaException e = new JargsSchemaException(ErrorCode.EMPTY_SCHEMA_ELEMENT_NAME, "*");

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsSchemaException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfNoSchemaAndOneArgumentName() throws Exception {
        // Given
        String schema = "";
        String[] args = {"-test"};

        try {
            // When
            underTest = new Jargs(schema, args);
        } catch (JargsArgumentException e) {
            // Then
            assertThat(e.getErrorCode()).isEqualTo(ErrorCode.UNEXPECTED_ARGUMENT);
            assertThat(e.getErrorArgumentName()).isEqualTo("test");
        }
    }

    @Test
    void itShouldThrowIfNoSchemaAndMultipleArgumentsName() throws Exception {
        // Given
        String schema = "";
        String[] args = {"-first", "-second"};

        try {
            // When
            underTest = new Jargs(schema, args);
        } catch (JargsArgumentException e) {
            // Then
            assertThat(e.getErrorCode()).isEqualTo(ErrorCode.UNEXPECTED_ARGUMENT);
            assertThat(e.getErrorArgumentName()).isEqualTo("first");
        }
    }

    @Test
    void isShouldThrowIfArgumentNameDoesNotStartWithHyphen() {
        // Given
        String schema = "name*";
        String[] args = {"name", "gians"};

        JargsArgumentException e = new JargsArgumentException(ErrorCode.INVALID_ARGUMENT_NAME, "name", null);

        // When
        // Then
        assertThatThrownBy(() -> underTest = new Jargs(schema, args))
            .isInstanceOf(JargsArgumentException.class)
            .isEqualToComparingFieldByField(e);
    }


    @Test
    void itShouldThrowIfSchemaElementContainsOtherThanALetter() {
        // Given
        String schema = "_test*";
        String[] args = new String[0];

        JargsSchemaException e = new JargsSchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT_NAME, "_test");

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsSchemaException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfSchemaElementFormatIsInvalid() {
        // Given
        String schema = "test&";
        String[] args = new String[0];

        JargsSchemaException e = new JargsSchemaException(ErrorCode.INVALID_SCHEMA_ELEMENT_TYPE, "&");

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsSchemaException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldThrowIfArgumentNameDoesNotMatchSchemaElementName() {
        // Given
        String schema = "testA%";
        String[] args = {"-testB"};

        JargsArgumentException e = new JargsArgumentException(ErrorCode.UNEXPECTED_ARGUMENT, "testB", null);

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsArgumentException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetBooleanValue() throws Exception {
        // Given
        String schema = "bool%";
        String[] args = {"-bool"};

        // When
        underTest = new Jargs(schema, args);

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

        JargsArgumentException e = new JargsArgumentException(ErrorCode.MISSING_STRING, "string", null);

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsArgumentException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetStringValues() throws Exception {
        // Given
        String schema = "name*, job*";
        String[] args = {"-name", "gians", "-job", "developer"};

        // When
        underTest = new Jargs(schema, args);

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

        try {
            new Jargs(schema, args);
        } catch (JargsException e) {
            assertThat(e.getMessage()).isEqualTo(String.format(
                        "Argument name '-%s' expects an integer but was '%s' .", "int", "six"));
        }
    }

    @Test
    void itShouldIThrowIfIntegerArgumentValueIsMissing() {
        // Given
        String schema = "int#";
        String[] args = {"-int"};

        JargsArgumentException e = new JargsArgumentException(ErrorCode.MISSING_INTEGER, "int", null);

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsArgumentException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetIntegerValue() throws Exception {
        // Given
        String schema = "int#";
        String[] args = {"-int", "99"};

        // When
        underTest = new Jargs(schema, args);

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

        JargsArgumentException e = new JargsArgumentException(ErrorCode.INVALID_DOUBLE, "double", "six");

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsArgumentException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldIThrowIfDoubleArgumentValueIsMissing() {
        // Given
        String schema = "double@";
        String[] args = {"-double"};

        JargsArgumentException e = new JargsArgumentException(ErrorCode.MISSING_DOUBLE, "double", null);

        // When
        // Then
        assertThatThrownBy(() -> new Jargs(schema, args))
                .isInstanceOf(JargsArgumentException.class)
                .isEqualToComparingFieldByField(e);
    }

    @Test
    void itShouldSetDoubleValue() throws Exception {
        // Given
        String schema = "double@";
        String[] args = {"-double", "99.05"};

        // When
        underTest = new Jargs(schema, args);

        // Then
        assertThat(underTest.getDouble("double")).isEqualTo(99.05);
        assertThat(underTest.cardinality()).isEqualTo(1);
        assertThat(underTest.has("double")).isTrue();
    }

    @Test
    void itShouldSetAllTheArguments() {
        // Given
        String schema = "first*, second#, third%, forth@";
        String[] args = {"-first", "first-value", "-second", "2", "-third", "-forth", "3.33"};

        // When
        try {
            underTest = new Jargs(schema, args);
        } catch (JargsException e) {
            e.printStackTrace();
            fail();
        }

        // Then
        assertThat(underTest.cardinality()).isEqualTo(4);
        assertThat(underTest.has("first")).isTrue();
        assertThat(underTest.has("second")).isTrue();
        assertThat(underTest.has("third")).isTrue();
        assertThat(underTest.has("forth")).isTrue();
        assertThat(underTest.has("fifth")).isFalse();

        assertThat(underTest.getString("first")).isEqualTo("first-value");
        assertThat(underTest.getInt("second")).isEqualTo(2);
        assertThat(underTest.getBoolean("third")).isTrue();
        assertThat(underTest.getDouble("forth")).isEqualTo(3.33);
    }

}