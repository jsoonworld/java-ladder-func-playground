package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NumericParserTest {

    @Test
    @DisplayName("정수 문자열을 숫자로 파싱한다")
    void parsesValidInteger() {
        int result = NumericParser.parse("42");
        assertThat(result).isEqualTo(42);
    }

    @Test
    @DisplayName("숫자가 아닌 문자열을 파싱하면 예외를 던진다")
    void throwsIfNotNumber() {
        assertThatThrownBy(() -> NumericParser.parse("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 숫자를 입력해 주세요.");
    }
}
