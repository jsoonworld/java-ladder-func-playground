package domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerResultsTest {

    @Test
    @DisplayName("참가자 이름, 결과, 인덱스 맵으로 결과를 생성한다")
    void from_createsCorrectMapping() {
        List<String> playerNames = List.of("neo", "brown", "brie", "tommy");
        List<String> outcomeLabels = List.of("꽝", "5000", "꽝", "3000");

        Map<Integer, Integer> startToEndIndexMap = Map.of(
                0, 1,
                1, 3,
                2, 0,
                3, 2
        );

        PlayerResults results = PlayerResults.from(playerNames, outcomeLabels, startToEndIndexMap);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(results.resultOf("neo")).isEqualTo("5000");
        softly.assertThat(results.resultOf("brown")).isEqualTo("3000");
        softly.assertThat(results.resultOf("brie")).isEqualTo("꽝");
        softly.assertThat(results.resultOf("tommy")).isEqualTo("꽝");

        softly.assertThat(results.hasPlayer("neo")).isTrue();
        softly.assertThat(results.hasPlayer("noname")).isFalse();

        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("neo", "5000");
        expected.put("brown", "3000");
        expected.put("brie", "꽝");
        expected.put("tommy", "꽝");

        softly.assertThat(results.allResults()).containsExactlyEntriesOf(expected);
        softly.assertAll();
    }

    @Test
    @DisplayName("참가자가 없을 경우 예외 발생")
    void throwsIfEmptyParticipants() {
        List<String> empty = List.of();
        List<String> labels = List.of();
        Map<Integer, Integer> indexMap = Map.of();
        assertThrows(IllegalArgumentException.class, () ->
                PlayerResults.from(empty, labels, indexMap));
    }
}
