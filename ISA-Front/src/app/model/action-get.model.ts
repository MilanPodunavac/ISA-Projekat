import { ClientGet } from "./client-get";

export class ActionGet {
    id: number;
    range: any;
    validUntilAndIncluding: Date;
    price: number;
    discount: number;
    systemCharge: number;
    actionRefund: number;
    client: ClientGet;
    availabilityPeriod: any;
    reserved: boolean;
    ownerCommentary: any;
    loyaltyPointsGiven: boolean;
}
