package controller;

import domain.LadderBoard;
import dto.LadderBuildRequest;
import dto.LadderBuildResponse;
import view.OutputView;

public class LadderController {

    private final OutputView outputView;

    public LadderController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void run(int columnCount) {
        LadderBuildRequest request = new LadderBuildRequest(columnCount);
        LadderBuildResponse response = LadderBoard.build(request);

        outputView.printLadderTitle();
        outputView.printLadder(response);
    }
}
