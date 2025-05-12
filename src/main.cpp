#include <iostream>
#include <vector>
#include <utility>
#include <limits>

#include "ui.hpp"
#include "flyer.hpp"

void simple_option_menu(std::vector<std::pair<std::string, UI::Window>> menu) {
    std::cout << std::endl << "Options:" << std::endl;
    
    int i = 1;
    for (const auto &m : menu) {
        std::cout << "    - " << i++ << ". " << m.first << std::endl;
    }
    
    std::cout << " > " << std::flush;

    int option = 0;
    std::cin >> option;

    if (1 <= option && option <= menu.size()) {
        UI::set_window(menu.at(option - 1).second);
    }
}

void window_root();

void window_flyer();
void window_flight();

void window_register_flyer();
void window_get_flyer_info();
void window_flyer_upgrade();

void window_get_all_flights() {}
void window_book_flight() {}

void window_root() {
    simple_option_menu({
        { "Flyer", window_flyer },
        { "Flight", window_flight },
    });
}

void window_flyer() {
    simple_option_menu({
        { "Register a new flyer", window_register_flyer },
        { "Get flyer info", window_get_flyer_info },
        { "Upgrade flyer (Loyalty program)", window_flyer_upgrade },
    });
}

void window_flight() {
    simple_option_menu({
        { "Get all flights", window_get_all_flights },
        { "Book a flight", window_book_flight },
    });
}

std::string input_passport_number() {
    std::cout << "Enter passport number: " << std::flush;
    std::string passportNumber;
    std::cin >> passportNumber;
    return passportNumber;
}

void window_register_flyer() {
    std::string passportNumber = input_passport_number();

    std::cout << "Enter first name: " << std::flush;
    std::string firstName;
    std::cin >> firstName;

    std::cout << "Enter last name: " << std::flush;
    std::string lastName;
    std::cin >> lastName;

    FlyerRegistry::add(firstName, lastName, passportNumber);

    std::cout << "Flyer registered successfuly" << std::endl;

    UI::set_window(window_root);
}

void window_get_flyer_info() {
    auto passportNumber = input_passport_number();
    
    if (auto flyer = FlyerRegistry::get(passportNumber)) {
        flyer->printInfo();
    } else {
        std::cout << "Error: No flyer with passport number '" << passportNumber << "' exists.\n";
    }
    UI::set_window(window_root);
}

void window_flyer_upgrade() {
    auto passportNumber = input_passport_number();
    
    if (auto flyer = FlyerRegistry::get(passportNumber)) {
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

int main() {
    UI::start(window_root);

    return 0;
}

