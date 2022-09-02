import { LocationGet } from "./location-get";

export class ClientGet {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    phoneNumber: string;
    enabled: boolean;
    location: LocationGet;
    role: string;
    penaltyPoints: number;
    banned: boolean;
    reservation: any[];
    fishingTripQuickReservations: any[];
    fishingTripReservations: any[];
    actions: any[];
    saleEntity: any[];
    review: any[];
    instructorsSubscribedTo: any[];
    loyaltyPoints: number;
    category: any;
}
