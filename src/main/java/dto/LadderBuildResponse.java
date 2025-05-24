package dto;

import domain.BridgeLine;
import domain.LadderBoard;

import java.util.List;

public record LadderBuildResponse(int columnCount, List<BridgeLine> lines) {
    public static LadderBuildResponse from(LadderBoard board) {
        return new LadderBuildResponse(board.getColumnCount(), board.getLines());
    }
}

