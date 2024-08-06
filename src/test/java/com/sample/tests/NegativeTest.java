package com.sample.tests;

import com.sample.pages.ConfirmationDialog;
import com.sample.pages.PizzaPage;
import com.sample.test.demo.TestBase;
import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class NegativeTest extends TestBase {

	@Test(priority = 1)
	public void checkMissingPhoneNum() throws Exception {

		PizzaPage pizzaPage = new PizzaPage(this.driver);
		pizzaPage.setPizza1Type(PizzaTypes.LARE_NOTOPPINGS.toString());

		pizzaPage.setToppings1Type(PizzaToppings.MOZZARELLA);
		pizzaPage.setToppings2Type(PizzaToppings.OLIVES);
		pizzaPage.setPizza1Quantity("1");

		pizzaPage.setCustomerName("AlphaOne");
		pizzaPage.setCustomerEmail("Alpha@yopmail.com");

		pizzaPage.placeOrder();

		ConfirmationDialog confirmationDialog = new ConfirmationDialog(this.driver);

		String text = confirmationDialog.getConfirmationText();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(text, "Missing phone number");
		softAssert.assertAll();
		pizzaPage.clodeDialog();

	}

	@Test(priority = 2)
	public void checkMissingName() throws Exception {

		PizzaPage pizzaPage = new PizzaPage(this.driver);
		pizzaPage.setPizza1Type(PizzaTypes.LARE_NOTOPPINGS.toString());

		pizzaPage.setToppings1Type(PizzaToppings.MOZZARELLA);
		pizzaPage.setToppings2Type(PizzaToppings.OLIVES);
		pizzaPage.setPizza1Quantity("1");

		pizzaPage.setCustomerEmail("Beta@yopmail.com");
		pizzaPage.setCustomerPhone("1234567890");

		pizzaPage.setCardPayment();
		pizzaPage.placeOrder();

		ConfirmationDialog confirmationDialog = new ConfirmationDialog(this.driver);

		String text = confirmationDialog.getConfirmationText();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(text, "Missing name");
		softAssert.assertAll();
		pizzaPage.clodeDialog();
	}

	@Test(priority = 3)
	public void checkCreditCardRadiobutton() throws Exception {

		PizzaPage pizzaPage = new PizzaPage(this.driver);
		pizzaPage.setCardPayment();
		pizzaPage.setCashPayment();
		pizzaPage.checkCreditCard();
	}

	@Test(priority = 4)
	public void checkCashonPickupRadiobutton() throws Exception {

		PizzaPage pizzaPage = new PizzaPage(this.driver);
		pizzaPage.setCashPayment();
		pizzaPage.setCardPayment();
		pizzaPage.checkCashonPickup();
	}

	@Test(priority = 5)
	public void checkResetbutton() throws Exception {

		PizzaPage pizzaPage = new PizzaPage(this.driver);
		pizzaPage.setPizza1Type(PizzaTypes.LARE_NOTOPPINGS.toString());
		pizzaPage.setToppings1Type(PizzaToppings.MOZZARELLA);
		pizzaPage.setToppings2Type(PizzaToppings.OLIVES);
		pizzaPage.setPizza1Quantity("1");
		pizzaPage.setCustomerName("Ivanoff");
		pizzaPage.setCustomerEmail("Abra@cadabra");
		pizzaPage.setCustomerPhone("123321");
		pizzaPage.setCardPayment();
		pizzaPage.resetOrder();
		pizzaPage.checkForm();
	}

	@Test(priority = 6)
	public void checkmaxLength() throws Exception {
		PizzaPage pizzaPage = new PizzaPage(this.driver);
		pizzaPage.checkMaxCustomerLength("Gamma");
		pizzaPage.checkMaxEmailLength("Gamma@gmail.com");
		pizzaPage.checkMaxPhoneLength("0123456789");

	}

}
