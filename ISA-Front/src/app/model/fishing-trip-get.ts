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
    fishingInstructor: any;
    pictures: any[];
    fishingTripQuickReservations: any[];
    fishingTripReservationTags: any[];
}
