import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BoatGet } from '../model/boat-get';
import { BoatService } from '../service/boat.service';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-boat-profile',
  templateUrl: './boat-profile.component.html',
  styleUrls: ['./boat-profile.component.scss']
})
export class BoatProfileComponent implements OnInit {
  role: string;
  boat: BoatGet;
  map: Map;

  constructor(private _route: ActivatedRoute, private boatService: BoatService, private sanitizer: DomSanitizer, private router: Router) {this.role = localStorage.getItem('role')}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.boatService.getBoat(id).subscribe(data => {
      this.boat = data;
      for(let i = 0; i<this.boat.pictures.length; i++){
        this.boat.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.boat.pictures[i].data);
      }
      for(let i = 0; i<this.boat.availabilityPeriods.length; i++){
        var tempStart = new Date(this.boat.availabilityPeriods[i].startDate)
        var tempEnd = new Date(this.boat.availabilityPeriods[i].endDate)
        this.boat.availabilityPeriods[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        this.boat.availabilityPeriods[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
      }
      for(let i = 0; i<this.boat.boatReservations.length; i++){
        var tempStart = new Date(this.boat.boatReservations[i].startDate)
        var tempEnd = new Date(this.boat.boatReservations[i].endDate)
        this.boat.boatReservations[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        this.boat.boatReservations[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
      }
      for(let i = 0; i<this.boat.boatActions.length; i++){
        var tempStart = new Date(this.boat.boatActions[i].startDate)
        var tempEnd = new Date(this.boat.boatActions[i].endDate)
        var tempValid = new Date(this.boat.boatActions[i].validUntilAndIncluding)
        this.boat.boatActions[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        this.boat.boatActions[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        this.boat.boatActions[i].validUntilAndIncluding = tempValid.getFullYear() + "-" + (tempValid.getMonth() + 1)// + "-" + tempValid.getDate() + ":" + tempValid.getHours()
      }
      this.map = new Map({
        layers: [
          new TileLayer({
            source: new OSM(),
          }),
        ],
        target: 'map',
        view: new View({
          center: olProj.fromLonLat([this.boat.location.longitude, this.boat.location.latitude]),
          zoom: 14, maxZoom: 20,
        }),
      });
    });
    console.log(this.boat)
  }
  updateBoat(){
    this.router.navigate(['/boat-owner/boat/', this.boat.id]).then(() => {
      window.location.reload();
    });
  }
  addAvailabiltiyPeriod(){
    this.router.navigate(['/boat/', this.boat.id, `new-availability-period`]).then(() => {
      window.location.reload();
    });
  }
  addReservation(){
    this.router.navigate(['/boat/', this.boat.id, `new-boat-reservation`]).then(() => {
      window.location.reload();
    });
  }
  doubleClickResFunction(resId: number){
    if(this.role !== "ROLE_BOAT_OWNER")return
    this.router.navigate(['boat', this.boat.id, 'reservation', resId]).then(() => {
      window.location.reload();
    });
  }
  addAction(){
    this.router.navigate(['/boat/', this.boat.id, `new-boat-action`]).then(() => {
      window.location.reload();
    });
  }
  doubleClickActFunction(actId: number){
    if(this.role !== "ROLE_BOAT_OWNER")return
    this.router.navigate(['boat', this.boat.id, 'action', actId]).then(() => {
      window.location.reload();
    });
  }
  deleteBoat(){
    if(confirm("Are you sure to delete this boat?")) {
      this.boatService.deleteBoat(this.boat.id).subscribe({
        next: data =>{
          if(data.status === 200){
            alert("Boat deleted")
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
  }
}
