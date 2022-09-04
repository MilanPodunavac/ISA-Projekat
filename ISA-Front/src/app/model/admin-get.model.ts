import { LocationGet } from "./location-get";

export class AdminGet {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    enabled: boolean;
    location: LocationGet;
    mainAdmin: boolean;
    passwordChanged: boolean;
}
