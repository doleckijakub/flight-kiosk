#include "flyer.hpp"

#include <iostream>

Flyer::Flyer(const std::string &firstName, const std::string &lastName, const std::string &passportNumber)
  : firstName(firstName),
    lastName(lastName),
    passportNumber(passportNumber),
    loyaltyStatus(LoyaltyStatus::NONE),
    kmFlown(0.0) {}

std::shared_ptr<Flyer> Flyer::create(const std::string& firstName, const std::string& lastName, const std::string& passportNumber) {
    return std::shared_ptr<Flyer>(new Flyer(firstName, lastName, passportNumber));
}

LoyaltyStatus Flyer::getLoyaltyStatus() {
    return loyaltyStatus;
}

void Flyer::upgradeStatus(LoyaltyStatus status) {
    loyaltyStatus = status;
}

void Flyer::printInfo() {
    std::cout << "Passport number: " << passportNumber << std::endl;
    std::cout << "First name: " << firstName << std::endl;
    std::cout << "Last name: " << lastName << std::endl;
    std::cout << "Loyalty status: " << loyaltyStatusToString(loyaltyStatus) << std::endl;
    std::cout << "Kilometers flown: " << kmFlown << std::endl;
}

/// registry

Flyer::Registry &Flyer::Registry::get_instance() {
    static Flyer::Registry registry;
    return registry;
}

void Flyer::Registry::add_impl(const std::string &firstName, const std::string &lastName, const std::string &passportNumber) {
    flyers.push_back(Flyer::create(firstName, lastName, passportNumber));
}

void Flyer::Registry::add(const std::string &firstName, const std::string &lastName, const std::string &passportNumber) {
    get_instance().add_impl(firstName, lastName, passportNumber);
}

Flyer::ref_t Flyer::Registry::get_impl(const std::string &passportNumber) {
    for (auto &flyer : flyers) {
        if (flyer->passportNumber == passportNumber) {
            return flyer;
        }
    }

    return nullptr;
}

Flyer::ref_t Flyer::Registry::get(const std::string &passportNumber) {
    return get_instance().get_impl(passportNumber);
}