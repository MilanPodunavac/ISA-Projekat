package code.controller;

import code.dto.AdminRegistration;
import code.dto.PasswordDTO;
import code.dto.PersonalData;
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

import static code.constants.AdminConstants.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {
    private static final String URL_PREFIX = "/api/admin";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @WithUserDetails("nikola@gmail.com")
    @Test
    public void changePersonalData() throws Exception {
        PersonalData pd = new PersonalData();
        pd.setId(DB_USER_ID);
        pd.setFirstName(NEW_FIRST_NAME);
        pd.setLastName(NEW_LAST_NAME);
        pd.setPhoneNumber(NEW_PHONE_NUMBER);
        pd.setAddress(NEW_ADDRESS);
        pd.setCity(NEW_CITY);
        pd.setCountry(NEW_COUNTRY);

        String json = TestUtil.json(pd);
        this.mockMvc.perform(put(URL_PREFIX + "/changePersonalData").contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @WithUserDetails("nikola@gmail.com")
    @Test
    public void register() throws Exception {
        AdminRegistration ar = new AdminRegistration();
        ar.setFirstName(NEW_FIRST_NAME);
        ar.setLastName(NEW_LAST_NAME);
        ar.setPhoneNumber(NEW_PHONE_NUMBER);
        ar.setAddress(NEW_ADDRESS);
        ar.setCity(NEW_CITY);
        ar.setCountry(NEW_COUNTRY);
        ar.setEmail(NEW_EMAIL);
        ar.setPassword(NEW_PASSWORD);

        String json = TestUtil.json(ar);
        this.mockMvc.perform(post(URL_PREFIX + "/register").contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @WithUserDetails("jana@gmail.com")
    @Test
    public void changePassword() throws Exception {
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setId(PASSWORD_DTO_ID);
        passwordDTO.setPassword(PASSWORD_DTO_PASSWORD);

        String json = TestUtil.json(passwordDTO);
        this.mockMvc.perform(put(URL_PREFIX + "/changePassword").contentType(contentType).content(json)).andExpect(status().isOk());
    }
}
