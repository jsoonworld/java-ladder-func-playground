package view;

import domain.BridgeLine;
import dto.LadderBuildResponse;

import java.util.List;

public class OutputView {

    private static final String LADDER_OUTPUT_TITLE = "실행결과";
    private static final String PILLAR = "|";
    private static final String CONNECTED = "-----";
    private static final String NOT_CONNECTED = "     ";

    public void printLadderTitle() {
        System.out.println(LADDER_OUTPUT_TITLE);
        System.out.println();
    }

    public void printLadder(LadderBuildResponse response) {
        int columnCount = response.columnCount();

        for (BridgeLine line : response.lines()) {
            String renderedRow = renderRow(line.connections(), columnCount);
            System.out.println("    " + renderedRow);
        }
    }

    private String renderRow(List<Boolean> connections, int columnCount) {
        StringBuilder row = new StringBuilder();

        for (int col = 0; col < columnCount; col++) {
            row.append(PILLAR);
            row.append(renderBridge(connections, col));
        }

        return row.toString();
    }

    private String renderBridge(List<Boolean> connections, int index) {
        if (index >= connections.size()) {
            return "";
        }

        if (connections.get(index)) {
            return CONNECTED;
        }

        return NOT_CONNECTED;
    }
}
