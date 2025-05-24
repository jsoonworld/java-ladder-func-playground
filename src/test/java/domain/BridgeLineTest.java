package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BridgeLineTest {

    @Test
    @DisplayName("index 0은 연결되어 있지 않다")
    void isConnectedAt_index0_false() {
        BridgeLine line = new BridgeLine(List.of(false, true, false));
        assertThat(line.isConnectedAt(0)).isFalse();
    }

    @Test
    @DisplayName("index 1은 연결되어 있다")
    void isConnectedAt_index1_true() {
        BridgeLine line = new BridgeLine(List.of(false, true, false));
        assertThat(line.isConnectedAt(1)).isTrue();
    }

    @Test
    @DisplayName("index 2는 연결되어 있지 않다")
    void isConnectedAt_index2_false() {
        BridgeLine line = new BridgeLine(List.of(false, true, false));
        assertThat(line.isConnectedAt(2)).isFalse();
    }

    @ParameterizedTest(name = "연결 리스트 {0}의 크기는 {1}이다")
    @MethodSource("widthTestCases")
    @DisplayName("width는 연결 리스트의 크기를 반환한다")
    void width_returnsCorrectSize(List<Boolean> connections, int expectedWidth) {
        BridgeLine line = new BridgeLine(connections);
        assertThat(line.width()).isEqualTo(expectedWidth);
    }

    @Test
    @DisplayName("drawLineFormat은 사다리 가로줄을 시각화된 문자열로 반환한다")
    void drawLineFormat_returnsVisualRepresentation() {
        BridgeLine line = new BridgeLine(List.of(true, false, true));
        String result = line.drawLineFormat(4);

        assertThat(result).isEqualTo("|-----|     |-----|     ");
    }

    private static Stream<Arguments> connectionTestCases() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, true),
                Arguments.of(2, false)
        );
    }

    private static Stream<Arguments> widthTestCases() {
        return Stream.of(
                Arguments.of(List.of(true, false, true), 3),
                Arguments.of(List.of(false, false, false, false), 4)
        );
    }
}
