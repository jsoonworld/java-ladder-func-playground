package view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String COLUMN_PROMPT = "사다리의 넓이는 몇 개인가요?";
    private static final String ROW_PROMPT = "사다리의 높이는 몇 개인가요?";

    public int readColumnCount() {
        System.out.println(COLUMN_PROMPT);
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public int readRowCount() {
        System.out.println(ROW_PROMPT);
        return Integer.parseInt(scanner.nextLine().trim());
    }
}
