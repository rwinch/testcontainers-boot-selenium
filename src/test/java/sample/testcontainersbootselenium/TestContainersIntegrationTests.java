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

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.dockerclient.DockerClientConfigUtils;
import org.testcontainers.dockerclient.LogToStringContainerCallback;

import java.util.Optional;

/**
 * We can easily run our tests within a browser version that works with our WebDriver
 * implementation using docker containers.
 *
 * @author Rob Winch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestContainersIntegrationTests {
	@Rule
	public BrowserWebDriverContainer browser = (BrowserWebDriverContainer) new BrowserWebDriverContainer()
			.withDesiredCapabilities(DesiredCapabilities.chrome());

	private String baseUrl;

	private WebDriver driver;

	@LocalServerPort
	public void setPort(int port) throws Exception {
		this.baseUrl = "http://" + getDetectedDockerHostIp() + ":"+ port;
	}

	@Before
	public void setup() {
		this.driver = this.browser.getWebDriver();
	}

	@Test
	public void helloWorldWorks() {
		IndexPage.to(this.driver, this.baseUrl).assertAt();
	}

	/**
	 * Taken from {@link DockerClientConfigUtils#getDetectedDockerHostIp()} but removed
	 * {@link DockerClientConfigUtils#IN_A_CONTAINER} because this always reports false.
	 *
	 * @return the ip address for the browser to connect to
	 */
	private static String getDetectedDockerHostIp() {
		return Optional.ofNullable(DockerClientFactory.instance().runInsideDocker(
				cmd -> cmd.withCmd("sh", "-c", "ip route|awk '/default/ { print $3 }'"),
				(client, id) -> {
					try {
						return client.logContainerCmd(id)
								.withStdOut(true)
								.exec(new LogToStringContainerCallback())
								.toString();
					} catch (Exception e) {
						return null;
					}
				}
		))
		.map(StringUtils::trimToEmpty)
		.filter(StringUtils::isNotBlank)
		.orElse(null);
	}
}
