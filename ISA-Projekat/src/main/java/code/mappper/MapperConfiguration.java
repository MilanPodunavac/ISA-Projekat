package code.mappper;

import code.dto.admin.AdminRegistration;
import code.dto.admin.PersonalData;
import code.dto.entities.boat.BoatGetDto;
import code.dto.entities.boat.BoatDto;
import code.dto.entities.boat.NewBoatActionDto;
import code.dto.entities.boat.NewBoatReservationDto;
import code.dto.entities.cottage.CottageGetDto;
import code.dto.entities.cottage.NewCottageActionDto;
import code.dto.entities.cottage.CottageDto;
import code.dto.entities.cottage.NewCottageReservationDto;
import code.dto.entities.NewOwnerCommentaryDto;
import code.dto.fishing_trip.EditFishingTrip;
import code.dto.fishing_trip.NewFishingTrip;
import code.dto.fishing_trip.NewQuickReservation;
import code.dto.provider_registration.ProviderDTO;
import code.dto.provider_registration.ProviderRegistrationRequest;
import code.dto.user.UpdateUserPersonalInfoDto;
import code.model.*;
import code.model.base.OwnerCommentary;
import code.model.boat.Boat;
import code.model.boat.BoatAction;
import code.model.boat.BoatOwner;
import code.model.boat.BoatReservation;
import code.model.cottage.Cottage;
import code.model.cottage.CottageAction;
import code.model.cottage.CottageOwner;
import code.model.cottage.CottageReservation;
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

        PropertyMap<CottageDto, Cottage> newCottageDtoToCottagePropertyMap = new PropertyMap<CottageDto, Cottage>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getStreetName());
                map().getLocation().setCityName(source.getCityName());
                map().getLocation().setCountryName(source.getCountryName());
                map().getLocation().setLatitude(source.getLatitude());
                map().getLocation().setLongitude(source.getLongitude());
            }
        };
        TypeMap<CottageDto, Cottage> newCottageDtoToCottage = modelMapper.createTypeMap(CottageDto.class, Cottage.class);
        newCottageDtoToCottage.addMappings(newCottageDtoToCottagePropertyMap);

        //UpdateUserPersonalInfoDto

        PropertyMap<UpdateUserPersonalInfoDto, CottageOwner> updateUserPersonalInfoDtoMap = new PropertyMap<UpdateUserPersonalInfoDto, CottageOwner>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getStreetName());
                map().getLocation().setCityName(source.getCityName());
                map().getLocation().setCountryName(source.getCountryName());
                map().getLocation().setLatitude(source.getLatitude());
                map().getLocation().setLongitude(source.getLongitude());
            }
        };
        TypeMap<UpdateUserPersonalInfoDto, CottageOwner> updateUserPersonalInfoToUserMap = modelMapper.createTypeMap(UpdateUserPersonalInfoDto.class, CottageOwner.class);
        updateUserPersonalInfoToUserMap.addMappings(updateUserPersonalInfoDtoMap);

        //NewAvailabilityPeriodDto
        /*PropertyMap<NewAvailabilityPeriodDto, AvailabilityPeriod> newAvailabilityPeriodPropMap = new PropertyMap<NewAvailabilityPeriodDto, AvailabilityPeriod>(){
            protected void configure(){
                map().setRange(new DateRange(source.getStartDate(), source.getEndDate()));
                //skip(destination.getSaleEntity());
                //skip(destination.getReservations());
            }
        };*/
        //TypeMap<NewAvailabilityPeriodDto, AvailabilityPeriod> newAvailabilityPeriodTypeMap = modelMapper.createTypeMap(NewAvailabilityPeriodDto.class, AvailabilityPeriod.class);
        //newAvailabilityPeriodTypeMap.addMappings(newAvailabilityPeriodPropMap);
        //newAvailabilityPeriodTypeMap.

        //NewCottageReservation

        PropertyMap<NewCottageReservationDto, CottageReservation> newCottageReservationMap = new PropertyMap<NewCottageReservationDto, CottageReservation>(){
            protected void configure(){

            }
        };
        TypeMap<NewCottageReservationDto, CottageReservation> newCottageReservationTypeMap = modelMapper.createTypeMap(NewCottageReservationDto.class, CottageReservation.class);

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

        // add quick reservation

        PropertyMap<NewQuickReservation, FishingTripQuickReservation> newQuickReservationFishingTripQuickReservationPropertyMap = new PropertyMap<NewQuickReservation, FishingTripQuickReservation>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<NewQuickReservation, FishingTripQuickReservation> newQuickReservationFishingTripQuickReservationTypeMap = modelMapper.createTypeMap(NewQuickReservation.class, FishingTripQuickReservation.class);
        newQuickReservationFishingTripQuickReservationTypeMap.addMappings(newQuickReservationFishingTripQuickReservationPropertyMap);

        // change fishing instructor personal data

        PropertyMap<PersonalData, FishingInstructor> personalDataFishingInstructorPropertyMap = new PropertyMap<PersonalData, FishingInstructor>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getAddress());
                map().getLocation().setCityName(source.getCity());
                map().getLocation().setCountryName(source.getCountry());
            }
        };
        TypeMap<PersonalData, FishingInstructor> personalDataFishingInstructorTypeMap = modelMapper.createTypeMap(PersonalData.class, FishingInstructor.class);
        personalDataFishingInstructorTypeMap.addMappings(personalDataFishingInstructorPropertyMap);

        PropertyMap<NewCottageActionDto, CottageAction> newCottageActionProp = new PropertyMap<NewCottageActionDto, CottageAction>(){
            protected void configure(){

            }
        };
        TypeMap<NewCottageActionDto, CottageAction> newCottageActionTypeMap = modelMapper.createTypeMap(NewCottageActionDto.class, CottageAction.class);

        //NewOwnerCommentaryDto -> OwnerCommentart

        modelMapper.createTypeMap(NewOwnerCommentaryDto.class, OwnerCommentary.class);

        //BoatDto

        PropertyMap<BoatDto, Boat> boatDtoToBoatPropertyMap = new PropertyMap<BoatDto, Boat>(){
            protected void configure(){
                map().getLocation().setStreetName(source.getStreetName());
                map().getLocation().setCityName(source.getCityName());
                map().getLocation().setCountryName(source.getCountryName());
                map().getLocation().setLatitude(source.getLatitude());
                map().getLocation().setLongitude(source.getLongitude());
            }
        };
        TypeMap<BoatDto, Boat> newBoatDtoToBoat = modelMapper.createTypeMap(BoatDto.class, Boat.class);
        newBoatDtoToBoat.addMappings(boatDtoToBoatPropertyMap);

        PropertyMap<Boat, BoatGetDto> boatToBoatDtoPropertyMap = new PropertyMap<Boat, BoatGetDto>(){
            protected void configure(){

            }
        };
        TypeMap<Boat, BoatGetDto> boatToBoatDto= modelMapper.createTypeMap(Boat.class, BoatGetDto.class);
        boatToBoatDto.addMappings(boatToBoatDtoPropertyMap);

        //NewBoatReservationDto

        TypeMap<NewBoatReservationDto, BoatReservation> newBoatReservationMap = modelMapper.createTypeMap(NewBoatReservationDto.class, BoatReservation.class);

        //NewBoatActionDto

        TypeMap<NewBoatActionDto, BoatAction> newBoatActionDtoBoatActionTypeMap = modelMapper.createTypeMap(NewBoatActionDto.class, BoatAction.class);

        //CottageGetDto

        PropertyMap<Cottage, CottageGetDto> cottageToCottageGetDtoPropMap = new PropertyMap<Cottage, CottageGetDto>() {
            @Override
            protected void configure() {
                //map().setLatitude(source.getLocation().getLatitude());
                //map().setLongitude(source.getLocation().getLongitude());
                //map().setStreetName(source.getLocation().getStreetName());
                //map().setCityName(source.getLocation().getCityName());
                //map().setCountryName(source.getLocation().getCountryName());
            }
        };

        TypeMap<Cottage, CottageGetDto> cottageToCottageGetDtoTypeMap = modelMapper.createTypeMap(Cottage.class, CottageGetDto.class);
        cottageToCottageGetDtoTypeMap.addMappings(cottageToCottageGetDtoPropMap);

        return modelMapper;
    }
}
