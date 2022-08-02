package code.controller;

import code.dto.provider_registration.DeclineRegistrationRequestDTO;
import code.dto.provider_registration.ProviderRegistrationRequest;
import code.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static code.constants.RegistrationConstants.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderRegistrationControllerTest {
    private static final String URL_PREFIX = "/api/registration";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void saveRegistrationRequest() throws Exception {
        ProviderRegistrationRequest prr = new ProviderRegistrationRequest();
        prr.setFirstName(NEW_FIRST_NAME);
        prr.setLastName(NEW_LAST_NAME);
        prr.setBiography(NEW_BIOGRAPHY);
        prr.setReasonForRegistration(NEW_REASON_FOR_REGISTRATION);
        prr.setPhoneNumber(NEW_PHONE_NUMBER);
        prr.setPassword(NEW_PASSWORD);
        prr.setEmail(NEW_EMAIL);
        prr.setAddress(NEW_ADDRESS);
        prr.setCity(NEW_CITY);
        prr.setCountry(NEW_COUNTRY);
        prr.setProviderType(NEW_PROVIDER_TYPE);

        String json = TestUtil.json(prr);
        this.mockMvc.perform(post(URL_PREFIX).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @WithMockUser(roles="ADMIN")
    @Test
    public void acceptRegistrationRequest() throws Exception {
        this.mockMvc.perform(put(URL_PREFIX + "/accept-request/" + DB_USER_ID_ACCEPT)).andExpect(status().isOk());
    }

    @WithMockUser(roles="ADMIN")
    @Test
    public void declineRegistrationRequest() throws Exception {
        DeclineRegistrationRequestDTO declineRegistrationRequestDTO = new DeclineRegistrationRequestDTO();
        declineRegistrationRequestDTO.setDeclineReason("ne može");

        String json = TestUtil.json(declineRegistrationRequestDTO);
        this.mockMvc.perform(delete(URL_PREFIX + "/decline-request/" + DB_USER_ID_DECLINE).contentType(contentType).content(json)).andExpect(status().isOk());
    }
}
