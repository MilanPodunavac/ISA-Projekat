package code.controller;

import code.controller.Base.BaseControllerTest;
import code.dto.auth.LoginRequest;
import code.dto.entities.NewCottageDto;
import code.utils.TestUtil;
import code.utils.TokenUtils;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageControllerTest extends BaseControllerTest {
    private static final String URL_PREFIX = "/api/cottage";

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
    public void addNewCottageShouldReturnOk() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));

        NewCottageDto dto = new NewCottageDto();
        dto.setBedNumber(1);
        dto.setCityName("string");
        dto.setCountryName("string");
        dto.setDescription("string");
        dto.setName("string");
        dto.setStreetName("string");
        dto.setRules("string");
        dto.setRoomNumber(1);

        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(post(URL_PREFIX).header("Authorization", auth).contentType(contentType).content(json));
        result.andExpect(status().isOk());
    }

    @WithUserDetails("ralo@gmail.com")
    @Test
    public void addNewCottageShouldReturnBadRequest() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));

        NewCottageDto dto = new NewCottageDto();
        dto.setBedNumber(0);//invalid
        dto.setCityName("string");
        dto.setCountryName("string");
        dto.setDescription("string");
        dto.setName("string");
        dto.setStreetName("string");
        dto.setRules("string");
        dto.setRoomNumber(0);//invalid

        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(post(URL_PREFIX).header("Authorization", auth).contentType(contentType).content(json));
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void addNewCottageShouldReturnUnauthorized() throws Exception {
        String auth = logIn(mockMvc, new LoginRequest("ralo@gmail.com", "123"));

        NewCottageDto dto = new NewCottageDto();
        dto.setBedNumber(1);
        dto.setCityName("string");
        dto.setCountryName("string");
        dto.setDescription("string");
        dto.setName("string");
        dto.setStreetName("string");
        dto.setRules("string");
        dto.setRoomNumber(1);
        String json = TestUtil.json(dto);
        ResultActions result = this.mockMvc.perform(post(URL_PREFIX).contentType(contentType).content(json));
        result.andExpect(status().isUnauthorized());
    }
}
