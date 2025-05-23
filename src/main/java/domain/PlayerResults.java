package domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlayerResults {

    private final Map<String, String> resultByPlayerName;

    private PlayerResults(Map<String, String> resultByPlayerName) {
        this.resultByPlayerName = resultByPlayerName;
    }

    public static PlayerResults from(List<String> playerNames, List<String> outcomeLabels, Map<Integer, Integer> startToEndIndexMap) {
        Map<String, String> mappedResults = new LinkedHashMap<>();
        for (int playerIndex = 0; playerIndex < playerNames.size(); playerIndex++) {
            int resultIndex = startToEndIndexMap.get(playerIndex);
            String playerName = playerNames.get(playerIndex);
            String outcomeLabel = outcomeLabels.get(resultIndex);
            mappedResults.put(playerName, outcomeLabel);
        }
        return new PlayerResults(mappedResults);
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
