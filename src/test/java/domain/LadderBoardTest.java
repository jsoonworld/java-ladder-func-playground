package domain;

import dto.LadderBuildRequest;
import dto.LadderBuildResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LadderBoardTest {

    @Test
    @DisplayName("요청에 따라 사다리를 생성하고 높이와 열 수가 일치해야 한다")
    void build_createsValidLadderStructure() {
        int columnCount = 4;
        int rowCount = 5;
        LadderBuildRequest request = new LadderBuildRequest(columnCount, rowCount);

        LadderBuildResponse response = LadderBoard.build(request);

        assertThat(response.columnCount()).isEqualTo(columnCount);
        assertThat(response.lines()).hasSize(rowCount);
    }

    @Test
    @DisplayName("생성된 사다리는 가운데 열에 적어도 하나의 연결이 포함되어야 한다")
    void build_containsAtLeastOneCenterConnection() {
        int columnCount = 5;
        int rowCount = 10;
        int centerIndex = (columnCount - 1) / 2;
        LadderBuildRequest request = new LadderBuildRequest(columnCount, rowCount);

        LadderBuildResponse response = LadderBoard.build(request);
        List<BridgeLine> lines = response.lines();

        boolean hasCenterConnection = lines.stream()
                .anyMatch(line -> line.isConnectedAt(centerIndex));

        assertThat(hasCenterConnection).isTrue();
    }
}
