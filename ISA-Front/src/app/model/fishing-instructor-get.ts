import { LocationGet } from "./location-get";

export class FishingInstructorGet {
    id : number;
    firstName : string;
    lastName : string;
    email : string;
    phoneNumber : string;
    enabled : string;
    reasonForRegistration : string;
    biography : string;
    location: LocationGet;
    fishingTrips : any[];
    fishingInstructorAvailablePeriods : any[];
    subscribers : any[];
}