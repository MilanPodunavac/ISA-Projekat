package code.mappper;

import code.dto.RegistrationRequest;
import code.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        /*PropertyMap<RegistrationRequest, BoatOwner> boatOwnerRegistrationPropertyMap = new PropertyMap<RegistrationRequest, BoatOwner>(){
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
        modelMapper.addMappings(cottageOwnerRegistrationPropertyMap);*/
        PropertyMap<RegistrationRequest, User> userRegistrationPropertyMap = new PropertyMap<RegistrationRequest, User>(){
            protected void configure(){
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
                map().getLocation().setStreetName(source.getAddress());
            }
        };
        TypeMap<RegistrationRequest, User> userRegMap = modelMapper.createTypeMap(RegistrationRequest.class, User.class);
        userRegMap.addMappings(userRegistrationPropertyMap);
        modelMapper.createTypeMap(RegistrationRequest.class, BoatOwner.class).includeBase(RegistrationRequest.class, User.class);
        modelMapper.createTypeMap(RegistrationRequest.class, CottageOwner.class).includeBase(RegistrationRequest.class, User.class);
        modelMapper.createTypeMap(RegistrationRequest.class, FishingInstructor.class).includeBase(RegistrationRequest.class, User.class);
        modelMapper.createTypeMap(RegistrationRequest.class, Client.class).includeBase(RegistrationRequest.class, User.class);
        modelMapper.createTypeMap(RegistrationRequest.class, Admin.class).includeBase(RegistrationRequest.class, User.class);
        return modelMapper;
    }
}
