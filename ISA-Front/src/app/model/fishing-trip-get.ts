import { FishingActionGet } from "./fishing-action-get.model";
import { FishingInstructorGet } from "./fishing-instructor-get";
import { LocationGet } from "./location-get";
import { ReviewFishingTripGet } from "./review-fishing-trip-get.model";

export class FishingTripGet {
    id: number;
    name: string;
    description: string;
    rules: string;
    equipment: string;
    maxPeople: number;
    costPerDay: number;
    percentageInstructorKeepsIfReservationCancelled: number;
    location: LocationGet;
    fishingInstructor: FishingInstructorGet;
    pictures: any[];
    fishingTripQuickReservations: FishingActionGet[];
    fishingTripReservationTags: string[];
    reviews: ReviewFishingTripGet[];
    grade: number;
}
