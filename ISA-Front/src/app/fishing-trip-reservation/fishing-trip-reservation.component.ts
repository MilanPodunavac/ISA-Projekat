import { DatePipe, formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FishingReservationGet } from '../model/fishing-reservation-get.model';
import { FishingTripGet } from '../model/fishing-trip-get';
import { ClientService } from '../service/client.service';
import { FishingTripService } from '../service/fishing-trip.service';

@Component({
  selector: 'app-fishing-trip-reservation',
  templateUrl: './fishing-trip-reservation.component.html',
  styleUrls: ['./fishing-trip-reservation.component.scss']
})
export class FishingTripReservationComponent implements OnInit {

  fishingTrips: FishingTripGet[] = [];
  unfilteredFishingTrips: FishingTripGet[] = [];
  SearchString: string = "";
  role: string;
  startDate: Date;
  numberOfDays: number;
  numberOfPeople: number;
  setStartDate: Date;
  setNumberOfDays: number;
  setNumberOfPeople: number;
  setEndDate : Date;
  loggedInId : number;

  constructor(private _fishingTripService: FishingTripService, private router: Router, private _clientService: ClientService) {
    this.role = localStorage.getItem('role');
    this._clientService.getLoggedInClient().subscribe(
      {next: data => {
        this.loggedInId = data.id;
      }
    });
  }


  ngOnInit(): void {
    this._fishingTripService.getAllFishingTrips().subscribe(
      {next: data => {
        this.unfilteredFishingTrips = data;
      }
    });
  }

  filterTrips(){
    this.setStartDate = this.startDate;
    this.setNumberOfDays = this.numberOfDays;
    this.setNumberOfPeople = this.numberOfPeople;
    this.setEndDate = new Date(this.setStartDate.getTime()+(this.numberOfDays*24*60*60*1000))
    console.log(this.setStartDate)
    console.log(this.setNumberOfDays)
    console.log(this.setNumberOfPeople)
    console.log(this.setEndDate)
    
    if(this.setStartDate === undefined || this.setNumberOfDays === 0 || this.setNumberOfPeople === 0){
      return;
    }

    this.fishingTrips = [];
    for(var i = 0; i < this.unfilteredFishingTrips.length; i++){
      console.log(this.unfilteredFishingTrips[i])
      //console.log(new Date(this.unFilteredCottages[i].availabilityPeriods[1].startDate))
        if(this.unfilteredFishingTrips[i].maxPeople >= this.numberOfPeople
          && this.unfilteredFishingTrips[i].fishingInstructor.fishingInstructorAvailablePeriods.filter(e => new Date(e.availableFrom) <= this.setStartDate && new Date(e.availableTo) > this.setEndDate).length > 0){
          this.fishingTrips.push(this.unfilteredFishingTrips[i]);
        }
    }
  }

  makeReservation(fishingTripId: number, additionalServices: string[]){
    
    var body = new FishingReservationGet()
    body.start = formatDate(this.setStartDate, "yyyy-MM-dd", "en-GB");
    body.durationInDays = this.setNumberOfDays;
    body.numberOfPeople = this.setNumberOfPeople;
    body.fishingTripReservationTags = additionalServices;
    console.log(body)
    this._fishingTripService.addFishingTripReservation(fishingTripId, this.loggedInId, body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Reservation added")
        }
        this.router.navigate(['reservations']).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['reservations']).then(() => {
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
