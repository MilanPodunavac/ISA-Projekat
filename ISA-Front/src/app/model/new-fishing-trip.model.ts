export class NewFishingTrip {
    id: number;
    name: string;
    description: string;
    rules: string;
    equipment: string;
    maxPeople: number;
    costPerDay: number;
    percentageInstructorKeepsIfReservationCancelled: number;
    address: string;
    city: string;
    country: string;
    longitude: number;
    latitude: number;
    fishingTripReservationTags: string[];
}
