package controller;

import domain.LadderBoard;
import domain.LadderResult;
import dto.LadderBuildRequest;
import dto.LadderBuildResponse;
import dto.LadderResultResponse;
import view.InputView;
import view.OutputView;

public class LadderController {

    private final InputView inputView;
    private final OutputView outputView;

    public LadderController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        int columnCount = inputView.readColumnCount();
        int rowCount = inputView.readRowCount();

        LadderBuildRequest request = new LadderBuildRequest(columnCount, rowCount);
        LadderBuildResponse response = LadderBoard.build(request);

        outputView.printLadderTitle();
        outputView.printLadder(response);

        LadderResult result = new LadderResult(response.lines(), response.columnCount());
        LadderResultResponse resultResponse = new LadderResultResponse(result.mapStartToEnd());

        outputView.printResult(resultResponse);
    }
}
