package code.controller;

import code.dto.fishing_instructor.NewAvailablePeriod;
import code.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static code.constants.FishingInstructorConstants.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FishingInstructorControllerTest {
    private static final String URL_PREFIX = "/api/fishing-instructor";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @WithUserDetails("marko@gmail.com")
    @Test
    public void addAvailablePeriod() throws Exception {
        NewAvailablePeriod aap = new NewAvailablePeriod();
        aap.setAvailableFrom(NEW_AVAILABLE_PERIOD_FROM);
        aap.setAvailableTo(NEW_AVAILABLE_PERIOD_TO);

        String json = TestUtil.json(aap);
        this.mockMvc.perform(post(URL_PREFIX + "/addAvailablePeriod").contentType(contentType).content(json)).andExpect(status().isOk());
    }
}
