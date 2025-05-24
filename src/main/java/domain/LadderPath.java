package domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LadderPath {

    private final List<BridgeLine> bridgeLines;
    private final int numberOfColumns;

    public LadderPath(List<BridgeLine> bridgeLines, int numberOfColumns) {
        this.bridgeLines = bridgeLines;
        this.numberOfColumns = numberOfColumns;
    }

    public Map<Integer, Integer> mapStartToEndIndex() {
        Map<Integer, Integer> startToEndMap = new LinkedHashMap<>();

        for (int startColumnIndex = 0; startColumnIndex < numberOfColumns; startColumnIndex++) {
            startToEndMap.put(startColumnIndex, tracePathFrom(startColumnIndex));
        }

        return startToEndMap;
    }

    private int tracePathFrom(int startColumnIndex) {
        int currentPosition = startColumnIndex;

        for (BridgeLine bridgeLine : bridgeLines) {
            currentPosition = bridgeLine.nextPositionFrom(currentPosition);
        }

        return currentPosition;
    }
}
