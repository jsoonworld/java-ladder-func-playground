package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

class LadderPathTest {

    @Test
    @DisplayName("3줄 사다리에서 각 참가자의 최종 위치를 정확히 계산한다")
    void mapStartToEndIndex_correctlyMapsPath() {
        List<BridgeLine> bridgeLines = List.of(
                new BridgeLine(List.of(true, false, true)),
                new BridgeLine(List.of(false, true, false)),
                new BridgeLine(List.of(true, false, false))
        );

        LadderPath path = new LadderPath(bridgeLines, 4);

        Map<Integer, Integer> result = path.mapStartToEndIndex();

        Map<Integer, Integer> expected = new LinkedHashMap<>();
        expected.put(0, 2);
        expected.put(1, 1);
        expected.put(2, 3);
        expected.put(3, 0);

        assertThat(result).containsExactlyEntriesOf(expected);
    }
}
