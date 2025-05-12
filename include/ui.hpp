#ifndef UI_HPP
#define UI_HPP

#include <functional>

class UI {
public:

    using Window = std::function<void()>;

    static void start(Window);
    static void set_window(Window);
    static void stop();

private:

    UI();
    ~UI();

    static UI &get_instance();

    void run();

    bool running;
    Window current_window;

};

#endif // UI_HPP