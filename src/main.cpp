#include <iostream>
#include <vector>
#include <utility>
#include <limits>
#include <cstdlib>

#include "ui.hpp"
#include "flyer.hpp"

bool is_number(const std::string& s) {
    std::string::const_iterator it = s.begin();
    while (it != s.end() && std::isdigit(*it)) ++it;
    return !s.empty() && it == s.end();
}

void window_root();

void window_flyer();
void window_flight();

void window_register_flyer();
void window_get_flyer_info();
void window_flyer_upgrade();

void window_get_all_flights();
void window_book_flight() {}

std::string input(const std::string &n) {
    std::cout << "Enter " << n << ": " << std::flush;
    std::string s;
    std::cin >> s;
    return s;
}

void simple_option_menu(std::vector<std::tuple<std::string, std::string, UI::Window>> menu) {
    std::cout << std::endl << "Options:" << std::endl;
    
    for (const auto &m : menu) {
        std::cout << "    - " << std::get<0>(m) << ". " << std::get<1>(m) << std::endl;
    }

    while (true) {
        std::string choice = input("choice");

        for (const auto &m : menu) {
            if (choice == std::get<0>(m)) {
                UI::set_window(std::get<2>(m));
                return;
            }
        }

        std::cout << "Not an option" << std::endl;
    }
}

void window_root() {
    simple_option_menu({
        { "q", "Quit", []() { std::exit(0); } },
        { "1", "Flyer", window_flyer },
        { "2", "Flight", window_flight },
    });
}

void window_flyer() {
    simple_option_menu({
        { "q", "Back (root)", window_root },
        { "1", "Register a new flyer", window_register_flyer },
        { "2", "Get flyer info", window_get_flyer_info },
        { "3", "Upgrade flyer (Loyalty program)", window_flyer_upgrade },
    });
}

void window_flight() {
    simple_option_menu({
        { "q", "Back (Flyer)", window_flyer },
        { "1", "Get all flights", window_get_all_flights },
        { "2", "Book a flight", window_book_flight },
    });
}

void window_register_flyer() {
    std::string passportNumber = input("passport number");
    std::string firstName = input("first name");
    std::string lastName = input("last name");

    Flyer::Registry::add(firstName, lastName, passportNumber);

    std::cout << "Flyer registered successfuly" << std::endl;

    UI::set_window(window_root);
}

void window_get_flyer_info() {
    std::string passportNumber = input("passport number");
    
    if (auto flyer = Flyer::Registry::get(passportNumber)) {
        flyer->printInfo();
    } else {
        std::cout << "Error: No flyer with passport number '" << passportNumber << "' exists.\n";
    }

    UI::set_window(window_root);
}

void window_flyer_upgrade() {
    auto passportNumber = input("passport number");
    
    if (auto flyer = Flyer::Registry::get(passportNumber)) {
        LoyaltyStatus current = flyer->getLoyaltyStatus();
        
        if (current == LoyaltyStatus::PLATINUM) {
            std::cout << "Error: Flyer already has maximum status (Platinum)\n";
            UI::set_window(window_root);
            return;
        }

        std::cout << "Current status: " << loyaltyStatusToString(current) << "\n"
                  << "Available upgrades:\n";
        
        int currentLevel = static_cast<int>(current);
        for (int i = currentLevel + 1; i <= static_cast<int>(LoyaltyStatus::PLATINUM); ++i) {
            auto newStatus = static_cast<LoyaltyStatus>(i);
            std::cout << "  " << (i - currentLevel) << ". " << loyaltyStatusToString(newStatus) 
                      << " (" << (loyaltyStatusToPrice(newStatus) - loyaltyStatusToPrice(current)) << " PLN)\n";
        }

        std::cout << "Select upgrade (1-" << (static_cast<int>(LoyaltyStatus::PLATINUM) - currentLevel) << "): " << std::flush;
        
        int choice;
        if (!(std::cin >> choice) || choice < 1 || choice > (static_cast<int>(LoyaltyStatus::PLATINUM) - currentLevel)) {
            std::cout << "Invalid selection\n";
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            UI::set_window(window_root);
            return;
        }

        LoyaltyStatus newStatus = static_cast<LoyaltyStatus>(currentLevel + choice);
        flyer->upgradeStatus(newStatus);
        std::cout << "Upgraded to " << loyaltyStatusToString(newStatus) << "\n";
        
    } else {
        std::cout << "Error: No flyer with passport number '" << passportNumber << "' exists.\n";
    }
    UI::set_window(window_root);
}

void window_get_all_flights() {
    // auto flights = FlightRegistry::get_all();

    // for (const auto &flight : flights) {
    //     std::cout << flight->get_number() << " -> " << flight->get_destination();
    // }
}

int main() {
    // do {
    //     std::cout << "Enter city code: " << std::flush;
    //     std::string city_code;
    //     std::cin >> city_code;

    //     CityRegistry
    // } while();

    UI::start(window_root);

    return 0;
}

