import { FishingInstructorGet } from "./fishing-instructor-get";

export class FishingInstructorAvailablePeriodGet {
    id: number;
    availableFrom: Date;
    availableTo: Date;
    fishingInstructor: FishingInstructorGet;
}
