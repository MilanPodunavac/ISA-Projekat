import { ClientGet } from "./client-get";

export class ComplaintGet {
    id: number;
    description: string;
    client: ClientGet;
    saleEntity: any;
}
