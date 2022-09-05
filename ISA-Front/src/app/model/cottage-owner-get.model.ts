import { LocationGet } from "./location-get";
import { LoyaltyProgramProvider } from "./loyalty-program-provider.model";

export class CottageOwnerGet {
    id: number;
    firstName: number;
    lastName: number;
    email: number;
    phoneNumber: string;
    enabled: boolean;
    location: LocationGet;
    role: any;
    reasonForRegistration: string;
    cottage: any[];
    loyaltyPoints: number;
    category: LoyaltyProgramProvider;
}
