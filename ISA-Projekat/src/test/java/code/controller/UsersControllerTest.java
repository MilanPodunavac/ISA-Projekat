package code.controller;


import code.controller.Base.BaseControllerTest;
import code.controller.base.BaseController;
import code.dto.admin.PasswordDTO;
import code.dto.auth.LoginRequest;
import code.dto.entities.NewCottageDto;
import code.dto.user.UpdatePasswordDto;
import code.dto.user.UpdateUserPersonalInfoDto;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersControllerTest extends BaseControllerTest {

    private static final String URL_PREFIX = "/api/users";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @WithUserDetails("ralo@gmail.com")
    @Test
    public void updatePersonalInfoShouldReturnOk() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));
        UpdateUserPersonalInfoDto dto = new UpdateUserPersonalInfoDto();
        dto.setCityName("TESTCITY");
        dto.setCountryName("TESTCOUNTRY");
        dto.setStreetName("TESTSTREETNAME14");
        dto.setLatitude(2);
        dto.setLongitude(3);
        dto.setFirstName("Mirko");
        dto.setLastName("Mirkovic");
        dto.setPhoneNumber("0621234567");
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(put(URL_PREFIX).header("Authorization", auth).contentType(contentType).content(json));
        result.andExpect(status().isOk());
    }

    @WithUserDetails("ralo@gmail.com")
    @Test
    public void updatePersonalInfoShouldReturnBadRequest() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));
        UpdateUserPersonalInfoDto dto = new UpdateUserPersonalInfoDto();
        dto.setCityName("TESTCITY");
        dto.setCountryName("TESTCOUNTRY");
        dto.setStreetName("TESTSTREETNAME14");
        dto.setLatitude(2);
        dto.setLongitude(3);
        dto.setFirstName("Mirko");
        dto.setLastName("Mirkovic");
        dto.setPhoneNumber("062");
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(put(URL_PREFIX).header("Authorization", auth).contentType(contentType).content(json));
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void updatePersonalInfoShouldReturnUnauthorized() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));
        UpdateUserPersonalInfoDto dto = new UpdateUserPersonalInfoDto();
        dto.setCityName("TESTCITY");
        dto.setCountryName("TESTCOUNTRY");
        dto.setStreetName("TESTSTREETNAME14");
        dto.setLatitude(2);
        dto.setLongitude(3);
        dto.setFirstName("Mirko");
        dto.setLastName("Mirkovic");
        dto.setPhoneNumber("062");
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(put(URL_PREFIX).contentType(contentType).content(json));
        result.andExpect(status().isUnauthorized());
    }

    @WithUserDetails("ralo@gmail.com")
    @Test
    public void changePasswordNotMatchShouldReturnBadRequest() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));
        UpdatePasswordDto dto = new UpdatePasswordDto("456", "564");
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(put(URL_PREFIX + "/password").header("Authorization", auth).contentType(contentType).content(json));
        result.andExpect(status().isBadRequest());
    }

    @WithUserDetails("ralo@gmail.com")
    @Test
    public void changePasswordInvalidShouldReturnBadRequest() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));
        UpdatePasswordDto dto = new UpdatePasswordDto("56", "56");
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(put(URL_PREFIX + "/password").header("Authorization", auth).contentType(contentType).content(json));
        result.andExpect(status().isBadRequest());
    }

    @WithUserDetails("ralo@gmail.com")
    @Test
    public void changePasswordShouldReturnOk() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo1@gmail.com", "123"));
        UpdatePasswordDto dto = new UpdatePasswordDto("456", "456");
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(put(URL_PREFIX + "/password").header("Authorization", auth).contentType(contentType).content(json));
        result.andExpect(status().isOk());
    }

    @Test
    public void changePasswordShouldUnauthorized() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));
        UpdatePasswordDto dto = new UpdatePasswordDto("456", "456");
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(put(URL_PREFIX + "/password").contentType(contentType).content(json));
        result.andExpect(status().isUnauthorized());
    }
}
