import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { CottageGet } from '../model/cottage-get';
import { CottageService } from '../service/cottage.service';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['./cottage-profile.component.scss']
})
export class CottageProfileComponent implements OnInit {
  role: string;
  cottage: CottageGet;
  map: Map;

  constructor(private _route: ActivatedRoute, private cottageService: CottageService, private sanitizer: DomSanitizer, private router: Router) {this.role = localStorage.getItem('role');}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.cottageService.getCottage(id).subscribe(data => {
      this.cottage = data;
      for(let i = 0; i<this.cottage.pictures.length; i++){
        this.cottage.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.cottage.pictures[i].data);
      }
      for(let i = 0; i<this.cottage.availabilityPeriods.length; i++){
        var tempStart = new Date(this.cottage.availabilityPeriods[i].startDate)
        var tempEnd = new Date(this.cottage.availabilityPeriods[i].endDate)
        this.cottage.availabilityPeriods[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        this.cottage.availabilityPeriods[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
      }
      for(let i = 0; i<this.cottage.cottageReservations.length; i++){
        var tempStart = new Date(this.cottage.cottageReservations[i].startDate)
        var tempEnd = new Date(this.cottage.cottageReservations[i].endDate)
        this.cottage.cottageReservations[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        this.cottage.cottageReservations[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
      }
      for(let i = 0; i<this.cottage.cottageActions.length; i++){
        var tempStart = new Date(this.cottage.cottageActions[i].startDate)
        var tempEnd = new Date(this.cottage.cottageActions[i].endDate)
        var tempValid = new Date(this.cottage.cottageActions[i].validUntilAndIncluding)
        this.cottage.cottageActions[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        this.cottage.cottageActions[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        this.cottage.cottageActions[i].validUntilAndIncluding = tempValid.getFullYear() + "-" + (tempValid.getMonth() + 1)// + "-" + tempValid.getDate() + ":" + tempValid.getHours()
      }
      this.map = new Map({
        layers: [
          new TileLayer({
            source: new OSM(),
          }),
        ],
        target: 'map',
        view: new View({
          center: olProj.fromLonLat([this.cottage.location.longitude, this.cottage.location.latitude]),
          zoom: 14, maxZoom: 20,
        }),
      });
    });

  }
  updateCottage(){
    this.router.navigate(['/cottage-owner/cottage/', this.cottage.id]).then(() => {
      window.location.reload();
    });
  }
  addAvailabiltiyPeriod(){
    this.router.navigate(['/cottage/', this.cottage.id, `new-availability-period`]).then(() => {
      window.location.reload();
    });
  }
  addReservation(){
    this.router.navigate(['/cottage/', this.cottage.id, `new-cottage-reservation`]).then(() => {
      window.location.reload();
    });
  }
  doubleClickResFunction(resId: number){
    if(this.role !== "ROLE_COTTAGE_OWNER")return
    this.router.navigate(['cottage', this.cottage.id, 'reservation', resId]).then(() => {
      window.location.reload();
    });
  }
  addAction(){
    this.router.navigate(['/cottage/', this.cottage.id, `new-cottage-action`]).then(() => {
      window.location.reload();
    });
  }
  doubleClickActFunction(actId: number){
    if(this.role !== "ROLE_COTTAGE_OWNER")return
    this.router.navigate(['cottage', this.cottage.id, 'action', actId]).then(() => {
      window.location.reload();
    });
  }
  deleteCottage(){
    if(confirm("Are you sure to delete this cottage?")) {
      this.cottageService.deleteCottage(this.cottage.id).subscribe({
        next: data =>{
          if(data.status === 200){
            alert("Cottage deleted")
          }
          this.router.navigate(['cottage']).then(() => {
            window.location.reload();
          });
        },
        error: data => {
          console.log(data)
          if(data.status === 200){
            alert(data.error.text)
            this.router.navigate(['cottage']).then(() => {
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
