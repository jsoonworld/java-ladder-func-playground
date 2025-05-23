package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatedInputParserTest {

    @Nested
    @DisplayName("참여자 이름 파싱")
    class ParseParticipantNames {

        @Test
        @DisplayName("쉼표로 구분된 참여자 이름들을 파싱한다")
        void parsesParticipantNames() {
            List<String> result = ValidatedInputParser.parseParticipantNames("neo,brown");
            assertThat(result).containsExactly("neo", "brown");
        }

        @Test
        @DisplayName("참여자가 없는 경우 예외를 던진다")
        void throwsIfNoParticipants() {
            assertThatThrownBy(() -> ValidatedInputParser.parseParticipantNames(""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 참여자는 한 명 이상이어야 합니다.");
        }

        @Test
        @DisplayName("이름이 5자를 초과하면 예외를 던진다")
        void throwsIfNameTooLong() {
            assertThatThrownBy(() -> ValidatedInputParser.parseParticipantNames("neo,longname"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 참여자 이름은 5자 이하만 가능합니다: longname");
        }
    }

    @Nested
    @DisplayName("결과 라벨 파싱")
    class ParseResultLabels {

        @Test
        @DisplayName("결과 수가 참여자 수와 다르면 예외를 던진다")
        void throwsIfResultCountMismatch() {
            assertThatThrownBy(() -> ValidatedInputParser.parseResultLabels("꽝,당첨", 3))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 실행 결과 수가 참여자 수와 일치해야 합니다.");
        }

        @Test
        @DisplayName("결과 라벨들을 파싱한다")
        void parsesResultLabels() {
            List<String> result = ValidatedInputParser.parseResultLabels("꽝,5000,3000", 3);
            assertThat(result).containsExactly("꽝", "5000", "3000");
        }
    }

    @Nested
    @DisplayName("사다리 높이 파싱")
    class ParseRowCount {

        @Test
        @DisplayName("숫자가 아니면 예외를 던진다")
        void throwsIfNotNumber() {
            assertThatThrownBy(() -> ValidatedInputParser.parseRowCount("five"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 숫자를 입력해 주세요.");
        }

        @Test
        @DisplayName("0 이하의 숫자면 예외를 던진다")
        void throwsIfZeroOrNegative() {
            assertThatThrownBy(() -> ValidatedInputParser.parseRowCount("0"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 사다리 높이는 1 이상이어야 합니다.");
        }

        @Test
        @DisplayName("숫자를 정상적으로 파싱한다")
        void parsesRowCount() {
            int result = ValidatedInputParser.parseRowCount("7");
            assertThat(result).isEqualTo(7);
        }
    }
}
