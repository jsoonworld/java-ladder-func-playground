package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BridgeLineTest {

    @ParameterizedTest(name = "index={0}일 때 연결 여부는 {1}이다")
    @MethodSource("connectionTestCases")
    @DisplayName("isConnectedAt는 주어진 인덱스의 연결 상태를 반환한다")
    void isConnectedAt_returnsCorrectStatus(int index, boolean expected) {
        BridgeLine line = new BridgeLine(List.of(false, true, false));
        assertThat(line.isConnectedAt(index)).isEqualTo(expected);
    }

    @ParameterizedTest(name = "연결 리스트 {0}의 크기는 {1}이다")
    @MethodSource("widthTestCases")
    @DisplayName("width는 연결 리스트의 크기를 반환한다")
    void width_returnsCorrectSize(List<Boolean> connections, int expectedWidth) {
        BridgeLine line = new BridgeLine(connections);
        assertThat(line.width()).isEqualTo(expectedWidth);
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
