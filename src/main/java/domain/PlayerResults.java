package domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlayerResults {

    private static final int MAX_NAME_LENGTH = 5;

    private static final String ERROR_NO_PARTICIPANTS = "[ERROR] 참여자는 한 명 이상이어야 합니다.";
    private static final String ERROR_NAME_TOO_LONG = "[ERROR] 참여자 이름은 5자 이하만 가능합니다: ";
    private static final String ERROR_RESULT_COUNT_MISMATCH = "[ERROR] 실행 결과 수가 참여자 수와 일치해야 합니다.";

    private final Map<String, String> resultByPlayerName;

    private PlayerResults(Map<String, String> resultByPlayerName) {
        this.resultByPlayerName = resultByPlayerName;
    }

    public static PlayerResults from(List<String> playerNames, List<String> outcomeLabels, Map<Integer, Integer> startToEndIndexMap) {
        validate(playerNames, outcomeLabels);
        Map<String, String> mappedResults = mapResults(playerNames, outcomeLabels, startToEndIndexMap);
        return new PlayerResults(mappedResults);
    }

    private static void validate(List<String> playerNames, List<String> outcomeLabels) {
        validateNotEmpty(playerNames);
        validateNameLength(playerNames);
        validateSizeMatch(playerNames, outcomeLabels);
    }

    private static void validateNotEmpty(List<String> playerNames) {
        if (playerNames.isEmpty()) throw new IllegalArgumentException(ERROR_NO_PARTICIPANTS);
    }

    private static void validateNameLength(List<String> playerNames) {
        for (String name : playerNames) {
            validateSingleNameLength(name);
        }
    }

    private static void validateSingleNameLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(ERROR_NAME_TOO_LONG + name);
        }
    }

    private static void validateSizeMatch(List<String> playerNames, List<String> outcomeLabels) {
        if (playerNames.size() != outcomeLabels.size()) {
            throw new IllegalArgumentException(ERROR_RESULT_COUNT_MISMATCH);
        }
    }

    private static Map<String, String> mapResults(List<String> names, List<String> labels, Map<Integer, Integer> indexMap) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            String label = labels.get(indexMap.get(i));
            resultMap.put(name, label);
        }
        return resultMap;
    }

    public String resultOf(String playerName) {
        return resultByPlayerName.get(playerName);
    }

    public Map<String, String> allResults() {
        return resultByPlayerName;
    }

    public boolean hasPlayer(String playerName) {
        return resultByPlayerName.containsKey(playerName);
    }
}
