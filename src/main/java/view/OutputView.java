package view;

import dto.LadderBuildResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    private static final String PARTICIPANT_PROMPT = "참여할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)";
    private static final String RESULT_PROMPT = "실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)";
    private static final String HEIGHT_PROMPT = "사다리의 높이는 몇 칸인가요?";
    private static final String RESULT_SELECTION_PROMPT = "결과를 보고 싶은 사람은?";
    private static final String LADDER_RESULT_TITLE = "실행 결과";
    private static final String NAME_NOT_FOUND_MESSAGE = "해당 이름은 존재하지 않습니다.";

    private static final String VERTICAL_BAR = "|";
    private static final String CONNECTED_LINE = "-----";
    private static final String EMPTY_LINE = "     ";
    private static final int DISPLAY_CELL_WIDTH = 6;
    private static final String EMPTY_PREFIX = " ";
    private static final String CELL_FORMAT = "%-" + DISPLAY_CELL_WIDTH + "s";
    private static final String RESULT_FORMAT = "%s : %s";

    public void printParticipantPrompt() {
        System.out.println(PARTICIPANT_PROMPT);
    }

    public void printResultPrompt() {
        System.out.println(RESULT_PROMPT);
    }

    public void printHeightPrompt() {
        System.out.println(HEIGHT_PROMPT);
    }

    public void printResultSelectionPrompt() {
        System.out.println(RESULT_SELECTION_PROMPT);
    }

    public void printLadderTitle() {
        System.out.println(LADDER_RESULT_TITLE);
    }

    public void printParticipantNames(List<String> participantNames) {
        String alignedNames = participantNames.stream()
                .map(this::formatCell)
                .collect(Collectors.joining());
        System.out.println(alignedNames);
    }

    public void printResultLabels(List<String> resultLabels) {
        String alignedResults = resultLabels.stream()
                .map(this::formatCell)
                .collect(Collectors.joining());
        System.out.println(alignedResults);
    }

    public void printBridgeLines(LadderBuildResponse response) {
        List<List<Boolean>> allBridgeLines = response.lines().stream()
                .map(line -> line.getConnections())
                .collect(Collectors.toList());

        int totalColumns = response.columnCount();

        for (List<Boolean> bridgeConnections : allBridgeLines) {
            String formattedLine = renderBridgeLine(bridgeConnections, totalColumns);
            System.out.println(EMPTY_PREFIX + formattedLine);
        }
    }

    public void printAllResults(Map<String, String> participantResults) {
        for (Map.Entry<String, String> entry : participantResults.entrySet()) {
            System.out.println(formatResult(entry.getKey(), entry.getValue()));
        }
    }

    public void printSingleResult(String resultValue) {
        System.out.println(resultValue);
    }

    public void printNameNotFound() {
        System.out.println(NAME_NOT_FOUND_MESSAGE);
    }

    private String formatResult(String name, String result) {
        return String.format(RESULT_FORMAT, name, result);
    }

    private String renderBridgeLine(List<Boolean> connections, int totalColumns) {
        StringBuilder lineBuilder = new StringBuilder();

        for (int columnIndex = 0; columnIndex < totalColumns; columnIndex++) {
            lineBuilder.append(VERTICAL_BAR);
            lineBuilder.append(renderBridgeBetweenColumns(connections, columnIndex));
        }

        return lineBuilder.toString();
    }

    private String renderBridgeBetweenColumns(List<Boolean> connections, int columnIndex) {
        if (isOutOfConnectionRange(connections, columnIndex)) {
            return EMPTY_LINE;
        }

        if (isConnected(connections, columnIndex)) {
            return CONNECTED_LINE;
        }

        return EMPTY_LINE;
    }

    private boolean isOutOfConnectionRange(List<Boolean> connections, int columnIndex) {
        return columnIndex >= connections.size();
    }

    private boolean isConnected(List<Boolean> connections, int columnIndex) {
        return connections.get(columnIndex);
    }

    private String formatCell(String value) {
        return String.format(CELL_FORMAT, value);
    }
}
