import { FishingActionGet } from "./fishing-action-get.model";
import { FishingInstructorGet } from "./fishing-instructor-get";
import { LocationGet } from "./location-get";

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
    fishingTripReviews: any[];
}
