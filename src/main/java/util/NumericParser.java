package util;

public class NumericParser {

    private static final String ERROR_INVALID_NUMBER = "[ERROR] 숫자를 입력해 주세요.";

    public static int parse(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_INVALID_NUMBER);
        }
    }
}
