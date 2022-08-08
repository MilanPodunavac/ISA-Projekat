package code.controller;

import code.dto.fishing_trip.EditFishingTrip;
import code.dto.fishing_trip.NewFishingTrip;
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

import static code.constants.FishingTripConstants.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FishingTripControllerTest {
    private static final String URL_PREFIX = "/api/fishing-trip";

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
    public void addFishingTrip() throws Exception {
        NewFishingTrip nft = new NewFishingTrip();
        nft.setAddress(NEW_ADDRESS);
        nft.setCity(NEW_CITY);
        nft.setCountry(NEW_COUNTRY);
        nft.setFishingTripReservationTags(NEW_FISHING_TRIP_RESERVATION_TAGS);
        nft.setDescription(NEW_DESCRIPTION);
        nft.setName(NEW_NAME);
        nft.setEquipment(NEW_EQUIPMENT);
        nft.setRules(NEW_RULES);
        nft.setPercentageInstructorKeepsIfReservationCancelled(NEW_PERCENTAGE_INSTRUCTOR_KEEPS_IF_RESERVATION_CANCELLED);
        nft.setCostPerDay(NEW_COST_PER_DAY);
        nft.setMaxPeople(NEW_MAX_PEOPLE);

        String json = TestUtil.json(nft);
        this.mockMvc.perform(post(URL_PREFIX + "/add").contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @WithUserDetails("marko@gmail.com")
    @Test
    public void editFishingTrip() throws Exception {
        EditFishingTrip eft = new EditFishingTrip();
        eft.setId(DB_USER_ID);
        eft.setAddress(NEW_ADDRESS);
        eft.setCity(NEW_CITY);
        eft.setCountry(NEW_COUNTRY);
        eft.setFishingTripReservationTags(NEW_FISHING_TRIP_RESERVATION_TAGS);
        eft.setDescription(NEW_DESCRIPTION);
        eft.setName(NEW_NAME);
        eft.setEquipment(NEW_EQUIPMENT);
        eft.setRules(NEW_RULES);
        eft.setPercentageInstructorKeepsIfReservationCancelled(NEW_PERCENTAGE_INSTRUCTOR_KEEPS_IF_RESERVATION_CANCELLED);
        eft.setCostPerDay(NEW_COST_PER_DAY);
        eft.setMaxPeople(NEW_MAX_PEOPLE);

        String json = TestUtil.json(eft);
        this.mockMvc.perform(put(URL_PREFIX + "/edit").contentType(contentType).content(json)).andExpect(status().isOk());
    }
}
