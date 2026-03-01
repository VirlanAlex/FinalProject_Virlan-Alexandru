package modelObject;

public class RegisterUserModel {
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String street;
    private final String postCode;
    private final String city;
    private final String state;
    private final String country;
    private final String phone;
    private final String email;
    private final String password;

    private RegisterUserModel(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.street = builder.street;
        this.postCode = builder.postCode;
        this.city = builder.city;
        this.state = builder.state;
        this.country = builder.country;
        this.phone = builder.phone;
        this.email = builder.email;
        this.password = builder.password;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String street;
        private String postCode;
        private String city;
        private String state;
        private String country;
        private String phone;
        private String email;
        private String password;

        public Builder firstName(String val) {
            this.firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            this.lastName = val;
            return this;
        }

        public Builder dateOfBirth(String val) {
            this.dateOfBirth = val;
            return this;
        }

        public Builder street(String val) {
            this.street = val;
            return this;
        }

        public Builder postCode(String val) {
            this.postCode = val;
            return this;
        }

        public Builder city(String val) {
            this.city = val;
            return this;
        }

        public Builder state(String val) {
            this.state = val;
            return this;
        }

        public Builder country(String val) {
            this.country = val;
            return this;
        }

        public Builder phone(String val) {
            this.phone = val;
            return this;
        }

        public Builder email(String val) {
            this.email = val;
            return this;
        }

        public Builder password(String val) {
            this.password = val;
            return this;
        }

        public RegisterUserModel build() {
            return new RegisterUserModel(this);
        }
    }

    public static RegisterUserModel fromRegisterData(
            modelObject.TestDataModel.RegisterData data,
            String uniqueEmail) {

        return new Builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .dateOfBirth(data.getDateOfBirth())
                .street(data.getStreet())
                .postCode(data.getPostCode())
                .city(data.getCity())
                .state(data.getState())
                .country(data.getCountry())
                .phone(data.getPhone())
                .email(uniqueEmail)
                .password(data.getRegisterPassword())
                .build();
    }
}
