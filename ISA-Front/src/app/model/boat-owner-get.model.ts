import { LocationGet } from "./location-get";
import { LoyaltyProgramProvider } from "./loyalty-program-provider.model";

export class BoatOwnerGet {
    id: number;
    firstName: number;
    lastName: number;
    email: number;
    phoneNumber: string;
    enabled: boolean;
    location: LocationGet;
    role: any;
    reasonForRegistration: string;
    boat: any[];
    loyaltyPoints: number;
    category: LoyaltyProgramProvider;
}
