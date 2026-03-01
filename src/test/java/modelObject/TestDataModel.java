package modelObject;

public class TestDataModel {
    private String baseUrl;
    private String validEmail;
    private String validPassword;
    private String newPassword;
    private String accountUrlPart;
    private String checkoutUrlPart;
    private String paymentSuccessMessage;
    private String totpErrorMessage;
    private RegisterData register;
    private Users users;

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

    public RegisterData getRegister() {
        return register;
    }

    public Users getUsers() {
        return users;
    }

    public static class RegisterData {
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String street;
        private String postCode;
        private String city;
        private String state;
        private String country;
        private String phone;
        private String emailPrefix;
        private String emailDomain;
        private String registerPassword;

        public String getRegisterPassword() {
            return registerPassword;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getStreet() {
            return street;
        }

        public String getPostCode() {
            return postCode;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmailPrefix() {
            return emailPrefix;
        }

        public String getEmailDomain() {
            return emailDomain;
        }
    }

    public static class Users {
        private LoginUser login;
        private ChangePasswordUser changePassword;

        public LoginUser getLogin() {
            return login;
        }

        public ChangePasswordUser getChangePassword() {
            return changePassword;
        }
    }

    public static class LoginUser {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class ChangePasswordUser {
        private String email;
        private String password;
        private String newPassword;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getNewPassword() {
            return newPassword;
        }
    }
}