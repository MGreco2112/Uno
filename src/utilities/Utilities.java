package utilities;

import java.util.Scanner;

public class Utilities {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String prompt, boolean isRequired) {
        String output = "";

        do {
            System.out.println(prompt);

            output = scanner.nextLine();
        } while (output.equals("") && isRequired);

        return output;
    }

    public static int getInt(String prompt, int min, int max) {
        int output = min - 1;

        while (output < min || output > max) {
            System.out.println(prompt);

            String input = scanner.nextLine();

            try {
               output = Integer.parseInt(input);
            } catch (Exception e) {
                output = min - 1;
            }


            if (output < min || output > max) {
                System.out.println("Invalid entry, try again");
            }
        }


        return output;
    }

}
