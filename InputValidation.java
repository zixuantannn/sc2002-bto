import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputValidation {

    private static Scanner sc = new Scanner(System.in);

    // Generic input validator
    public static <T> T getValidatedInput(String prompt, InputParser<T> parser, Predicate<T> validator, String errorMessage) {
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

    // Functional interface for parsing strings to type T
    @FunctionalInterface
    public interface InputParser<T> {
        T parse(String input) throws Exception;
    }

    // Convenience methods for common types
    public static Integer getInt(String prompt, Predicate<Integer> validator, String errorMessage) {
        return getValidatedInput(prompt, Integer::parseInt, validator, errorMessage);
    }

    public static String getString(String prompt, Predicate<String> validator, String errorMessage) {
        return getValidatedInput(prompt, input -> input, validator, errorMessage);
    }
    public static String getYesOrNo(String prompt, String errorMessage) {
        Predicate<String> yesOrNoValidator = input -> input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no");
        return getValidatedInput(prompt, input -> input, yesOrNoValidator, errorMessage);
    }
    public static String getApproveOrReject(String prompt, String errorMessage) {
        Predicate<String> approveOrRejectValidator = input -> input.equalsIgnoreCase("approve") || input.equalsIgnoreCase("reject");
        return getValidatedInput(prompt, input -> input, approveOrRejectValidator, errorMessage);
    }
    

    public static String getOnOrOff(String prompt, String errorMessage) {
        Predicate<String> onOrOffValidator = input -> input.equalsIgnoreCase("on") || input.equalsIgnoreCase("off");
        return getValidatedInput(prompt, input -> input, onOrOffValidator, errorMessage);
    }
    
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

    public static String getStrongPassword(String prompt, String errorMessage) {
        return getValidatedInput(
            prompt, 
            input -> input, 
            password -> password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*") && password.length() >= 8, 
            errorMessage
        );
    }
    
}
