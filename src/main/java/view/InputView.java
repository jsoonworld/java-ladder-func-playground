package view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public String readParticipantNames() {
        return readLine();
    }

    public String readResultLabels() {
        return readLine();
    }

    public String readLadderHeight() {
        return readLine();
    }

    public String readResultRequest() {
        return readLine();
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }
}
