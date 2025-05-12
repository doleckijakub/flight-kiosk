#ifndef LOYALTY_PROGRAM_HPP
#define LOYALTY_PROGRAM_HPP

enum class LoyaltyStatus { NONE, BRONZE, SILVER, GOLD, PLATINUM };

inline static std::string loyaltyStatusToString(LoyaltyStatus loyaltyStatus) {
    switch (loyaltyStatus) {
        case LoyaltyStatus::NONE: return "Standard";
        case LoyaltyStatus::BRONZE: return "Bronze";
        case LoyaltyStatus::SILVER: return "Silver";
        case LoyaltyStatus::GOLD: return "Gold";
        case LoyaltyStatus::PLATINUM: return "Platinum";
    }

    throw 0;
}

inline static int loyaltyStatusToPrice(LoyaltyStatus loyaltyStatus) {
    switch (loyaltyStatus) {
        case LoyaltyStatus::NONE: return 0;
        case LoyaltyStatus::BRONZE: return 500;
        case LoyaltyStatus::SILVER: return 1200;
        case LoyaltyStatus::GOLD: return 2500;
        case LoyaltyStatus::PLATINUM: return 5000;
    }

    throw 0;
}

class LoyaltyProgram {
public:
    static LoyaltyStatus calculateStatus(double miles);
    static double calculateDiscount(LoyaltyStatus status);
    static std::string statusToString(LoyaltyStatus status);
};

#endif // LOYALTY_PROGRAM_HPP