package dto;

import domain.BridgeLine;

import java.util.List;

public record LadderBuildResponse(int columnCount, List<BridgeLine> lines) {
}
