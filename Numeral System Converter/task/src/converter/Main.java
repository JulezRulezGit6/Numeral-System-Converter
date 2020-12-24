package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean hasError = false;

        String inputSourceRadix = "";
        if (scanner.hasNext()) {
            inputSourceRadix = scanner.next();
        } else {
            hasError = true;
        }

        String sourceNumber = "";
        if (scanner.hasNext()) {
            sourceNumber = scanner.next();
        } else {
            hasError = true;
        }

        String inputTargetRadix = "";
        if (scanner.hasNext()) {
            inputTargetRadix = scanner.next();
        } else {
            hasError = true;
        }
        if (hasError) {
            System.out.println("ERROR");
            return;
        }

        if (!inputSourceRadix.matches("[0-9]{1,2}") || Integer.parseInt(inputSourceRadix) <= 0 || Integer.parseInt(inputSourceRadix) > 36) {
            hasError = true;
        }
        if (!sourceNumber.matches("[0-9A-Za-z]+(.[0-9A-Za-z]+)?")) {
            hasError = true;
        }
        if (!inputTargetRadix.matches("[0-9]{1,2}") || Integer.parseInt(inputTargetRadix) <= 0 || Integer.parseInt(inputTargetRadix) > 36) {
            hasError = true;
        }
        if (hasError) {
            System.out.println("ERROR");
            return;
        }

        int sourceRadix = Integer.parseInt(inputSourceRadix);

        int targetRadix = Integer.parseInt(inputTargetRadix);

        boolean isWholeNumber = false;
        int decimalIntegerRepresentation;

        String integerPart = "";
        String fractionalPart = "";

        if (sourceNumber.contains(".")) {
            String[] split = sourceNumber.split("\\.");
            integerPart = split[0];
            fractionalPart = split[1];
        } else {
            integerPart = sourceNumber;
            isWholeNumber = true;
        }

        if (sourceRadix == 1) {
            decimalIntegerRepresentation = integerPart.length();
        } else {
            decimalIntegerRepresentation = Integer.parseInt(integerPart, sourceRadix);
        }

        double fractionalPartSum = 0;
        for (int i = 0; i < fractionalPart.length(); i++) {
            char currentChar = fractionalPart.charAt(i);
            if (currentChar >= 'a' && currentChar <= 'z') {
                int numericValue = Character.getNumericValue(currentChar);
                fractionalPartSum += numericValue / Math.pow(sourceRadix, i + 1);
            } else {

                fractionalPartSum += Double.parseDouble(String.valueOf(currentChar)) / Math.pow(sourceRadix, i + 1);
            }
        }
        StringBuilder targetIntegerRepresentation = new StringBuilder();

        if (targetRadix == 1) {
            targetIntegerRepresentation.append("1".repeat(Math.max(0, decimalIntegerRepresentation)));
        } else {

            targetIntegerRepresentation.append(Integer.toString(decimalIntegerRepresentation, targetRadix));
        }

        StringBuilder targetFractionalRepresentation = new StringBuilder();

        if (targetRadix == 1) {
            targetFractionalRepresentation.append("1".repeat(Math.max(0, decimalIntegerRepresentation)));
        } else {

            for (int i = 0; i < 5; i++) {

                fractionalPartSum = fractionalPartSum * targetRadix;

                int numberToAppend = (int) fractionalPartSum;

                if (numberToAppend > 9) {
                    char letter = (char) (numberToAppend + 87);
                    targetFractionalRepresentation.append(letter);
                } else {
                    targetFractionalRepresentation.append(numberToAppend);
                }
                fractionalPartSum -= numberToAppend;

            }

        }

        if (isWholeNumber) {
            System.out.println(targetIntegerRepresentation);
        } else {
            System.out.println(targetIntegerRepresentation + "." + targetFractionalRepresentation);
        }
    }

}
