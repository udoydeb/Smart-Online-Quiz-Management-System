import model.User;
import model.Teacher;
import service.UserManager;
import service.Quiz;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();

        while (true) {
            System.out.println("🧠 Smart Online Quiz System");
            System.out.println("1. Student Login/Register");
            System.out.println("2. Faculty Login/Register");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (mainChoice == 1) {
                handleStudentFlow(scanner, userManager);
            } else if (mainChoice == 2) {
                handleTeacherFlow(scanner, userManager);
            } else if (mainChoice == 3) {
                System.out.println("👋 Goodbye!");
                break;
            } else {
                System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private static void handleStudentFlow(Scanner scanner, UserManager userManager) {
        User currentUser = null;

        while (currentUser == null) {
            System.out.println("\n📚 Student Portal");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 3) {
                return; // Go back to main menu
            }

            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (choice == 1) {
                if (userManager.register(username, password)) {
                    System.out.println("✅ Registration successful.");
                } else {
                    System.out.println("⚠ Username already exists.");
                }
            } else if (choice == 2) {
                currentUser = userManager.login(username, password);
                if (currentUser == null) {
                    System.out.println("❌ Login failed.");
                } else {
                    System.out.println("✅ Welcome, " + currentUser.getUsername() + "!");
                }
            } else {
                System.out.println("❌ Invalid option.");
            }
        }

        // Show student dashboard after successful login
        showStudentDashboard(scanner, userManager, currentUser);
    }

    private static void showStudentDashboard(Scanner scanner, UserManager userManager, User student) {
        while (true) {
            System.out.println("\n📊 Student Dashboard - Welcome " + student.getUsername());
            System.out.println("1. Take Quiz");
            System.out.println("2. View Leaderboard");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    takeQuiz(scanner, userManager, student);
                    break;
                case 2:
                    userManager.showLeaderboard();
                    break;
                case 3:
                    System.out.println("👋 " + student.getUsername() + " logged out.");
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private static void takeQuiz(Scanner scanner, UserManager userManager, User student) {
        Quiz quiz = new Quiz();
        int score = quiz.start(student);
        userManager.updateUserScore(student, score);
        System.out.println("\n🎉 Quiz completed! Showing updated leaderboard:");
        userManager.showLeaderboard();
    }

    private static void handleTeacherFlow(Scanner scanner, UserManager userManager) {
        Teacher currentTeacher = null;

        while (currentTeacher == null) {
            System.out.println("\n👨‍🏫 Faculty Portal");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 3) {
                return; // Go back to main menu
            }

            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (choice == 1) {
                if (userManager.registerTeacher(username, password)) {
                    System.out.println("✅ Faculty registration successful.");
                } else {
                    System.out.println("⚠ Faculty username already exists.");
                }
            } else if (choice == 2) {
                currentTeacher = userManager.teacherLogin(username, password);
                if (currentTeacher == null) {
                    System.out.println("❌ Faculty login failed.");
                } else {
                    System.out.println("✅ Welcome, Faculty " + currentTeacher.getUsername() + "!");
                }
            } else {
                System.out.println("❌ Invalid option.");
            }
        }

        // Teacher dashboard
        showTeacherDashboard(scanner, userManager, currentTeacher);
    }

    private static void showTeacherDashboard(Scanner scanner, UserManager userManager, Teacher teacher) {
        while (true) {
            System.out.println("\n📊 Faculty Dashboard - Welcome " + teacher.getUsername());
            System.out.println("1. View Detailed Leaderboard");
            System.out.println("2. View Students List");
            System.out.println("3. View Quick Leaderboard");
            System.out.println("4. Manage Quiz Questions");
            System.out.println("5. Manage Students");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    userManager.showDetailedLeaderboard();
                    break;
                case 2:
                    userManager.showStudentsList();
                    break;
                case 3:
                    userManager.showLeaderboard();
                    break;
                case 4:
                    manageQuestions(scanner, userManager);
                    break;
                case 5:
                    manageStudents(scanner, userManager);
                    break;
                case 6:
                    System.out.println("👋 Faculty " + teacher.getUsername() + " logged out.");
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private static void manageQuestions(Scanner scanner, UserManager userManager) {
        while (true) {
            System.out.println("\n📝 Question Management");
            System.out.println("1. View All Questions");
            System.out.println("2. Add New Question");
            System.out.println("3. Delete Question");
            System.out.println("4. Back to Dashboard");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    userManager.showAllQuestions();
                    break;
                case 2:
                    addNewQuestion(scanner, userManager);
                    break;
                case 3:
                    deleteQuestion(scanner, userManager);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private static void addNewQuestion(Scanner scanner, UserManager userManager) {
        System.out.println("\n➕ Add New Question");
        System.out.print("Enter question text: ");
        String questionText = scanner.nextLine();

        System.out.print("Option 1: ");
        String option1 = scanner.nextLine();
        System.out.print("Option 2: ");
        String option2 = scanner.nextLine();
        System.out.print("Option 3: ");
        String option3 = scanner.nextLine();
        System.out.print("Option 4: ");
        String option4 = scanner.nextLine();

        System.out.print("Correct option (1-4): ");
        int correctOption = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (correctOption >= 1 && correctOption <= 4) {
            if (userManager.addQuestion(questionText, option1, option2, option3, option4, correctOption)) {
                System.out.println("✅ Question added successfully!");
            } else {
                System.out.println("❌ Failed to add question.");
            }
        } else {
            System.out.println("❌ Invalid correct option. Must be 1-4.");
        }
    }

    private static void deleteQuestion(Scanner scanner, UserManager userManager) {
        System.out.println("\n🗑️ Delete Question");
        userManager.showAllQuestions();
        System.out.print("Enter question ID to delete (or 0 to cancel): ");
        int questionId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (questionId > 0) {
            if (userManager.deleteQuestion(questionId)) {
                System.out.println("✅ Question deleted successfully!");
            } else {
                System.out.println("❌ Failed to delete question or question not found.");
            }
        } else {
            System.out.println("❌ Delete operation cancelled.");
        }
    }

    private static void manageStudents(Scanner scanner, UserManager userManager) {
        while (true) {
            System.out.println("\n👥 Student Management");
            System.out.println("1. View All Students");
            System.out.println("2. Unregister Student");
            System.out.println("3. Back to Dashboard");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    userManager.showStudentsList();
                    break;
                case 2:
                    unregisterStudent(scanner, userManager);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private static void unregisterStudent(Scanner scanner, UserManager userManager) {
        System.out.println("\n🗑️ Unregister Student");
        userManager.showStudentsList();
        System.out.print("Enter student username to unregister (or type 'cancel' to cancel): ");
        String username = scanner.nextLine().trim();

        if (username.equalsIgnoreCase("cancel") || username.isEmpty()) {
            System.out.println("❌ Unregister operation cancelled.");
            return;
        }

        System.out.print("⚠️ Are you sure you want to unregister student '" + username + "'? (yes/no): ");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            if (userManager.deleteStudent(username)) {
                System.out.println("✅ Student '" + username + "' has been successfully unregistered!");
            } else {
                System.out.println("❌ Failed to unregister student. Student not found or database error.");
            }
        } else {
            System.out.println("❌ Unregister operation cancelled.");
        }
    }
}
