export class ReservationTableGet {
    id: number;
    start: Date;
    end: Date;
    numberOfPeople: number;
    price: number;
    systemTax: number;
    reservationTags: string[];
    fishingTripName: string;
    clientId: number;
    clientFirstName: string;
    clientLastName: string;
}
