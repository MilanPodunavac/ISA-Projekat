import { ClientGet } from "./client-get";
import { FishingInstructorGet } from "./fishing-instructor-get";

export class ComplaintFishingInstructorGet {
    id: number;
    description: string;
    client: ClientGet;
    fishingInstructor: FishingInstructorGet;
}
