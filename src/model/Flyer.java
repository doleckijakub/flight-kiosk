package model;

public class Flyer {
    private final String firstName;
    private final String lastName;
    private final String passportNumber;
    private LoyaltyStatus loyaltyStatus;

    public Flyer(String firstName, String lastName, String passportNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
        this.loyaltyStatus = LoyaltyStatus.NONE;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return getFirstName() + " " + getLastName(); }
    public String getPassportNumber() { return passportNumber; }
    public LoyaltyStatus getLoyaltyStatus() { return loyaltyStatus; }

    public void upgradeStatus(LoyaltyStatus status) {
        this.loyaltyStatus = status;
    }

    public double calculateDiscountedPrice(double price) {
        return (1.0 - this.loyaltyStatus.getDiscount()) * price;
    }

}