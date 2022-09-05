import { Component, OnInit } from '@angular/core';
import { BoatService } from 'src/app/service/boat.service';
import { Router } from '@angular/router';
import { BoatOwnerService } from '../service/boat-owner.service';

@Component({
  selector: 'app-boat-list',
  templateUrl: './boat-list.component.html',
  styleUrls: ['./boat-list.component.scss']
})
export class BoatListComponent implements OnInit {

  boats: any[] = [];
  unfilteredBoats: any[] = [];
  SearchString: string = "";
  role: string;


  constructor(private _boatOwnerService: BoatOwnerService, private _boatService: BoatService, private router: Router) { this.role = localStorage.getItem('role'); }


  ngOnInit(): void {
    if (this.role === "ROLE_BOAT_OWNER") {
      this._boatOwnerService.getOwnersBoats().subscribe(
        {
          next: data => {
            this.boats = data;
          },
          error: data => {
            console.log(data)
            alert(data.error)
          }
        });
      this._boatOwnerService.getOwnersBoats().subscribe(
        {
          next: data => {
            this.unfilteredBoats = data;
          }
        });
      return;
    }
    this._boatService.getAllBoats().subscribe(
      {
        next: data => {
          this.boats = data;
        },
        error: data => {
          console.log(data)
          alert(data.error)
        }
      });
    this._boatService.getAllBoats().subscribe(
      {
        next: data => {
          this.unfilteredBoats = data;
        }
      });
  }

  FilterFn() {
    var searchString = this.SearchString.trim().toLowerCase();

    if (searchString == "") {
      this.boats = this.unfilteredBoats;
      return;
    }

    this.boats = [];
    for (var i = 0; i < this.unfilteredBoats.length; i++) {
      if (this.unfilteredBoats[i].name.toLowerCase().includes(searchString) ||
        (this.unfilteredBoats[i].location.streetName + " " + this.unfilteredBoats[i].location.streetNumber + " " + this.unfilteredBoats[i].location.cityName + " " + this.unfilteredBoats[i].location.countryName).toLowerCase().includes(searchString) ||
        this.unfilteredBoats[i].type.toLowerCase().includes(searchString) ||
        this.unfilteredBoats[i].additionalServices.toLowerCase().includes(searchString)) {
        this.boats.push(this.unfilteredBoats[i]);
      }
    }
  }

  doubleClickFunction(id: any) {
    this.router.navigate(['/boat', id])
  }

  makeReservation() {
    this.router.navigate(['/boat/reservation'])
  }
}
