package view;

import dto.LadderBuildResponse;

public class OutputView {

    private static final String LADDER_OUTPUT_TITLE = "실행결과";

    public void printLadderTitle() {
        System.out.println(LADDER_OUTPUT_TITLE);
        System.out.println();
    }

    public void printLadder(LadderBuildResponse response) {
        for (String row : response.rows()) {
            System.out.println("    " + row);
        }
    }
}
