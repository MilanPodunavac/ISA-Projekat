import { ClientGet } from "./client-get";

export class ReviewGet {
    id: number;
    grade: number;
    description: string;
    approved: boolean;
    client: ClientGet;
    saleEntity: any;
}
