package ui;

class MenuOption {
    String key;
    String description;
    Runnable action;

    MenuOption(String key, String description, Runnable action) {
        this.key = key;
        this.description = description;
        this.action = action;
    }
}