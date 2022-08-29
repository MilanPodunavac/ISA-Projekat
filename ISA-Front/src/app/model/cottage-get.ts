import { LocationGet } from "./location-get";

export class CottageGet {
    id : number;
    name : string;
    description : string;
    rules : string;
    roomNumber : number;
    pricePerDay : number;
    bedNumber : number;
    location: LocationGet;
    additionalServices : any[];
    cottageOwner : any;
    pictures : any[];

}
