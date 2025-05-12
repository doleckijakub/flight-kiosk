#ifndef PASSENGER_HPP
#define PASSENGER_HPP

#include <string>
#include <vector>
#include <memory>

#include "loyalty_program.hpp"

class FlyerRegistry;

class Flyer {
private:

    std::string firstName, lastName;
    std::string passportNumber;
    LoyaltyStatus loyaltyStatus;
    double kmFlown;

    Flyer(const std::string &firstName, const std::string &lastName, const std::string &passportNumber);

    static std::shared_ptr<Flyer> create(const std::string& firstName, const std::string& lastName, const std::string& passportNumber);
    
    friend class FlyerRegistry;

public:

    LoyaltyStatus getLoyaltyStatus();
    void upgradeStatus(LoyaltyStatus status);

    void printInfo();

};

class FlyerRegistry {
private:

    std::vector<std::shared_ptr<Flyer>> flyers;

    static FlyerRegistry &get_instance();

    void add_impl(const std::string &firstName, const std::string &lastName, const std::string &passportNumber);
    std::shared_ptr<Flyer> get_impl(const std::string &passportNumber);

public:

    static void add(const std::string &firstName, const std::string &lastName, const std::string &passportNumber);
    static std::shared_ptr<Flyer> get(const std::string &passportNumber);

};

#endif // PASSENGER_HPP