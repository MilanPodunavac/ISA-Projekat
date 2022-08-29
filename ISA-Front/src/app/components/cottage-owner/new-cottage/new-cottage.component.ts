import { Component, OnInit } from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';
import { ActivatedRoute, Router } from '@angular/router';
import { CottageService } from 'src/app/service/cottage.service';

interface AdditionalServiceMap{
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-new-cottage',
  templateUrl: './new-cottage.component.html',
  styleUrls: ['./new-cottage.component.scss']
})
export class NewCottageComponent implements OnInit {
  newCottageServices: string[] = [];
  additionalServicesTableMap: string[] = [];
  additionalServices: AdditionalServiceMap[] = [];
  selectedNewService: string;
  longitude: number;
  latitude: number;
  streetName: string;
  cityName: string;
  countryName: string;
  name: string;
  description: string;
  rules: string;
  roomNumber: number;
  pricePerDay: number;
  reservationRefund: number;
  bedPerRoom: number;
  map: Map;
  constructor(private _cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this.additionalServices.push({value:"wiFi", viewValue:"Wi-Fi"});
    this.additionalServices.push({value:"petFriendly", viewValue:"Pet friendly"});
    this.additionalServices.push({value:"minibar", viewValue:"Minibar"});
    this.additionalServices.push({value:"childFriendly", viewValue:"Child Friendly"});
    this.additionalServices.push({value:"parking", viewValue:"Parking"});
    this.latitude = 45.25282;
    this.longitude = 19.8366829;
    this.streetName = "Bulevar oslobodjenja 74"
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

  AddService(){
    if(this.selectedNewService == undefined)return;
    let exists = false;
    for(let i=0;i<this.newCottageServices.length;i++){
      if(this.selectedNewService === this.newCottageServices[i])exists = true;
    }
    if(!exists)this.newCottageServices.push(this.selectedNewService)
    this.UpdateServicesTable();
    console.log(this.newCottageServices)
  }
  UpdateServicesTable(){
    this.additionalServicesTableMap = [];
    for(let i=0;i<this.newCottageServices.length;i++){
      for(let j=0;j<this.additionalServices.length;j++){
        if(this.newCottageServices[i] == this.additionalServices[j].value){
          this.additionalServicesTableMap.push(this.additionalServices[j].viewValue)
        }
      }
    }
  }
  RemoveService(service: any){
    for(var i = 0; i < this.newCottageServices.length; i++){
      if(this.additionalServicesTableMap[i] === service){
        this.additionalServicesTableMap.splice(i,1);
        for(let j=0;j<this.additionalServices.length;j++){
          if(this.additionalServices[j].viewValue === service){
            for(let k=0;k<this.newCottageServices.length;k++){
              if(this.additionalServices[j].value === this.newCottageServices[k]){
                this.newCottageServices.splice(k,1);
              }
            }
          }
        }
        break;
      }
    }
    this.UpdateServicesTable();
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
  submit(){
    var errors = "";
    var notValid = false;
    if(Number(this.bedPerRoom) == NaN || this.bedPerRoom <= 0){
      errors += "Beds per room must be greater than 0!\n"
      notValid = true;
    }
    if(this.cityName == ""){
      errors += "City name is required!\n"
      notValid = true;
    }
    if(this.countryName == ""){
      errors += "Country name is required!\n"
      notValid = true;
    }
    if(this.description == ""){
      errors += "Description is required!\n"
      notValid = true;
    }
    if(this.name == ""){
      errors += "Name is required!\n"
      notValid = true;
    }
    if(isNaN(Number(this.pricePerDay)) || this.pricePerDay <= 0){
      errors += "Price per day must be greater than 0\n"
      notValid = true;
    }
    if(isNaN(Number(this.reservationRefund)) || this.reservationRefund < 0 || this.reservationRefund > 50){
      errors += "Reservation refund must be between 0 and 50\n"
      notValid = true;
    }
    if(isNaN(Number(this.roomNumber)) || this.roomNumber <= 0){
      errors += "Room number must be greater than 0\n"
      notValid = true;
    }
    if(this.rules == ""){
      errors += "Rules must not be empty\n"
      notValid = true
    }
    if(this.streetName == ""){
      errors += "Street name must not be empty\n"
      notValid = true;
    }
    if(notValid){
      alert(errors)
      return;
    }
    var body = {
      additionalServices: this.newCottageServices,
      bedNumber: this.bedPerRoom,
      cityName: this.cityName,
      countryName: this.countryName,
      description: this.description,
      latitude: this.latitude,
      longitude: this.longitude,
      name: this.name,
      pricePerDay: this.pricePerDay,
      reservationRefund: this.reservationRefund,
      roomNumber: this.roomNumber,
      rules: this.rules,
      streetName: this.streetName
    }
    this._cottageService.addCottage(body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Cottage added")
        }
        this.router.navigate(['cottage-owner']).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['cottage-owner']).then(() => {
            window.location.reload();
          });
        }
        else{
          alert(data.error)
        }     
      }
    })
  }
}
