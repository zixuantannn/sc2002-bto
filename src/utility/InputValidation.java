package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * The {@code InputValidation} class provides generic and specialized methods
 * for validating and parsing user input from the console.
 */
public class InputValidation {

    private static Scanner sc = new Scanner(System.in);

    /**
     * A generic method for parsing and validating user input.
     *
     * @param <T>          the type of input.
     * @param prompt       the prompt shown to the user.
     * @param parser       a parser that converts string input to the desired type.
     * @param validator    a predicate to validate the parsed input.
     * @param errorMessage the error message displayed when input is invalid.
     * @return a validated value of type T.
     */
    public static <T> T getValidatedInput(String prompt, InputParser<T> parser, Predicate<T> validator,
            String errorMessage) {
        T value = null;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine();
                value = parser.parse(input);
                if (validator.test(value)) {
                    valid = true;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
        return value;
    }

    /**
     * A functional interface for parsing strings into type T.
     *
     * @param <T> the type of the parsed result.
     */
    @FunctionalInterface
    public interface InputParser<T> {
        T parse(String input) throws Exception;
    }

    /**
     * Validates integer input.
     *
     * @param prompt       the prompt shown to the user.
     * @param validator    the condition that input must satisfy.
     * @param errorMessage the message shown when validation fails.
     * @return a valid integer.
     */
    public static Integer getInt(String prompt, Predicate<Integer> validator, String errorMessage) {
        return getValidatedInput(prompt, Integer::parseInt, validator, errorMessage);
    }

    /**
     * Validates string input.
     *
     * @param prompt       the prompt shown to the user.
     * @param validator    the condition that input must satisfy.
     * @param errorMessage the message shown when validation fails.
     * @return a valid string.
     */
    public static String getString(String prompt, Predicate<String> validator, String errorMessage) {
        return getValidatedInput(prompt, input -> input, validator, errorMessage);
    }

    /**
     * Validates "yes" or "no" responses (case insensitive).
     *
     * @param prompt       the prompt shown to the user.
     * @param errorMessage the message shown when validation fails.
     * @return either "yes" or "no".
     */
    public static String getYesOrNo(String prompt, String errorMessage) {
        Predicate<String> yesOrNoValidator = input -> input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no");
        return getValidatedInput(prompt, input -> input, yesOrNoValidator, errorMessage);
    }

    /**
     * Validates "approve" or "reject" responses (case insensitive).
     *
     * @param prompt       the prompt shown to the user.
     * @param errorMessage the message shown when validation fails.
     * @return either "approve" or "reject".
     */
    public static String getApproveOrReject(String prompt, String errorMessage) {
        Predicate<String> approveOrRejectValidator = input -> input.equalsIgnoreCase("approve")
                || input.equalsIgnoreCase("reject");
        return getValidatedInput(prompt, input -> input, approveOrRejectValidator, errorMessage);
    }

    /**
     * Validates "on" or "off" responses (case insensitive).
     *
     * @param prompt       the prompt shown to the user.
     * @param errorMessage the message shown when validation fails.
     * @return either "on" or "off".
     */
    public static String getOnOrOff(String prompt, String errorMessage) {
        Predicate<String> onOrOffValidator = input -> input.equalsIgnoreCase("on") || input.equalsIgnoreCase("off");
        return getValidatedInput(prompt, input -> input, onOrOffValidator, errorMessage);
    }

    /**
     * Validates date input in the specified format.
     *
     * @param prompt       the prompt shown to the user.
     * @param format       the expected date format (e.g. "yyyy-MM-dd").
     * @param errorMessage the message shown when validation fails.
     * @return a valid {@code Date} object.
     */
    public static Date getDate(String prompt, String format, String errorMessage) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // Ensures the date is strictly parsed (no invalid dates like 2000-00-00)

        return getValidatedInput(prompt, input -> {
            try {
                // Try to parse the date
                return sdf.parse(input);
            } catch (ParseException e) {
                throw new ParseException("Invalid date format or date does not exist", 0);
            }
        }, date -> date != null, errorMessage);
    }

    /**
     * Validates password strength. A strong password must contain letters, digits,
     * and be at least 8 characters long.
     *
     * @param prompt       the prompt shown to the user.
     * @param errorMessage the message shown when validation fails.
     * @return a strong password string.
     */
    public static String getStrongPassword(String prompt, String errorMessage) {
        return getValidatedInput(
                prompt,
                input -> input,
                password -> password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*") && password.length() >= 8,
                errorMessage);
    }

}
