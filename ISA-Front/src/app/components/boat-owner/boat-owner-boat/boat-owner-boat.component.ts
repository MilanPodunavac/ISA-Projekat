import { Component, OnInit } from '@angular/core';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';
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
  selector: 'app-boat-owner-boat',
  templateUrl: './boat-owner-boat.component.html',
  styleUrls: ['./boat-owner-boat.component.scss']
})
export class BoatOwnerBoatComponent implements OnInit {
  boat: any;
  additionalServicesTableMap: string[] = [];
  additionalServices: AdditionalServiceMap[] = [];
  selectedNewService: string;
  map: Map
  imageSrc: SafeStyle  = "./assets/images/no-image.jpg";
  imageFile: File = null;

  navigationalEquipment: NavigaitonalEquipmentMap[] = [];

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    if(localStorage.getItem("role") != "ROLE_BOAT_OWNER"){
      this.router.navigate(['login']).then(() => {
        window.location.reload();
      });
    }
    this.navigationalEquipment.push({value: "Gps", viewValue: "Gps"})
    this.navigationalEquipment.push({value: "radar", viewValue: "Radar"})
    this.navigationalEquipment.push({value: "Vhf_radio", viewValue: "VHF radio"})
    this.navigationalEquipment.push({value: "fishfinder", viewValue: "fish finder"})

    this.additionalServices.push({ value: "wiFi", viewValue: "Wi-Fi" });
    this.additionalServices.push({ value: "petFriendly", viewValue: "Pet friendly" });
    this.additionalServices.push({ value: "ecoFriendly", viewValue: "Eco friendly" });
    this._boatService.getBoat(Number(this._route.snapshot.paramMap.get('id'))).subscribe({
      next: data => {
        this.boat = data;
        this.UpdateServicesTable();
        this.map = new Map({
          layers: [
            new TileLayer({
              source: new OSM(),
            }),
          ],
          target: 'map',
          view: new View({
            center: olProj.fromLonLat([this.boat.location.longitude, this.boat.location.latitude]),
            zoom: 15, maxZoom: 20,
          }),
        });
        for(let i = 0; i<this.boat.pictures.length; i++){
          this.boat.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.boat.pictures[i].data);
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

  UpdateBoat(): void {
    var id = Number(this._route.snapshot.paramMap.get('id'))
    var body = {
      additionalServices: this.boat.additionalServices,
      cityName: this.boat.location.cityName,
      countryName: this.boat.location.countryName,
      description: this.boat.description,
      length: this.boat.length,
      type: this.boat.type,
      engineNumber: this.boat.engineNumber,
      enginePower: this.boat.enginePower,
      maxSpeed: this.boat.maxSpeed,
      maxPeople: this.boat.maxPeople,
      navigationalEquipment: this.boat.navigationalEquipment,
      fishingEquipment: this.boat.fishingEquipment,
      rules: this.boat.rules,
      streetName: "a",
      name: this.boat.name,
      pricePerDay: this.boat.pricePerDay,
      longitude: this.boat.location.longitude,
      latitude: this.boat.location.latitude,
      reservationRefund: this.boat.reservationRefund
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
    this._boatService.updateBoat(id, body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Boat updated")
        }
        this.router.navigate(['/boat/', id]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['/boat/', id]).then(() => {
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
    for(var i = 0; i < this.boat.additionalServices.length; i++){
      if(this.additionalServicesTableMap[i] === service){
        this.additionalServicesTableMap.splice(i,1);
        for(let j=0;j<this.additionalServices.length;j++){
          if(this.additionalServices[j].viewValue === service){
            for(let k=0;k<this.boat.additionalServices.length;k++){
              if(this.additionalServices[j].value === this.boat.additionalServices[k]){
                this.boat.additionalServices.splice(k,1);
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
    for(let i=0;i<this.boat.additionalServices.length;i++){
      if(this.selectedNewService === this.boat.additionalServices[i])exists = true;
    }
    if(!exists)this.boat.additionalServices.push(this.selectedNewService)
    this.UpdateServicesTable();
  }
  UpdateServicesTable(){
    this.additionalServicesTableMap = [];
    for(let i=0;i<this.boat.additionalServices.length;i++){
      for(let j=0;j<this.additionalServices.length;j++){
        if(this.boat.additionalServices[i] == this.additionalServices[j].value){
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
        console.log(this.boat)
        this.boat.location.longitude = json.lon
        this.boat.location.latitude = json.lat
        this.boat.location.streetName = json.address.road + " " + json.address.house_number
        this.boat.location.cityName = json.address.city;
        this.boat.location.countryName = json.address.country;
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
    this._boatService.removeImage(this.boat.id, id).subscribe({
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
    this._boatService.addImage(this.boat.id, this.imageFile).subscribe({
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
