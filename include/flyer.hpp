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

public:

    using ref_t = std::shared_ptr<Flyer>;

    class Registry {
    private:

        std::vector<ref_t> flyers;

        static Registry &get_instance();

        void add_impl(const std::string &firstName, const std::string &lastName, const std::string &passportNumber);
        ref_t get_impl(const std::string &passportNumber);

    public:

        static void add(const std::string &firstName, const std::string &lastName, const std::string &passportNumber);
        static ref_t get(const std::string &passportNumber);

    };

    static ref_t create(const std::string &firstName, const std::string &lastName, const std::string &passportNumber);

    LoyaltyStatus getLoyaltyStatus();
    void upgradeStatus(LoyaltyStatus status);

    void printInfo();

};

#endif // PASSENGER_HPP