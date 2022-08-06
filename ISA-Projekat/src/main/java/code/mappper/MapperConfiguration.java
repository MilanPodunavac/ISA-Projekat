package code.mappper;

import code.dto.admin.AdminRegistration;
import code.dto.admin.PersonalData;
import code.dto.entities.NewCottageDto;
import code.dto.fishing_trip.EditFishingTrip;
import code.dto.fishing_trip.NewFishingTrip;
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

        // add provider

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

        // get providers

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

        // change admin personal data

        PropertyMap<PersonalData, Admin> personalDataAdminPropertyMap = new PropertyMap<PersonalData, Admin>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<PersonalData, Admin> personalDataAdminTypeMap = modelMapper.createTypeMap(PersonalData.class, Admin.class);
        personalDataAdminTypeMap.addMappings(personalDataAdminPropertyMap);

        // add admin

        PropertyMap<AdminRegistration, Admin> adminRegistrationAdminPropertyMap = new PropertyMap<AdminRegistration, Admin>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<AdminRegistration, Admin> adminRegistrationAdminTypeMap = modelMapper.createTypeMap(AdminRegistration.class, Admin.class);
        adminRegistrationAdminTypeMap.addMappings(adminRegistrationAdminPropertyMap);

        //NewCottageDto

        PropertyMap<NewCottageDto, Cottage> newCottageDtoToCottagePropertyMap = new PropertyMap<NewCottageDto, Cottage>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getStreetName());
                map().getLocation().setCityName(source.getCityName());
                map().getLocation().setCountryName(source.getCountryName());
                map().getLocation().setLatitude(source.getLatitude());
                map().getLocation().setLongitude(source.getLongitude());
            }
        };
        TypeMap<NewCottageDto, Cottage> newCottageDtoToCottage = modelMapper.createTypeMap(NewCottageDto.class, Cottage.class);
        newCottageDtoToCottage.addMappings(newCottageDtoToCottagePropertyMap);

        // add fishing trip

        PropertyMap<NewFishingTrip, FishingTrip> newFishingTripFishingTripPropertyMap = new PropertyMap<NewFishingTrip, FishingTrip>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<NewFishingTrip, FishingTrip> newFishingTripFishingTripTypeMap = modelMapper.createTypeMap(NewFishingTrip.class, FishingTrip.class);
        newFishingTripFishingTripTypeMap.addMappings(newFishingTripFishingTripPropertyMap);

        // edit fishing trip

        PropertyMap<EditFishingTrip, FishingTrip> editFishingTripFishingTripPropertyMap = new PropertyMap<EditFishingTrip, FishingTrip>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<EditFishingTrip, FishingTrip> editFishingTripFishingTripTypeMap = modelMapper.createTypeMap(EditFishingTrip.class, FishingTrip.class);
        editFishingTripFishingTripTypeMap.addMappings(editFishingTripFishingTripPropertyMap);

        return modelMapper;
    }
}
