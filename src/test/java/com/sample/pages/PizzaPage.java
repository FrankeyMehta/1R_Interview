package com.sample.pages;

import com.sample.test.demo.constants.PizzaToppings;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

public class PizzaPage {

	private static final Logger logger = LogManager.getLogger(PizzaPage.class);
	SoftAssert softAssert = new SoftAssert();

	public PizzaPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "pizza1Pizza")
	private WebElement pizza1TypeDropdown;

	/**
	 * Selects a pizza type from the dropdown. pizzaName is the name of the pizza to
	 * select.
	 */
	public void setPizza1Type(String pizzaName) {
		pizza1TypeDropdown.sendKeys(pizzaName);
		logger.info("Pizza Selected to: " + pizzaName);
	}

	@FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings1']")
	private WebElement toppings1TypeDropdown;

	/**
	 * Selects the topping1 from the dropdown.
	 */
	public void setToppings1Type(PizzaToppings topping) {
		toppings1TypeDropdown.sendKeys(topping.name());
		logger.info("1st Topping Selected to : " + topping);
	}

	@FindBy(xpath = "//div[@id='pizza1']//select[@class='toppings2']")
	private WebElement toppings2TypeDropdown;

	/**
	 * Selects the topping2 from the dropdown.
	 */
	public void setToppings2Type(PizzaToppings topping) {
		toppings2TypeDropdown.sendKeys(topping.name());
		logger.info("2nd Topping Selected to : " + topping);
	}

	@FindBy(id = "pizza1Qty")
	private WebElement pizza1QuantityInput;

	/**
	 * Enters the quantity for the pizza
	 */
	public void setPizza1Quantity(String quantity) {
		pizza1QuantityInput.clear();
		pizza1QuantityInput.sendKeys(quantity);
		pizza1QuantityInput.sendKeys(Keys.ENTER);
		logger.info("Quantity entered to : " + quantity);
	}

	@FindBy(id = "pizza1Cost")
	private WebElement pizza1CostLabel;

	/**
	 * Retrieves the cost of the pizza in number
	 */
	public double getPizza1Cost() {
		String text = pizza1CostLabel.getAttribute("value");
		logger.info("Cost : " + text);
		return Double.parseDouble(text.replace("$", "").replace(",", ""));
	}

	@FindBy(id = "name")
	private WebElement customerNameInput;

	/**
	 * Enters the customer name.
	 */
	public void setCustomerName(String name) {
		customerNameInput.sendKeys(name);
		logger.info("Customer Name : " + name);
	}

	/**
	 * Checks if the customer name length is within the maximum allowed length.
	 */
	public void checkMaxCustomerLength(String name) {

		customerNameInput.sendKeys(name);
		String namelength = customerNameInput.getAttribute("value");
		int maxLength = 100;
		int length = namelength.length();
		if (length <= maxLength) {
			softAssert.assertTrue(true, "Customer name length is within the maximum length");
		} else {
			softAssert.assertFalse(false, "Customer name exceeds the maximum length of ");
		}
		softAssert.assertAll();
	}

	@FindBy(id = "email")
	private WebElement customerEmailInput;

	/**
	 * Enters the customer email.
	 */
	public void setCustomerEmail(String email) {
		customerEmailInput.sendKeys(email);
		logger.info("Customer Email : " + email);
	}

	/**
	 * Checks if the customer email length is within the maximum allowed length and
	 * if it follows the correct format.
	 */
	public void checkMaxEmailLength(String email) {
		customerEmailInput.sendKeys(email); // Minimum length
		String namelength = customerEmailInput.getAttribute("value");
		int maxLength = 100;
		int length = namelength.length();
		if (length <= maxLength) {
			softAssert.assertTrue(true, "Email id length is within the maximum length");
		} else {
			softAssert.assertFalse(false, "Email id exceeds the maximum length");
		}

		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		if (namelength.matches(emailPattern)) {
			softAssert.assertTrue(true, "Email format is valid");

		} else {
			softAssert.assertFalse(true, "Email format is invalid");

		}
		softAssert.assertAll();
	}

	@FindBy(id = "phone")
	private WebElement customerPhoneInput;

	/**
	 * Enters the customer phone number.
	 */
	public void setCustomerPhone(String phone) {
		customerPhoneInput.sendKeys(phone);
		logger.info("Customer PhoneNo : " + phone);
	}

	/**
	 * Checks if the customer phone number length is exactly 10 digits.
	 */
	public void checkMaxPhoneLength(String phone) {

		customerPhoneInput.sendKeys(phone);

		String phonevalue = customerPhoneInput.getAttribute("value");
		int maxLength = 10;
		int phonelength = phonevalue.length();
		if (phonelength != maxLength) {
			softAssert.assertFalse(true,
					"Phone No length is " + phonelength + ". It should be exactly " + maxLength + " digits.");

		} else {
			softAssert.assertTrue(true, "Phone No length is within the maximum length");
		}
		softAssert.assertAll();
	}

	@FindBy(id = "placeOrder")
	private WebElement placeOrderButton;

	/**
	 * Clicks the place order button to submit the order.
	 */
	public void placeOrder() {
		placeOrderButton.click();
	}

	@FindBy(id = "reset")
	private WebElement resetButton;

	/**
	 * Clicks the reset button to clear the form.
	 */
	public void resetOrder() {
		resetButton.click();
	}

	/**
	 * Checks if all form fields are reset to their default values after the reset
	 * action.
	 */
	public void checkForm() {

		String pizzaTypeValue = pizza1TypeDropdown.getAttribute("value");
		softAssert.assertTrue(pizzaTypeValue.contains("$00.00"), "Pizza type dropdown is not reset.");

		Select toppings1Select = new Select(toppings1TypeDropdown);
		String selectedOption = toppings1Select.getFirstSelectedOption().getText();
		String expectedDefaultOptionText = "Choose a Toppings 1";
		softAssert.assertEquals(selectedOption, expectedDefaultOptionText, "Topping 1 dropdown is not reset.");

		Select toppings2Select = new Select(toppings2TypeDropdown);
		String selectedOption2 = toppings2Select.getFirstSelectedOption().getText();
		String expectedDefaultOptionText2 = "Choose a Toppings 2";
		softAssert.assertEquals(selectedOption2, expectedDefaultOptionText2, "Topping 2 dropdown is not reset.");

		String quantityValue = pizza1QuantityInput.getAttribute("value");
		softAssert.assertTrue(quantityValue.contains("0"), "Pizza quantity is not reset.");

		String costValue = pizza1CostLabel.getAttribute("value");
		softAssert.assertTrue(costValue.contains("0"), "Pizza cost is not reset.");

		String customerNameValue = customerNameInput.getAttribute("value");
		softAssert.assertTrue(customerNameValue.isEmpty(), "Customer name is not reset.");

		String customerEmailValue = customerEmailInput.getAttribute("value");
		softAssert.assertTrue(customerEmailValue.isEmpty(), "Customer email is not reset.");

		String customerPhoneValue = customerPhoneInput.getAttribute("value");
		softAssert.assertTrue(customerPhoneValue.isEmpty(), "Customer phone is not reset.");

		boolean isCardPaymentSelected = cardPaymentRadioButton.isSelected();
		boolean isCashPaymentSelected = cashPaymentRadioButton.isSelected();
		softAssert.assertFalse(isCardPaymentSelected, "Credit Card payment radio button is selected.");
		softAssert.assertFalse(isCashPaymentSelected, "Cash on Pickup payment radio button is selected.");

		softAssert.assertAll();

	}

	@FindBy(id = "ccpayment")
	private WebElement cardPaymentRadioButton;

	/**
	 * Clicks on Credit Cart radio button
	 */
	public void setCardPayment() {
		cardPaymentRadioButton.click();
	}

	@FindBy(id = "cashpayment")
	private WebElement cashPaymentRadioButton;

	/**
	 * Clicks on Cash on Pickup radio button
	 */
	public void setCashPayment() {
		cashPaymentRadioButton.click();
	}

	/**
	 * Checks if the Credit Card radio button is working as expected 
	 */
	public void checkCreditCard() {
		boolean isSelected = cardPaymentRadioButton.isSelected();

		if (isSelected) {
			logger.info("Radio Button Credit Card is Selected");
			softAssert.assertFalse(isSelected, "Credit Card should not be Selected");
		} else {
			logger.info("Radio Button Credit Card is Not Selected");

		}
		softAssert.assertAll();
	}

	/**
	 * Checks if the Cash on Pickup radio button is working as expected 
	 */
	public void checkCashonPickup() {
		boolean isSelected = cashPaymentRadioButton.isSelected();
		if (isSelected) {
			logger.info("Radio Button Cash on Pickup is Selected");
			softAssert.assertFalse(isSelected, "Cash on Pickup should not be Selected");
		} else {
			logger.info("Radio Button Cash on Pickup is Not Selected");

		}
		softAssert.assertAll();
	}

	@FindBy(xpath = "//button[@title='Close']")
	private WebElement close;
	/**
	 *  Clicks on Cross icon on Dialogbox
	 */
	public void clodeDialog() {
		close.click();
	}

}
