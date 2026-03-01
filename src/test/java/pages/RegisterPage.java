package pages;

import modelObject.RegisterUserModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By registerLink = By.cssSelector("a[data-test='register-link']");
    private final By firstNameInput = By.cssSelector("input#first_name");
    private final By lastNameInput = By.cssSelector("input#last_name");
    private final By dateOfBirthInput = By.cssSelector("input#dob");
    private final By streetInput = By.cssSelector("input#street");
    private final By postCodeInput = By.cssSelector("input#postal_code");
    private final By cityInput = By.cssSelector("input#city");
    private final By stateInput = By.cssSelector("input#state");
    private final By countrySelect = By.cssSelector("select[data-test='country']");
    private final By phoneInput = By.cssSelector("input#phone");
    private final By emailInput = By.cssSelector("input#email");
    private final By passwordInput = By.cssSelector("input#password");
    private final By registerButton = By.cssSelector("button.btnSubmit");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void openRegisterForm() {
        logStep("Open register form");
        elements.click(registerLink);
    }

    public void register(RegisterUserModel user) {
        logStep("Register new user (email=" + user.getEmail() + ")");
        elements.type(firstNameInput, user.getFirstName());
        elements.type(lastNameInput, user.getLastName());
        elements.type(dateOfBirthInput, user.getDateOfBirth());
        elements.type(streetInput, user.getStreet());
        elements.type(postCodeInput, user.getPostCode());
        elements.type(cityInput, user.getCity());
        elements.type(stateInput, user.getState());

        // sendKeys on <select> reliably selects option by visible text on most browsers
        elements.typeNoClear(countrySelect, user.getCountry());

        elements.type(phoneInput, user.getPhone());
        elements.type(emailInput, user.getEmail());
        elements.type(passwordInput, user.getPassword());
        elements.click(registerButton);
    }
}
