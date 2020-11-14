package Assistants;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import Page.PagePrincipal;
import core.DriverUtils;

@SuppressWarnings("all")
public class ServiceGetCourses {

	private static String URL = "";

	private static PagePrincipal page = null;

	public ServiceGetCourses(String URL) {
		this.URL = URL;
	}

	private static List<String> coursesLinks;

	@Test
	public void buyFreeCourses() {
		getLinkCourses();

		getCourses();
	}

	private void getCourses() {

		for (int i = 0; i < coursesLinks.size(); i++) {

			openNavigator(coursesLinks.get(i));

			initPages();

			addCourseToCart();

		}

	}

	private void addCourseToCart() {

		DriverUtils.delay(3000);

		if (DriverUtils.buscarElementoPresente(page.btnAddToCart)) {

			DriverUtils.clicar(page.btnAddToCart);

			DriverUtils.esperarPor(By.xpath(page.BtnAddToCart));

			DriverUtils.clicar(page.btnClosePopUp);
			
			DriverUtils.esperarPorAjax();
			
			DriverUtils.scrollToElemento(page.btnAddToCart);
			
			DriverUtils.clicar(page.btnAddToCart);

			DriverUtils.delay(5000);

			assertTrue(page.spanGoToCourse.getText().contains("Gratuito"));

		}

	}

	private void getLinkCourses() {

		openNavigator(URL);

		initPages();

		DriverUtils.esperarPor(By.xpath(page.LinkList));

		initPages();

		coursesLinks = new ArrayList<>();

		for (int i = 0; i < page.listLink.size() - 1; i++) {

			DriverUtils.delay(250);

			DriverUtils.scrollToElemento(page.listLink.get(i));

			DriverUtils.delay(250);

			String link = page.listLink.get(i).getText();

			coursesLinks.add(link);

			DriverUtils.delay(250);

		}

		DriverUtils.fecharDriver();

	}

	private void initPages() {

		page = new PagePrincipal();

		page = PageFactory.initElements(DriverUtils.getDriver(), PagePrincipal.class);

	}

	private void openNavigator(String URL) {

		DriverUtils.getDriver().get(URL);

		DriverUtils.getDriver().manage().window().maximize();

	}

	@After
	public void close() {

		DriverUtils.fecharDriver();

	}

}
