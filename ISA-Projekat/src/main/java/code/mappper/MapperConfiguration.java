package code.mappper;

import code.dto.RegistrationRequest;
import code.model.BoatOwner;
import code.model.CottageOwner;
import code.model.FishingInstructor;
import code.model.Location;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<RegistrationRequest, BoatOwner> boatOwnerRegistrationPropertyMap = new PropertyMap<RegistrationRequest, BoatOwner>(){
            protected void configure(){
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
                map().getLocation().setStreetName(source.getAddress());
            }
        };
        PropertyMap<RegistrationRequest, FishingInstructor> fishingInstructorRegistrationPropertyMap = new PropertyMap<RegistrationRequest, FishingInstructor>(){
            protected void configure(){
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
                map().getLocation().setStreetName(source.getAddress());
            }
        };
        PropertyMap<RegistrationRequest, CottageOwner> cottageOwnerRegistrationPropertyMap = new PropertyMap<RegistrationRequest, CottageOwner>(){
            protected void configure(){
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
                map().getLocation().setStreetName(source.getAddress());
            }
        };
        modelMapper.addMappings(boatOwnerRegistrationPropertyMap);
        modelMapper.addMappings(fishingInstructorRegistrationPropertyMap);
        modelMapper.addMappings(cottageOwnerRegistrationPropertyMap);
        return modelMapper;
    }
}
