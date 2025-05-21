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
        int columnCount = request.count();
        int middleIndex = (columnCount - 1) / 2;

        while (true) {
            LadderBuildResponse response = generateValidBoardRows(columnCount, middleIndex);
            LadderBuildResponse validBoard = returnIfValidBoard(response);
            if (validBoard != null) return validBoard;
        }
    }

    private static LadderBuildResponse generateValidBoardRows(int columnCount, int middleIndex) {
        List<String> rows = new ArrayList<>();
        boolean middleBridgeExists = false;
        Set<Integer> connectedPositions = new HashSet<>();

        for (int i = 0; i < columnCount; i++) {
            List<Boolean> bridgeConnectionStates = generateBridgeConnectionStates(columnCount);
            LadderRow row = new LadderRow(columnCount, bridgeConnectionStates);
            rows.add(row.draw());

            middleBridgeExists = updateMiddleBridgePresence(middleBridgeExists, row, middleIndex);
            recordConnectedPositions(connectedPositions, bridgeConnectionStates);
        }

        if (!allPositionsConnected(connectedPositions, columnCount - 1)) return null;
        return returnIfMiddleBridgeExists(rows, middleBridgeExists);
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
        if (index == 0) return true;
        return !connectionStates.get(index - 1);
    }

    private static boolean decideBridgePlacement(boolean connectable) {
        if (!connectable) return false;
        return random.nextBoolean();
    }

    private static boolean updateMiddleBridgePresence(
            boolean currentState,
            LadderRow row,
            int middleIndex
    ) {
        if (row.hasMiddleBridge(middleIndex)) return true;
        return currentState;
    }

    private static void recordConnectedPositions(Set<Integer> connected, List<Boolean> currentLine) {
        for (int i = 0; i < currentLine.size(); i++) {
            addIfConnected(connected, currentLine.get(i), i);
        }
    }

    private static void addIfConnected(Set<Integer> connected, boolean isConnected, int index) {
        if (!isConnected) return;
        connected.add(index);
    }

    private static boolean allPositionsConnected(Set<Integer> connected, int bridgeCount) {
        return connected.size() == bridgeCount;
    }

    private static LadderBuildResponse returnIfMiddleBridgeExists(List<String> rows, boolean exists) {
        if (!exists) return null;
        return new LadderBuildResponse(rows);
    }

    private static LadderBuildResponse returnIfValidBoard(LadderBuildResponse response) {
        if (response == null) return null;
        return response;
    }
}
