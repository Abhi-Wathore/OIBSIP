import java.util.Scanner;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String username;
    private String password;
    private String profileInfo;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profileInfo = "Default Profile Info";
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfile(String profileInfo) {
        this.profileInfo = profileInfo;
    }

    public String getProfileInfo() {
        return this.profileInfo;
    }
}

public class LoginSystem {
    private static HashMap<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static Timer timer = new Timer();
    private static boolean timeUp = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Adding sample users
        users.put("user1", new User("user1", "pass1"));
        users.put("user2", new User("user2", "pass2"));
        
        System.out.println("Welcome to the Login System!");

        // Login process
        while (currentUser == null) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            currentUser = login(username, password);
            if (currentUser == null) {
                System.out.println("Invalid login. Try again.");
            }
        }

        System.out.println("Login successful! Welcome, " + currentUser.getUsername());

        // Menu for profile update and MCQ section
        boolean running = true;
        while (running) {
            System.out.println("\n1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Start MCQ Quiz");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter new profile info: ");
                    String profileInfo = scanner.nextLine();
                    currentUser.updateProfile(profileInfo);
                    System.out.println("Profile updated successfully.");
                    break;
                case 2:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    currentUser.updatePassword(newPassword);
                    System.out.println("Password updated successfully.");
                    break;
                case 3:
                    startQuizWithTimer(scanner);
                    break;
                case 4:
                    logout();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    // Login function
    public static User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            return user;
        }
        return null;
    }

    // Logout function
    public static void logout() {
        System.out.println("Logging out...");
        currentUser = null;
    }

    // Start the quiz with a timer
    public static void startQuizWithTimer(Scanner scanner) {
        String[] questions = {
            "What is the capital of France?\n1. Berlin\n2. Madrid\n3. Paris\n4. Rome",
            "What is 2*2+2?\n1. 3\n2. 4\n3. 5\n4. 6"
        };
        int[] correctAnswers = {3, 4};
        int score = 0;

        startTimer(60);  // Start a 60-second timer

        for (int i = 0; i < questions.length && !isTimeUp(); i++) {
            System.out.println(questions[i]);
            System.out.print("Enter your answer (1-4): ");
            int answer = scanner.nextInt();
            if (answer == correctAnswers[i]) {
                score++;
            }
        }

        System.out.println("\nQuiz completed! Your score is: " + score);
        timeUp = false; // Reset time-up flag for the next quiz
    }

    // Timer functionality to auto-submit after a certain time
    public static void startTimer(int seconds) {
        timeUp = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up! Auto-submitting answers...");
                timeUp = true;
            }
        }, seconds * 1000);
    }

    public static boolean isTimeUp() {
        return timeUp;
    }
}
