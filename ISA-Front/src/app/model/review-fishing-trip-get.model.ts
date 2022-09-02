import { ClientGet } from "./client-get";
import { FishingTripGet } from "./fishing-trip-get";

export class ReviewFishingTripGet {
    id: number;
    grade: number;
    description: string;
    approved: boolean;
    client: ClientGet;
    fishingTrip: FishingTripGet;
}
