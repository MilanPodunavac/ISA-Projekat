import { Component, OnInit } from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';
import { Router, ActivatedRoute } from '@angular/router';
import { BoatService } from 'src/app/service/boat.service';

interface AdditionalServiceMap{
  value: string;
  viewValue: string;
}

interface NavigaitonalEquipmentMap{
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-new-boat',
  templateUrl: './new-boat.component.html',
  styleUrls: ['./new-boat.component.scss']
})
export class NewBoatComponent implements OnInit {
  newBoatServices: string[] = [];
  additionalServicesTableMap: string[] = [];
  additionalServices: AdditionalServiceMap[] = [];
  selectedNewService: string;
  map: Map;

  longitude: number;
  latitude: number;
  streetName: string = "";
  cityName: string = "";
  countryName: string = "";
  rules: string = "";
  description: string = "";
  maxPeople: number;
  maxSpeed: number;
  engineNumber: number;
  enginePower: number;
  reservationRefund: number;
  fishingEquipment: string = "";
  pricePerDay: number;
  length: number;
  type: string = "";
  name: string = "";

  selectedNavigationalEquipment: string;
  navigationalEquipment: NavigaitonalEquipmentMap[] = [];

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this.navigationalEquipment.push({value: "Gps", viewValue: "Gps"})
    this.navigationalEquipment.push({value: "radar", viewValue: "Radar"})
    this.navigationalEquipment.push({value: "Vhf_radio", viewValue: "VHF radio"})
    this.navigationalEquipment.push({value: "fishfinder", viewValue: "fish finder"})

    this.additionalServices.push({ value: "wiFi", viewValue: "Wi-Fi" });
    this.additionalServices.push({ value: "petFriendly", viewValue: "Pet friendly" });
    this.additionalServices.push({ value: "ecoFriendly", viewValue: "Eco friendly" });

    this.latitude = 45.23001853946144;
    this.longitude = 19.767328606804313;
    this.streetName = "a"
    this.cityName = "Novi Sad City"
    this.countryName = "Serbia"
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      target: 'map',
      view: new View({
        center: olProj.fromLonLat([this.longitude, this.latitude]),
        zoom: 14, maxZoom: 20,
      }),
    });
  }

  AddBoat(){
    var body = {
      additionalServices: this.newBoatServices,
      cityName: this.cityName,
      countryName: this.countryName,
      description: this.description,
      length: this.length,
      type: this.type,
      engineNumber: this.engineNumber,
      enginePower: this.enginePower,
      maxSpeed: this.maxSpeed,
      maxPeople: this.maxPeople,
      navigationalEquipment: this.selectedNavigationalEquipment,
      fishingEquipment: this.fishingEquipment,
      rules: this.rules,
      streetName: "a",
      name: this.name,
      pricePerDay: this.pricePerDay,
      longitude: this.longitude,
      latitude: this.latitude,
      reservationRefund: this.reservationRefund
    }
    var errors = "";
    var notValid = false;
    if(body.cityName == ""){
      errors += "City name is required!\n"
      notValid = true;
    }
    if(body.countryName == ""){
      errors += "Country name is required!\n"
      notValid = true;
    }
    if(body.description == ""){
      errors += "Description is required!\n"
      notValid = true;
    }
    if(isNaN(Number(body.length)) || body.length <= 0){
      errors += "Length must be greater than 0\n"
      notValid = true;
    }
    if(isNaN(Number(body.engineNumber)) || body.engineNumber <= 0){
      errors += "Engine number must be greater than 0\n"
      notValid = true;
    }
    if(isNaN(Number(body.enginePower)) || body.enginePower <= 0){
      errors += "Engine power must be greater than 0\n"
      notValid = true;
    }
    if(isNaN(Number(body.maxSpeed)) || body.maxSpeed <= 0){
      errors += "Maximum speed must be greater than 0\n"
      notValid = true;
    }
    if(isNaN(Number(body.maxPeople)) || body.maxPeople <= 0){
      errors += "Maximum people must be greater than 0\n"
      notValid = true;
    }
    if(body.name == ""){
      errors += "Name is required!\n"
      notValid = true;
    }
    if(body.type == ""){
      errors += "Type is required!\n"
      notValid = true;
    }
    if(isNaN(Number(body.pricePerDay)) || body.pricePerDay <= 0){
      errors += "Price per day must be greater than 0\n"
      notValid = true;
    }
    if(isNaN(Number(body.reservationRefund)) || body.reservationRefund < 0 || body.reservationRefund > 50){
      errors += "Reservation refund must be between 0 and 50\n"
      notValid = true;
    }
    if(body.rules == ""){
      errors += "Rules must not be empty\n"
      notValid = true
    }
    if(body.streetName == ""){
      errors += "Street name must not be empty\n"
      notValid = true;
    }
    if(notValid){
      alert(errors)
      return;
    }
    this._boatService.addBoat(body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Boat added successfully")
        }
        this.router.navigate(['boat']).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['boat']).then(() => {
            window.location.reload();
          });
        }
        else{
          alert(data.error)
        }     
      }
    })
  }

  getCoord(event: any){
    var coordinate = this.map.getEventCoordinate(event);
    var lonLatCoords = olProj.toLonLat(coordinate)
    this.reverseGeocode(lonLatCoords)
  }
  reverseGeocode(coords) {
    fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
    .then(function(response) {
        return response.json();
    }).then(json => {
        this.longitude = json.lon
        this.latitude = json.lat
        this.streetName = json.address.road + " " + json.address.house_number
        this.cityName = json.address.city;
        this.countryName = json.address.country;
    });
  }
  AddService(){
    if(this.selectedNewService == undefined)return;
    let exists = false;
    for(let i=0;i<this.newBoatServices.length;i++){
      if(this.selectedNewService === this.newBoatServices[i])exists = true;
    }
    if(!exists)this.newBoatServices.push(this.selectedNewService)
    this.UpdateServicesTable();
    console.log(this.newBoatServices)
  }
  UpdateServicesTable(){
    this.additionalServicesTableMap = [];
    for(let i=0;i<this.newBoatServices.length;i++){
      for(let j=0;j<this.additionalServices.length;j++){
        if(this.newBoatServices[i] == this.additionalServices[j].value){
          this.additionalServicesTableMap.push(this.additionalServices[j].viewValue)
        }
      }
    }
  }
  RemoveService(service: any){
    for(var i = 0; i < this.newBoatServices.length; i++){
      if(this.additionalServicesTableMap[i] === service){
        this.additionalServicesTableMap.splice(i,1);
        for(let j=0;j<this.additionalServices.length;j++){
          if(this.additionalServices[j].viewValue === service){
            for(let k=0;k<this.newBoatServices.length;k++){
              if(this.additionalServices[j].value === this.newBoatServices[k]){
                this.newBoatServices.splice(k,1);
              }
            }
          }
        }
        break;
      }
    }
    this.UpdateServicesTable();
  }
}
