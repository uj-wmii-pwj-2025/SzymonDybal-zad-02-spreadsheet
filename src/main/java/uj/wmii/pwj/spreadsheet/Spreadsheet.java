package uj.wmii.pwj.spreadsheet;

public class Spreadsheet {

    public String[][] calculate(String[][] input) {
        String[][] output = new String[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                output[i][j] = evaluateOperation(input[i][j], input);
            }
        }
        return output;
    }

    private String evaluateOperation(String operation, String[][] input) {
        operation = operation.trim();

        if (operation.startsWith("$")) {
            return reference(operation, input);
        }
        if (operation.startsWith("=")) {
            return expression(operation, input);
        }
        return operation;
    }

    private String reference(String operation, String[][] input) {

        operation = operation.substring(1);
        char colChar = operation.charAt(0);
        int col = colChar - 'A';
        int row = Integer.parseInt(operation.substring(1)) - 1;
        return evaluateOperation(input[row][col], input);
    }

    private String expression(String operation, String[][] input) {
        String op = operation.substring(1, operation.indexOf('('));
        String[] params = operation.substring(operation.indexOf('(') + 1, operation.lastIndexOf(')')).split(",");

        params[0] = evaluateOperation(params[0].trim(), input);
        params[1] = evaluateOperation(params[1].trim(), input);

        int a = Integer.parseInt(params[0]);
        int b = Integer.parseInt(params[1]);

        return switch (op) {
            case "ADD" -> String.valueOf(a + b);
            case "SUB" -> String.valueOf(a - b);
            case "MUL" -> String.valueOf(a * b);
            case "DIV" -> String.valueOf(a / b);
            case "MOD" -> String.valueOf(a % b);
            default -> "0";
        };
    }
}
