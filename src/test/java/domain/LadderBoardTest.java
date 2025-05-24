package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LadderBoardTest {

    @Test
    @DisplayName("요청에 따라 사다리를 생성하고 높이와 열 수가 일치해야 한다")
    void build_createsValidLadderStructure() {
        int columnCount = 4;
        int rowCount = 5;

        LadderBoard board = LadderBoard.build(columnCount, rowCount);

        assertThat(board.getColumnCount()).isEqualTo(columnCount);
        assertThat(board.getLines()).hasSize(rowCount);
    }

    @Test
    @DisplayName("생성된 사다리는 가운데 열에 적어도 하나의 연결이 포함되어야 한다")
    void build_containsAtLeastOneCenterConnection() {
        int columnCount = 5;
        int rowCount = 10;
        int centerIndex = (columnCount - 1) / 2;

        LadderBoard board = LadderBoard.build(columnCount, rowCount);
        List<BridgeLine> lines = board.getLines();

        boolean hasCenterConnection = lines.stream()
                .anyMatch(line -> line.isConnectedAt(centerIndex));

        assertThat(hasCenterConnection).isTrue();
    }

    @DisplayName("참가자 수가 2명 미만이면 예외를 던진다")
    @Test
    void throwsIfParticipantsLessThanTwo() {
        assertThatThrownBy(() -> LadderBoard.build(1, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사다리는 최소 2명 이상의 참가자가 필요합니다.");
    }

    @DisplayName("사다리 높이가 1 미만이면 예외를 던진다")
    @Test
    void throwsIfHeightLessThanOne() {
        assertThatThrownBy(() -> LadderBoard.build(4, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 사다리 높이는 1 이상이어야 합니다.");
    }
}
