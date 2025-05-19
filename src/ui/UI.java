package ui;

import util.ANSI;

public class UI {
    private static UI INSTANCE = new UI();
    private boolean running;
    private Runnable currentWindow;

    private UI() {
        this.running = true;
    }

    public static void start(Runnable window) {
        setWindow(window);
        INSTANCE.run();
    }

    public static void setWindow(Runnable window) {
        INSTANCE.currentWindow = window;
    }

    public static void stop() {
        INSTANCE.running = false;
    }

    private void run() {
        while (running) {
            if (currentWindow != null) {
                currentWindow.run();
            }

            System.out.println(ANSI.BLUE + "\n############################\n" + ANSI.RESET);
        }
    }
}