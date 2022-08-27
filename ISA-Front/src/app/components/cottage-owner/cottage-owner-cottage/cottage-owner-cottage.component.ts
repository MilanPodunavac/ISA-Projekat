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

  constructor(private _cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { }

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
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      target: 'map',
      view: new View({
        center: olProj.fromLonLat([19.748239679272345, 45.250187083356856]),//center: [45.250187083356856, 19.748239679272345],
        zoom: 20, maxZoom: 20,
      }),
    });
  }
  
  ngAfterViewInit(): void {
    this.map.setTarget('map');
  }

  UpdateCottage(): void {
    alert('ddasads')
    window.location.reload();
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
    var data = this.reverseGeocode(lonLatCoords)
 }
  reverseGeocode(coords) {
    fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
    .then(function(response) {
        return response.json();
    }).then(function(json) {
        console.log(json);
        alert(json.address.house_number)
    });
  }
}
