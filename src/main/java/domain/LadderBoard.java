package domain;

import java.util.*;

public class LadderBoard {

    private static final Random RANDOM = new Random();
    private static final int MAX_ATTEMPTS = 100;
    private static final int MIN_PARTICIPANTS = 2;
    private static final int MIN_HEIGHT = 1;

    private static final String ERROR_TOO_FEW_PARTICIPANTS = "[ERROR] 사다리는 최소 2명 이상의 참가자가 필요합니다.";
    private static final String ERROR_INVALID_HEIGHT = "[ERROR] 사다리 높이는 1 이상이어야 합니다.";
    private static final String ERROR_GENERATION_FAILED = "[ERROR] 사다리를 생성하지 못했습니다. 참가자 수나 높이를 확인해 주세요.";

    private final int columnCount;
    private final List<BridgeLine> lines;

    private LadderBoard(int columnCount, List<BridgeLine> lines) {
        this.columnCount = columnCount;
        this.lines = lines;
    }

    public static LadderBoard build(int columnCount, int rowCount) {
        validate(columnCount, rowCount);
        int centerColumnIndex = (columnCount - 1) / 2;
        List<BridgeLine> bridgeLines = generateValidBridgeLines(columnCount, rowCount, centerColumnIndex);
        return new LadderBoard(columnCount, bridgeLines);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public List<BridgeLine> getLines() {
        return lines;
    }

    private static void validate(int columnCount, int rowCount) {
        validateParticipantCount(columnCount);
        validateLadderHeight(rowCount);
    }

    private static void validateParticipantCount(int columnCount) {
        if (columnCount < MIN_PARTICIPANTS) {
            throw new IllegalArgumentException(ERROR_TOO_FEW_PARTICIPANTS);
        }
    }

    private static void validateLadderHeight(int rowCount) {
        if (rowCount < MIN_HEIGHT) {
            throw new IllegalArgumentException(ERROR_INVALID_HEIGHT);
        }
    }

    private static List<BridgeLine> generateValidBridgeLines(int participantCount, int ladderHeight, int centerColumnIndex) {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            List<BridgeLine> candidateBridgeLines = tryGenerateBridgeLines(participantCount, ladderHeight, centerColumnIndex);
            if (candidateBridgeLines != null) return candidateBridgeLines;
        }
        throw new IllegalStateException(ERROR_GENERATION_FAILED);
    }

    private static List<BridgeLine> tryGenerateBridgeLines(int participantCount, int ladderHeight, int centerColumnIndex) {
        List<BridgeLine> generatedBridgeLines = new ArrayList<>();
        boolean isCenterBridgePresent = false;
        Set<Integer> connectedColumnIndices = new HashSet<>();

        for (int row = 0; row < ladderHeight; row++) {
            List<Boolean> connectionStates = generateConnectionStates(participantCount);
            BridgeLine line = new BridgeLine(connectionStates);
            generatedBridgeLines.add(line);

            if (isConnectedAtCenter(line, centerColumnIndex)) {
                isCenterBridgePresent = true;
            }

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
            boolean shouldConnect = canConnect && RANDOM.nextBoolean();
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
