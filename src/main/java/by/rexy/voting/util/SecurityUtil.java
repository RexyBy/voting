package by.rexy.voting.util;

public class SecurityUtil {
    private static int authUserId = 100004;

    public static int getAuthUserId() {
        return authUserId;
    }

    public static void setAuthUserId(int id) {
        authUserId = id;
    }
}