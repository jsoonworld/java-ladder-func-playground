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
        int participantCount = request.columnCount();
        int ladderHeight = request.rowCount();
        int centerColumnIndex = (participantCount - 1) / 2;

        List<BridgeLine> bridgeLines = generateValidBridgeLines(participantCount, ladderHeight, centerColumnIndex);
        return new LadderBuildResponse(participantCount, bridgeLines);
    }

    private static List<BridgeLine> generateValidBridgeLines(int participantCount, int ladderHeight, int centerColumnIndex) {
        while (true) {
            List<BridgeLine> candidateBridgeLines = tryGenerateBridgeLines(participantCount, ladderHeight, centerColumnIndex);
            if (candidateBridgeLines != null) return candidateBridgeLines;
        }
    }

    private static List<BridgeLine> tryGenerateBridgeLines(int participantCount, int ladderHeight, int centerColumnIndex) {
        List<BridgeLine> generatedBridgeLines = new ArrayList<>();
        boolean isCenterBridgePresent = false;
        Set<Integer> connectedColumnIndices = new HashSet<>();

        for (int row = 0; row < ladderHeight; row++) {
            List<Boolean> connectionStates = generateConnectionStates(participantCount);
            BridgeLine line = new BridgeLine(connectionStates);
            generatedBridgeLines.add(line);

            isCenterBridgePresent |= isConnectedAtCenter(line, centerColumnIndex);
            recordConnectedIndices(connectedColumnIndices, connectionStates);
        }

        if (!areAllColumnsConnected(connectedColumnIndices, participantCount - 1)) return null;
        if (!isCenterBridgePresent) return null;

        return generatedBridgeLines;
    }

    private static List<Boolean> generateConnectionStates(int participantCount) {
        List<Boolean> connectionStates = new ArrayList<>();
        for (int index = 0; index < participantCount - 1; index++) {
            boolean canConnect = index == 0 || !connectionStates.get(index - 1);
            boolean shouldConnect = canConnect && random.nextBoolean();
            connectionStates.add(shouldConnect);
        }
        return connectionStates;
    }

    private static boolean isConnectedAtCenter(BridgeLine line, int centerIndex) {
        return centerIndex < line.width() && line.isConnectedAt(centerIndex);
    }

    private static void recordConnectedIndices(Set<Integer> connectedColumnIndices, List<Boolean> connectionStates) {
        for (int i = 0; i < connectionStates.size(); i++) {
            if (connectionStates.get(i)) connectedColumnIndices.add(i);
        }
    }

    private static boolean areAllColumnsConnected(Set<Integer> connectedColumnIndices, int expectedCount) {
        return connectedColumnIndices.size() == expectedCount;
    }
}
