package AdminPortal.RegressionTestCases;

import java.util.Random;

public class randomGenerator {

	private static final String[] FIRST_NAMES = {"John", "Jane", "Bob", "Alice", "Mike", "Sarah", "Emily", "David"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson"};
    private static final String[] EMAIL_DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com"};
    
    private static final String[] VEHICLE_NUMBER = {"2100", "2105", "3105", "4050", "5000", "0100", "0150", "0200", "0180"};
    private static final String[] VEHICLE_LETTERS = {"zxc", "asd", "qwe", "sdf", "xcv", "wer", "dfg", "cvb", "ert"};

    public static class Visitor {
        public String firstName;
        public String lastName;
        public String email;
        public String numbers;
        public String vehiclenumber;
        public String letters;

        public Visitor(String firstName, String lastName, String email, String numbers, String vehiclenumber, String letters) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.numbers = numbers;
            this.letters = letters;
            this.vehiclenumber = vehiclenumber;
        }
    }

    public static Visitor generateRandomContact() {
        Random random = new Random();

        // Generate random first name
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];

        // Generate random last name
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

        // Generate random email domain
        String emailDomain = EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)];

        // Generate random email username (with numbers)
        String emailUsername = firstName.toLowerCase() + lastName.toLowerCase() + random.nextInt(100);

        // Generate random email
        String email = emailUsername + "@" + emailDomain;
        
        //Generate car plate letters
        String letters = VEHICLE_LETTERS[random.nextInt(VEHICLE_LETTERS.length)];

        // Generate random phone number and car plate number
        int randomInt = random.nextInt(900) + 100;
        
        String numbers = String.valueOf(randomInt);
        
        String vehiclenumber = VEHICLE_NUMBER[random.nextInt(VEHICLE_NUMBER.length)];
        
        String phoneNumberSuffix = String.valueOf(random.nextInt(100));
        
        String phoneNumber = numbers + "-" + phoneNumberSuffix;

        // Return the generated contact information
        return new Visitor(firstName, lastName, email, numbers, vehiclenumber, letters);
    }
}
