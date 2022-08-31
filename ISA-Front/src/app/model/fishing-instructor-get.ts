import { ClientGet } from "./client-get";
import { FishingInstructorAvailablePeriodGet } from "./fishing-instructor-available-period-get.model";
import { FishingTripGet } from "./fishing-trip-get";
import { LocationGet } from "./location-get";
import { LoyaltyProgramProvider } from "./loyalty-program-provider.model";

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
    fishingTrips : FishingTripGet[];
    fishingInstructorAvailablePeriods : FishingInstructorAvailablePeriodGet[];
    subscribers : ClientGet[];
    loyaltyPoints : number;
    category : LoyaltyProgramProvider;
}
