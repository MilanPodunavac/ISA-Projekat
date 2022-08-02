package code.mappper;

import code.dto.admin.AdminRegistration;
import code.dto.admin.PersonalData;
import code.dto.provider_registration.ProviderDTO;
import code.dto.provider_registration.ProviderRegistrationRequest;
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

        PropertyMap<User, ProviderDTO> userProviderDTOPropertyMap = new PropertyMap<User, ProviderDTO>(){
            protected void configure(){
                map().setAddress(source.getLocation().getStreetName());
                map().setCity(source.getLocation().getCityName());
                map().setCountry(source.getLocation().getCountryName());
                map().setProviderType(source.getRole().getName());
            }
        };
        TypeMap<User, ProviderDTO> userProviderDTOTypeMap = modelMapper.createTypeMap(User.class, ProviderDTO.class);
        userProviderDTOTypeMap.addMappings(userProviderDTOPropertyMap);
        modelMapper.createTypeMap(BoatOwner.class, ProviderDTO.class).includeBase(User.class, ProviderDTO.class);
        modelMapper.createTypeMap(CottageOwner.class, ProviderDTO.class).includeBase(User.class, ProviderDTO.class);
        modelMapper.createTypeMap(FishingInstructor.class, ProviderDTO.class).includeBase(User.class, ProviderDTO.class);

        PropertyMap<PersonalData, Admin> personalDataAdminPropertyMap = new PropertyMap<PersonalData, Admin>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<PersonalData, Admin> personalDataAdminTypeMap = modelMapper.createTypeMap(PersonalData.class, Admin.class);
        personalDataAdminTypeMap.addMappings(personalDataAdminPropertyMap);

        PropertyMap<AdminRegistration, Admin> adminRegistrationAdminPropertyMap = new PropertyMap<AdminRegistration, Admin>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<AdminRegistration, Admin> adminRegistrationAdminTypeMap = modelMapper.createTypeMap(AdminRegistration.class, Admin.class);
        adminRegistrationAdminTypeMap.addMappings(adminRegistrationAdminPropertyMap);

        return modelMapper;
    }
}
