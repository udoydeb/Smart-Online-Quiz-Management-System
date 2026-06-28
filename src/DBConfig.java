package model;

public class DBConfig {
    public static final String URL;
    public static final String USER;
    public static final String PASS;

    static {
        String url = System.getenv("DB_URL");
        if (url == null || url.isEmpty()) {
            url = "jdbc:mysql://localhost:3306/quizdb";
        }
        String user = System.getenv("DB_USER");
        if (user == null || user.isEmpty()) {
            user = "root";
        }
        String pass = System.getenv("DB_PASS");
        if (pass == null) {
            pass = ""; // default empty; do NOT commit secrets into source
        }

        URL = url;
        USER = user;
        PASS = pass;
    }
}
