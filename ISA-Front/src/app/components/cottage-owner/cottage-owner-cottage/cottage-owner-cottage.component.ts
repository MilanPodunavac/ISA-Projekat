import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CottageService } from '../../../service/cottage.service';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';
import {useGeographic} from 'ol/proj';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';

interface AdditionalServiceMap{
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-cottage-owner-cottage',
  templateUrl: './cottage-owner-cottage.component.html',
  styleUrls: ['./cottage-owner-cottage.component.scss']
})
export class CottageOwnerCottageComponent implements OnInit {
  cottage: any;
  additionalServicesTableMap: string[] = [];
  additionalServices: AdditionalServiceMap[] = [];
  selectedNewService: string;
  map: Map
  imageSrc: SafeStyle  = "./assets/images/no-image.jpg";
  imageFile: File = null;

  constructor(private _cottageService: CottageService, private router: Router, private _route: ActivatedRoute, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.additionalServices.push({value:"wiFi", viewValue:"Wi-Fi"});
    this.additionalServices.push({value:"petFriendly", viewValue:"Pet friendly"});
    this.additionalServices.push({value:"minibar", viewValue:"Minibar"});
    this.additionalServices.push({value:"childFriendly", viewValue:"Child Friendly"});
    this.additionalServices.push({value:"parking", viewValue:"Parking"});
    this._cottageService.getCottage(Number(this._route.snapshot.paramMap.get('id'))).subscribe({
      next: data => {
        this.cottage = data;
        this.UpdateServicesTable();
        this.map = new Map({
          layers: [
            new TileLayer({
              source: new OSM(),
            }),
          ],
          target: 'map',
          view: new View({
            center: olProj.fromLonLat([this.cottage.longitude, this.cottage.latitude]),
            zoom: 17, maxZoom: 20,
          }),
        });
        for(let i = 0; i<this.cottage.pictures.length; i++){
          this.cottage.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.cottage.pictures[i].data);
        }
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
  }
  
  ngAfterViewInit(): void {
    this.map.setTarget('map');
  }

  UpdateCottage(): void {
    var id = Number(this._route.snapshot.paramMap.get('id'))
    var body = {
      additionalServices: this.cottage.additionalServices,
      bedNumber: this.cottage.bedNumber,
      roomNumber: this.cottage.roomNumber,
      cityName: this.cottage.cityName,
      countryName: this.cottage.countryName,
      description: this.cottage.description,
      rules: this.cottage.rules,
      streetName: this.cottage.streetName,
      name: this.cottage.name,
      pricePerDay: this.cottage.pricePerDay,
      longitude: this.cottage.longitude,
      latitude: this.cottage.latitude,
      reservationRefund: this.cottage.reservationRefund
    }
    var errors = "";
    var notValid = false;
    if((isNaN(body.bedNumber)) || body.bedNumber <= 0){
      errors += "Beds per room must be greater than 0!\n"
      notValid = true;
    }
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
    if(body.name == ""){
      errors += "Name is required!\n"
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
    if(isNaN(Number(body.roomNumber)) || body.roomNumber <= 0){
      errors += "Room number must be greater than 0\n"
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
    this._cottageService.updateCottage(id, this.cottage).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Cottage updated")
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

  RemoveService(service: any){
    for(var i = 0; i < this.cottage.additionalServices.length; i++){
      if(this.additionalServicesTableMap[i] === service){
        this.additionalServicesTableMap.splice(i,1);
        for(let j=0;j<this.additionalServices.length;j++){
          if(this.additionalServices[j].viewValue === service){
            for(let k=0;k<this.cottage.additionalServices.length;k++){
              if(this.additionalServices[j].value === this.cottage.additionalServices[k]){
                this.cottage.additionalServices.splice(k,1);
              }
            }
          }
        }
        break;
      }
    }
    this.UpdateServicesTable();
  }
  AddService(){
    if(this.selectedNewService == undefined)return;
    let exists = false;
    for(let i=0;i<this.cottage.additionalServices.length;i++){
      if(this.selectedNewService === this.cottage.additionalServices[i])exists = true;
    }
    if(!exists)this.cottage.additionalServices.push(this.selectedNewService)
    this.UpdateServicesTable();
  }
  UpdateServicesTable(){
    this.additionalServicesTableMap = [];
    for(let i=0;i<this.cottage.additionalServices.length;i++){
      for(let j=0;j<this.additionalServices.length;j++){
        if(this.cottage.additionalServices[i] == this.additionalServices[j].value){
          this.additionalServicesTableMap.push(this.additionalServices[j].viewValue)
        }
      }
    }
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
        console.log(this.cottage)
        this.cottage.longitude = json.lon
        this.cottage.latitude = json.lat
        this.cottage.streetName = json.address.road + " " + json.address.house_number
        this.cottage.cityName = json.address.city;
        this.cottage.countryName = json.address.country;
    });
  }

  onFileChange(event) {
    const reader = new FileReader();
    
    if(event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      reader.onload = () => {this.imageSrc = reader.result as string};
      this.imageFile = file;
    }
  }

  RemoveImage(id: number){
    this._cottageService.removeImage(this.cottage.id, id).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Image removed")
        }
        window.location.reload();
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          window.location.reload();
        }
        else{
          alert(data.error)
        }     
      }
    })
  }

  AddPicture(){
    if(this.imageFile === null){
      alert("No image selected!")
      return
    }
    this._cottageService.addImage(this.cottage.id, this.imageFile).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Image added")
        }
        window.location.reload();
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          window.location.reload();
        }
        else{
          alert(data.error)
        }     
      }
    })
  }
}
