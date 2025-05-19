package model;

public enum LoyaltyStatus {
    NONE, BRONZE, SILVER, GOLD, PLATINUM;

    @Override
    public String toString() {
        switch (this) {
            case NONE: return "Standard";
            case BRONZE: return "Bronze";
            case SILVER: return "Silver";
            case GOLD: return "Gold";
            case PLATINUM: return "Platinum";
        }

        return null;
    }

    public int getPrice() {
        switch (this) {
            case NONE: return 0;
            case BRONZE: return 100;
            case SILVER: return 300;
            case GOLD: return 600;
            case PLATINUM: return 1000;
        };
        
        return -1;
    }

    public double getDiscount() {
        switch (this) {
            case NONE: return 0.0;
            case BRONZE: return 0.1;
            case SILVER: return 0.2;
            case GOLD: return 0.6;
            case PLATINUM: return 0.8;
        };
        
        return 0;
    }
}