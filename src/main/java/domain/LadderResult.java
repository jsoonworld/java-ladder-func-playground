package domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LadderResult {

    private final List<BridgeLine> lines;
    private final int columnCount;

    public LadderResult(List<BridgeLine> lines, int columnCount) {
        this.lines = lines;
        this.columnCount = columnCount;
    }

    public Map<Integer, Integer> mapStartToEnd() {
        Map<Integer, Integer> result = new LinkedHashMap<>();

        for (int start = 0; start < columnCount; start++) {
            result.put(start, traverse(start));
        }

        return result;
    }

    private int traverse(int startIndex) {
        int position = startIndex;

        for (BridgeLine line : lines) {
            position = move(position, line);
        }

        return position;
    }

    private int move(int position, BridgeLine line) {
        if (position > 0 && line.isConnected(position - 1)) {
            return position - 1;
        }

        if (position < line.size() && line.isConnected(position)) {
            return position + 1;
        }

        return position;
    }
}
