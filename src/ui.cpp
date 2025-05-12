#include "ui.hpp"

void UI::start(Window window) {
    set_window(window);
    get_instance().run();
}

void UI::set_window(Window window) {
    get_instance().current_window = window;
}

void UI::stop() {
    get_instance().running = false;
}

UI::UI() : running(true) {}
UI::~UI() {}

UI &UI::get_instance() {
    static UI instance;
    return instance;
}

void UI::run() {
    while (running) {
        current_window();
    }
}

