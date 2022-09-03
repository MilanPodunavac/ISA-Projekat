import { LocationGet } from "./location-get";

export class BoatGet {
    id : number;
    name : string;
    description : string;
    rules : string;
    pricePerDay : number;
    length : number;
    type : string;
    engineNumber : number;
    enginePower : number;
    maxSpeed : number;
    maxPeople : number;
    navigationalEquipment : any;
    fishingEquipment : string
    location: LocationGet;
    additionalServices : any[];
    boatOwner : any;
    pictures : any[];
    availabilityPeriods : any[];
}
