package kms.app;

public class WvsApp {
    public static void main(String[] args) {
        try {
            new WvsLogin().launchServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
