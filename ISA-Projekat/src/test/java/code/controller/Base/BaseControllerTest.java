package code.controller.Base;

import code.dto.auth.LoginRequest;
import code.dto.auth.UserTokenState;
import code.utils.TestUtil;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class BaseControllerTest {
    private static final String LOGIN_URL_PREFIX = "/auth/login";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public String logIn(MockMvc mvc, LoginRequest request) throws Exception {
        String json = TestUtil.json(request);
        ResultActions result = mvc.perform(post(LOGIN_URL_PREFIX).contentType(contentType).content(json));
        return "Bearer " + (TestUtil.json(result.andReturn().getResponse().getContentAsString(), UserTokenState.class)).getAccessToken();
    }
}
