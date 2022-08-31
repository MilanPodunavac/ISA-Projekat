import { ClientGet } from "./client-get";
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
    fishingTripReservationTags: string[];
    fishingTrip: FishingTripGet;
    client: ClientGet;
    ownerCommentary: any;
}