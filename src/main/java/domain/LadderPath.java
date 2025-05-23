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

        for (int startIndex = 0; startIndex < numberOfColumns; startIndex++) {
            startToEndMap.put(startIndex, traverseFrom(startIndex));
        }

        return startToEndMap;
    }

    private int traverseFrom(int startColumnIndex) {
        int current = startColumnIndex;

        for (BridgeLine line : bridgeLines) {
            current = moveAlongLine(current, line);
        }

        return current;
    }

    private int moveAlongLine(int position, BridgeLine line) {
        if (position > 0 && line.isConnectedAt(position - 1)) return position - 1;
        if (position < line.width() && line.isConnectedAt(position)) return position + 1;
        return position;
    }
}
