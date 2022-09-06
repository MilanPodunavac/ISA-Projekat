import { ClientGet } from "./client-get";

export class ReservationGet {
    id: number;
    dateRange: any;
    numberOfPeople: number;
    price: number;
    reservationRefund: number;
    systemCharge: number;
    client: ClientGet;
    reservationDiscount: any;
    reservationStatus: any;
    ownerCommentary: any;
    availabilityPeriod: any;
    loyaltyPointsGiven: boolean;
    ownerNeeded: boolean
}
