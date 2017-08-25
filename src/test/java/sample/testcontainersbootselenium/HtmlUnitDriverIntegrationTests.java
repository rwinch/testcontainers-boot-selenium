/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.testcontainersbootselenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Starts up the application and makes HTTP requests to it. This is nice because we are
 * testing more of the stack. However, we cannot leverage things like Spring Security's
 * test support. This still uses HtmlUnitDriver, so our JavaScript support is somewhat
 * limited.
 *
 * @author Rob Winch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HtmlUnitDriverIntegrationTests {
	private String baseUrl;

	private WebDriver driver;

	@LocalServerPort
	public void setPort(int port) {
		this.baseUrl = "http://localhost:"+ port;
	}

	@Before
	public void setup() {
		this.driver = new HtmlUnitDriver(BrowserVersion.BEST_SUPPORTED);
	}

	@Test
	public void helloWorldWorks() {
		IndexPage.to(this.driver, this.baseUrl).assertAt();
	}

}
