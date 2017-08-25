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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Demonstrates a test running with MockMvc based implementation of WebDriver. This allows
 * for very fast tests (no HTTP necessary), mocking specific beans for testing lots of
 * scenarios, running as a particular user with Spring Security, etc. What is nice is we
 * can reuse a lot of our testing logic in integration tests.
 *
 * The disadvantages are:
 *
 * <ul>
 *     <li>There is the possibility to miss something since we aren't actually making HTTP requests</li>
 *     <li>JavaScript support is not fully functional since HtmlUnitDriver's RHINO engine is used.</li>
 * </ul>
 *
 * @author Rob Winch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HtmlUnitDriverTests {
	@Autowired
	private WebDriver driver;

	@Test
	public void helloWorldWorks() {
		IndexPage.to(this.driver).assertAt();
	}

}
