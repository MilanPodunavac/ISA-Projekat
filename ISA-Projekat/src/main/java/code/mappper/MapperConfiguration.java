package code.mappper;

import code.dto.ProviderRegistrationRequest;
import code.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        PropertyMap<ProviderRegistrationRequest, User> userRegistrationPropertyMap = new PropertyMap<ProviderRegistrationRequest, User>(){
            protected void configure(){
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
                map().getLocation().setStreetName(source.getAddress());
            }
        };
        TypeMap<ProviderRegistrationRequest, User> userRegMap = modelMapper.createTypeMap(ProviderRegistrationRequest.class, User.class);
        userRegMap.addMappings(userRegistrationPropertyMap);
        modelMapper.createTypeMap(ProviderRegistrationRequest.class, BoatOwner.class).includeBase(ProviderRegistrationRequest.class, User.class);
        modelMapper.createTypeMap(ProviderRegistrationRequest.class, CottageOwner.class).includeBase(ProviderRegistrationRequest.class, User.class);
        modelMapper.createTypeMap(ProviderRegistrationRequest.class, FishingInstructor.class).includeBase(ProviderRegistrationRequest.class, User.class);
        modelMapper.createTypeMap(ProviderRegistrationRequest.class, Client.class).includeBase(ProviderRegistrationRequest.class, User.class);
        modelMapper.createTypeMap(ProviderRegistrationRequest.class, Admin.class).includeBase(ProviderRegistrationRequest.class, User.class);
        return modelMapper;
    }
}
