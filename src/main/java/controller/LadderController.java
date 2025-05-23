package controller;

import domain.LadderBoard;
import domain.LadderPath;
import domain.PlayerResults;
import dto.LadderBuildRequest;
import dto.LadderBuildResponse;
import dto.LadderResultResponse;
import util.ValidatedInputParser;
import view.InputView;
import view.OutputView;

import java.util.List;

public class LadderController {

    private final InputView inputView;
    private final OutputView outputView;

    public LadderController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<String> participantNames = getValidatedParticipantNames();
        List<String> resultLabels = getValidatedResultLabels(participantNames.size());
        int ladderHeight = getValidatedLadderHeight();

        LadderBuildResponse ladder = buildLadder(participantNames.size(), ladderHeight);

        displayLadder(participantNames, resultLabels, ladder);

        PlayerResults playerResults = mapPlayerResults(participantNames, resultLabels, ladder);

        handleResultQueries(playerResults);
    }

    private List<String> getValidatedParticipantNames() {
        outputView.printParticipantPrompt();
        String rawParticipantInput = inputView.readParticipantNames();
        return ValidatedInputParser.parseParticipantNames(rawParticipantInput);
    }

    private List<String> getValidatedResultLabels(int expectedCount) {
        outputView.printResultPrompt();
        String rawResultInput = inputView.readResultLabels();
        return ValidatedInputParser.parseResultLabels(rawResultInput, expectedCount);
    }

    private int getValidatedLadderHeight() {
        outputView.printHeightPrompt();
        String rawHeightInput = inputView.readLadderHeight();
        return ValidatedInputParser.parseRowCount(rawHeightInput);
    }

    private LadderBuildResponse buildLadder(int numberOfColumns, int numberOfRows) {
        LadderBuildRequest buildRequest = new LadderBuildRequest(numberOfColumns, numberOfRows);
        return LadderBoard.build(buildRequest);
    }

    private void displayLadder(List<String> participantNames, List<String> resultLabels, LadderBuildResponse ladder) {
        outputView.printLadderTitle();
        outputView.printParticipantNames(participantNames);
        outputView.printBridgeLines(ladder);
        outputView.printResultLabels(resultLabels);
    }

    private PlayerResults mapPlayerResults(List<String> participantNames, List<String> resultLabels, LadderBuildResponse ladder) {
        LadderPath ladderPath = new LadderPath(ladder.lines(), ladder.columnCount());
        LadderResultResponse resultMapping = new LadderResultResponse(ladderPath.mapStartToEndIndex());
        return PlayerResults.from(participantNames, resultLabels, resultMapping.positionMap());
    }

    private void handleResultQueries(PlayerResults playerResults) {
        while (true) {
            outputView.printResultSelectionPrompt();
            String queryName = inputView.readResultRequest();
            if (printIfMatchedPlayer(playerResults, queryName)) continue;
            if (printIfAllResults(playerResults, queryName)) break;
            outputView.printNameNotFound();
        }
    }

    private boolean printIfMatchedPlayer(PlayerResults playerResults, String playerName) {
        if (!playerResults.hasPlayer(playerName)) return false;
        outputView.printLadderTitle();
        outputView.printSingleResult(playerResults.resultOf(playerName));
        return true;
    }

    private boolean printIfAllResults(PlayerResults playerResults, String queryName) {
        if (!isAllCommand(queryName)) return false;
        outputView.printLadderTitle();
        outputView.printAllResults(playerResults.allResults());
        return true;
    }

    private boolean isAllCommand(String name) {
        return "all".equals(name);
    }
}
