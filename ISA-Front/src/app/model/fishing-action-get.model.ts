import { ClientGet } from "./client-get";
import { FishingTripGet } from "./fishing-trip-get";
import { LocationGet } from "./location-get";

export class FishingActionGet {
    id: number;
    start: Date;
    end: Date;
    durationInDays: number;
    validUntilAndIncluding: Date;
    maxPeople: number;
    price: number;
    systemTaxPercentage: number;
    loyaltyPointsGiven: boolean;
    location: LocationGet;
    fishingTrip: FishingTripGet;
    fishingTripReservationTags: string[];
    client: ClientGet;
    ownerCommentary: any;
}
