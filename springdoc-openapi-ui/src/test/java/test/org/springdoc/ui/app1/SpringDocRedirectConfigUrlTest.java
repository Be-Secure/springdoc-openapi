package test.org.springdoc.ui.app1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import test.org.springdoc.ui.AbstractSpringDocTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {
        "springdoc.swagger-ui.configUrl=/foo/bar",
        "springdoc.swagger-ui.url=/batz" // ignored since configUrl is configured
})
public class SpringDocRedirectConfigUrlTest extends AbstractSpringDocTest {

    @SpringBootApplication
    static class SpringDocTestApp { }

    @Test
    public void shouldRedirectWithConfigUrlIgnoringQueryParams() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().isFound()).andReturn();

        String locationHeader = mvcResult.getResponse().getHeader("Location");
        assertEquals("/swagger-ui/index.html?configUrl=/foo/bar", locationHeader);
    }

}