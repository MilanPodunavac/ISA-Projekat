package code.mappper;

import code.dto.PersonalData;
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

        PropertyMap<User, ProviderRegistrationRequest> providerRetrievalPropertyMap = new PropertyMap<User, ProviderRegistrationRequest>(){
            protected void configure(){
                map().setAddress(source.getLocation().getStreetName());
                map().setCity(source.getLocation().getCityName());
                map().setCountry(source.getLocation().getCountryName());
                map().setProviderType(source.getRole().getName());
            }
        };
        TypeMap<User, ProviderRegistrationRequest> providerRetrievalMap = modelMapper.createTypeMap(User.class, ProviderRegistrationRequest.class);
        providerRetrievalMap.addMappings(providerRetrievalPropertyMap);
        modelMapper.createTypeMap(BoatOwner.class, ProviderRegistrationRequest.class).includeBase(User.class, ProviderRegistrationRequest.class);
        modelMapper.createTypeMap(CottageOwner.class, ProviderRegistrationRequest.class).includeBase(User.class, ProviderRegistrationRequest.class);
        modelMapper.createTypeMap(FishingInstructor.class, ProviderRegistrationRequest.class).includeBase(User.class, ProviderRegistrationRequest.class);

        PropertyMap<PersonalData, Admin> personalDataAdminPropertyMap = new PropertyMap<PersonalData, Admin>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<PersonalData, Admin> personalDataAdminTypeMap = modelMapper.createTypeMap(PersonalData.class, Admin.class);
        personalDataAdminTypeMap.addMappings(personalDataAdminPropertyMap);

        return modelMapper;
    }
}
