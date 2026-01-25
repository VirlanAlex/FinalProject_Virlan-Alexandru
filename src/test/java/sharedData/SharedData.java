package sharedData;

public class SharedData {
    private String baseUrl;
    private String validEmail;
    private String validPassword;
    private String newPassword;
    private String accountUrlPart;
    private String checkoutUrlPart;
    private String paymentSuccessMessage;
    private String totpErrorMessage;

    public SharedData() {
        this.baseUrl = "https://practicesoftwaretesting.com/";
        this.validEmail = "virlanalexandru20@yahoo.com";
        this.validPassword = "123sd21123@asdadd2Asdsd";
        this.newPassword = "123sd21123@asdadd2Asds2";
        this.accountUrlPart = "/account";
        this.checkoutUrlPart = "/checkout";
        this.paymentSuccessMessage = "Payment was successful";
        this.totpErrorMessage = "Invalid TOTP code. Please try again.";
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getValidEmail() {
        return validEmail;
    }

    public String getValidPassword() {
        return validPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getAccountUrlPart() {
        return accountUrlPart;
    }

    public String getCheckoutUrlPart() {
        return checkoutUrlPart;
    }

    public String getPaymentSuccessMessage() {
        return paymentSuccessMessage;
    }

    public String getTotpErrorMessage() {
        return totpErrorMessage;
    }
}