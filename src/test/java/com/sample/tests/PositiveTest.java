package com.sample.tests;

import com.sample.pages.ConfirmationDialog;
import com.sample.pages.PizzaPage;
import com.sample.test.demo.TestBase;
import com.sample.test.demo.constants.PizzaToppings;
import com.sample.test.demo.constants.PizzaTypes;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;

public class PositiveTest extends TestBase {

	@DataProvider(name = "pizza types")
	public Object[][] pizzaTypes() {
		ArrayList<Object[]> objects = new ArrayList<>();
		for (PizzaTypes types : PizzaTypes.values()) {
			objects.add(new Object[] { types.getDisplayName(), types.getCost() });
		}
		return objects.toArray(new Object[0][]);
	}

	@Test(dataProvider = "pizza types",priority = 0)
	public void testPizzaType(String pizzaName, Double pizzaCost) throws Exception {

		PizzaPage pizzaPage = new PizzaPage(this.driver);
		pizzaPage.setPizza1Type(pizzaName);

		pizzaPage.setToppings1Type(PizzaToppings.MOZZARELLA);
		pizzaPage.setToppings2Type(PizzaToppings.OLIVES);
		pizzaPage.setPizza1Quantity("1");

		double cost = pizzaPage.getPizza1Cost();

		pizzaPage.setCustomerName("Frankey");
		pizzaPage.setCustomerEmail("Franky@gmail.com");
		pizzaPage.setCustomerPhone("1234567890");

		pizzaPage.setCardPayment();
		pizzaPage.placeOrder();

		ConfirmationDialog confirmationDialog = new ConfirmationDialog(this.driver);
		String text = confirmationDialog.getConfirmationText();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(cost, pizzaCost);
		softAssert.assertEquals(text, "Thank you for your order! TOTAL: " + pizzaCost + " " + pizzaName);
		softAssert.assertAll();

		pizzaPage.clodeDialog();

		pizzaPage.checkForm();

	}

}
