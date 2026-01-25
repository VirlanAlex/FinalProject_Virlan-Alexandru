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

    public RegisterUserModel(
            String firstName,
            String lastName,
            String dateOfBirth,
            String street,
            String postCode,
            String city,
            String state,
            String country,
            String phone,
            String email,
            String password
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getStreet() { return street; }
    public String getPostCode() { return postCode; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
