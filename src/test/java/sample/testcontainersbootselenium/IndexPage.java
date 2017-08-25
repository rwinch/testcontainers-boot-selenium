package sample.testcontainersbootselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class IndexPage {
	private WebElement message;

	public void assertAt() {
		assertThat(this.message.getText()).isEqualTo("Hello Boot!");
	}

	public static IndexPage to(WebDriver driver) {
		return to(driver, "http://localhost:8080");
	}

	public static IndexPage to(WebDriver driver, String baseUrl) {
		driver.get(baseUrl + "/index.html");
		return PageFactory.initElements(driver, IndexPage.class);
	}
}
