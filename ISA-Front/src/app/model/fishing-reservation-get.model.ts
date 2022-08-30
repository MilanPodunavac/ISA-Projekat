import { FishingTripGet } from "./fishing-trip-get";

export class FishingReservationGet {
    id: number;
    start: Date;
    end: Date;
    durationInDays: number;
    numberOfPeople: number;
    price: number;
    systemTaxPercentage: number;
    loyaltyPointsGiven: boolean;
    fishingTripReservationTags: any[];
    fishingTrip: FishingTripGet;
    client: any;
    ownerCommentary: any;
}
