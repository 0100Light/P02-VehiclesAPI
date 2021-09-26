package com.udacity.pricing;

import com.udacity.pricing.api.PricingController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WebMvcTest(PricingController.class)
public class PricingServiceApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    // had to user @Before because it's Junit 4
    // @BeforeEach and @BeforeAllk is for Junit >= 5
    @Before
    public void Setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new PricingController()).build();
    }

    @Test
    public void one() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/services/price?vehicleId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void two() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/services/price?vehicleId=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
/*
    @Rule
    public BrowserWebDriverContainer<?> c  = new BrowserWebDriverContainer<>().withCapabilities(new ChromeOptions());
*/

/*
    @Test
    public void canGetPricing() {
        this.c.getWebDriver().get("http://localhost:" + port + "/services/price?vehicleId=1");
        String msg = c.getWebDriver().findElement(By.cssSelector("body")).getText();

    }
*/
}

