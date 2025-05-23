package util;

import java.util.Arrays;
import java.util.List;

public class ValidatedInputParser {

    private static final int MAX_NAME_LENGTH = 5;
    private static final String ERROR_NO_PARTICIPANTS = "[ERROR] 참여자는 한 명 이상이어야 합니다.";
    private static final String ERROR_NAME_TOO_LONG = "[ERROR] 참여자 이름은 5자 이하만 가능합니다: ";
    private static final String ERROR_RESULT_COUNT_MISMATCH = "[ERROR] 실행 결과 수가 참여자 수와 일치해야 합니다.";
    private static final String ERROR_INVALID_ROW_COUNT = "[ERROR] 사다리 높이는 1 이상이어야 합니다.";
    private static final String ERROR_INVALID_NUMBER_FORMAT = "[ERROR] 숫자를 입력해 주세요.";

    public static List<String> parseParticipantNames(String rawInput) {
        List<String> participantNames = Arrays.asList(rawInput.trim().split(","));
        validateParticipantNamesExist(participantNames);
        validateParticipantNameLengths(participantNames);
        return participantNames;
    }

    public static List<String> parseResultLabels(String rawInput, int expectedCount) {
        List<String> resultLabels = Arrays.asList(rawInput.trim().split(","));
        validateResultLabelCount(resultLabels, expectedCount);
        return resultLabels;
    }

    public static int parseRowCount(String rawInput) {
        try {
            int rowCount = Integer.parseInt(rawInput.trim());
            validateRowCountPositive(rowCount);
            return rowCount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_INVALID_NUMBER_FORMAT);
        }
    }

    private static void validateParticipantNamesExist(List<String> participantNames) {
        if (participantNames.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_PARTICIPANTS);
        }
    }

    private static void validateParticipantNameLengths(List<String> participantNames) {
        participantNames.stream()
                .filter(name -> name.length() > MAX_NAME_LENGTH)
                .findFirst()
                .ifPresent(name -> {
                    throw new IllegalArgumentException(ERROR_NAME_TOO_LONG + name);
                });
    }

    private static void validateResultLabelCount(List<String> resultLabels, int expectedCount) {
        if (resultLabels.size() != expectedCount) {
            throw new IllegalArgumentException(ERROR_RESULT_COUNT_MISMATCH);
        }
    }

    private static void validateRowCountPositive(int rowCount) {
        if (rowCount <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_ROW_COUNT);
        }
    }
}
