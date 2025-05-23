package domain;

import dto.LadderBuildRequest;
import dto.LadderBuildResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LadderBoard {

    private static final Random random = new Random();

    public static LadderBuildResponse build(LadderBuildRequest request) {
        int columnCount = request.columnCount();
        int rowCount = request.rowCount();
        int middleIndex = (columnCount - 1) / 2;

        List<BridgeLine> validLines = createValidBridgeLines(columnCount, rowCount, middleIndex);
        return new LadderBuildResponse(columnCount, validLines);
    }

    private static List<BridgeLine> createValidBridgeLines(int columnCount, int rowCount, int middleIndex) {
        while (true) {
            List<BridgeLine> lines = generateBridgeLines(columnCount, rowCount, middleIndex);
            if (lines != null) return lines;
        }
    }

    private static List<BridgeLine> generateBridgeLines(int columnCount, int rowCount, int middleIndex) {
        List<BridgeLine> lines = new ArrayList<>();
        boolean middleBridgeExists = false;
        Set<Integer> connectedPositions = new HashSet<>();

        for (int i = 0; i < rowCount; i++) {
            List<Boolean> bridgeConnectionStates = generateBridgeConnectionStates(columnCount);
            BridgeLine bridgeLine = new BridgeLine(bridgeConnectionStates);
            lines.add(bridgeLine);

            middleBridgeExists |= isMiddleBridgeConnected(bridgeLine, middleIndex);
            recordConnectedPositions(connectedPositions, bridgeConnectionStates);
        }

        if (!allPositionsConnected(connectedPositions, columnCount - 1)) return null;
        if (!middleBridgeExists) return null;
        return lines;
    }

    private static List<Boolean> generateBridgeConnectionStates(int columnCount) {
        List<Boolean> connectionStates = new ArrayList<>();

        for (int index = 0; index < columnCount - 1; index++) {
            boolean connectable = isConnectable(connectionStates, index);
            boolean shouldPlaceBridge = decideBridgePlacement(connectable);
            connectionStates.add(shouldPlaceBridge);
        }

        return connectionStates;
    }

    private static boolean isConnectable(List<Boolean> connectionStates, int index) {
        return index == 0 || !connectionStates.get(index - 1);
    }

    private static boolean decideBridgePlacement(boolean connectable) {
        return connectable && random.nextBoolean();
    }

    private static boolean isMiddleBridgeConnected(BridgeLine line, int middleIndex) {
        return middleIndex < line.size() && line.isConnected(middleIndex);
    }

    private static void recordConnectedPositions(Set<Integer> connected, List<Boolean> currentLine) {
        for (int i = 0; i < currentLine.size(); i++) {
            if (currentLine.get(i)) connected.add(i);
        }
    }

    private static boolean allPositionsConnected(Set<Integer> connected, int bridgeCount) {
        return connected.size() == bridgeCount;
    }
}
