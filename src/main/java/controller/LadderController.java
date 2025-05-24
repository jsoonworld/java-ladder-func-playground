package controller;

import domain.LadderBoard;
import domain.LadderPath;
import domain.PlayerResults;
import dto.LadderBuildResponse;
import dto.LadderResultResponse;
import util.NumericParser;
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
        List<String> participantNames = promptParticipants();
        List<String> resultLabels = promptResultLabels();
        int ladderHeight = promptAndParseLadderHeight();

        LadderBuildResponse ladder = buildLadder(participantNames.size(), ladderHeight);
        displayLadderBoard(participantNames, resultLabels, ladder);

        PlayerResults playerResults = mapResults(participantNames, resultLabels, ladder);
        handleResultRequestLoop(playerResults);
    }

    private List<String> promptParticipants() {
        outputView.printParticipantPrompt();
        return inputView.readParticipantNames();
    }

    private List<String> promptResultLabels() {
        outputView.printResultPrompt();
        return inputView.readResultLabels();
    }

    private int promptAndParseLadderHeight() {
        outputView.printHeightPrompt();
        String input = inputView.readResultRequest();
        return NumericParser.parse(input);
    }

    private LadderBuildResponse buildLadder(int columnCount, int rowCount) {
        LadderBoard ladderBoard = LadderBoard.build(columnCount, rowCount);
        return LadderBuildResponse.from(ladderBoard);
    }

    private void displayLadderBoard(List<String> participants, List<String> results, LadderBuildResponse ladder) {
        outputView.printLadderTitle();
        outputView.printParticipantNames(participants);
        outputView.printBridgeLines(ladder);
        outputView.printResultLabels(results);
    }

    private PlayerResults mapResults(List<String> participants, List<String> results, LadderBuildResponse ladder) {
        LadderPath ladderPath = new LadderPath(ladder.lines(), ladder.columnCount());
        LadderResultResponse resultMapping = new LadderResultResponse(ladderPath.mapStartToEndIndex());
        return PlayerResults.from(participants, results, resultMapping.positionMap());
    }

    private void handleResultRequestLoop(PlayerResults playerResults) {
        while (true) {
            outputView.printResultSelectionPrompt();
            String name = inputView.readResultRequest();

            if (handleSingleResult(playerResults, name)) continue;
            if (handleAllResults(playerResults, name)) return;

            outputView.printNameNotFound();
        }
    }

    private boolean handleSingleResult(PlayerResults playerResults, String name) {
        if (!playerResults.hasPlayer(name)) return false;
        outputView.printSingleResultWithTitle(playerResults.resultOf(name));
        return true;
    }

    private boolean handleAllResults(PlayerResults playerResults, String name) {
        if (!isAllCommandAndNotPlayer(name, playerResults)) return false;
        outputView.printLadderTitle();
        outputView.printAllResults(playerResults.allResults());
        return true;
    }

    private boolean isAllCommandAndNotPlayer(String name, PlayerResults playerResults) {
        return "all".equals(name) && !playerResults.hasPlayer(name);
    }
}
