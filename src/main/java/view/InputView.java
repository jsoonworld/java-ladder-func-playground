package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public List<String> readParticipantNames() {
        return Arrays.asList(readLine().split(","));
    }

    public List<String> readResultLabels() {
        return Arrays.asList(readLine().split(","));
    }

    public int readLadderHeight() {
        return Integer.parseInt(readLine());
    }

    public String readResultRequest() {
        return readLine();
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }
}
