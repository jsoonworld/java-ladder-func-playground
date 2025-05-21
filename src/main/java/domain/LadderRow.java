package domain;

import java.util.List;

public class LadderRow {

    private final int columnCount;
    private final List<BridgeUnit> bridges;

    public LadderRow(int columnCount, List<Boolean> bridgeConnectionStates) {
        this.columnCount = columnCount;
        this.bridges = createBridgesFromStates(bridgeConnectionStates);
    }

    public String draw() {
        StringBuilder line = new StringBuilder();

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            line.append(PillarUnit.SYMBOL);
            appendBridgeIfPresent(line, columnIndex);
        }

        return line.toString();
    }

    public boolean hasMiddleBridge(int middleIndex) {
        if (isIndexOutOfBridgeRange(middleIndex)) {
            return false;
        }

        return bridges.get(middleIndex).isConnected();
    }

    private List<BridgeUnit> createBridgesFromStates(List<Boolean> connectionStates) {
        return connectionStates.stream()
                .map(BridgeUnit::new)
                .toList();
    }

    private void appendBridgeIfPresent(StringBuilder line, int index) {
        if (isIndexOutOfBridgeRange(index)) return;
        line.append(bridges.get(index).draw());
    }

    private boolean isIndexOutOfBridgeRange(int index) {
        return index >= bridges.size();
    }
}
