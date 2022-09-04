import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FishingTripGet } from '../model/fishing-trip-get';
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

  constructor(private _fishingTripService: FishingTripService, private router: Router) {
    this.role = localStorage.getItem('role');
  }


  ngOnInit(): void {
    this._fishingTripService.getAllFishingTrips().subscribe(
      {next: data => {
        this.unfilteredFishingTrips = data;
      }
    });
  }

  filterBoat(){
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
    /*for(var i = 0; i < this.unfilteredFishingTrips.length; i++){
      console.log(this.unfilteredFishingTrips[i])
      //console.log(new Date(this.unFilteredCottages[i].availabilityPeriods[1].startDate))
        if(this.unfilteredFishingTrips[i].maxPeople >= this.numberOfPeople
          && this.unfilteredFishingTrips[i].startDate.filter(e => new Date(e.startDate) <= this.setStartDate && new Date(e.endDate) > this.setEndDate).length > 0){
          this.fishingTrips.push(this.unfilteredFishingTrips[i]);
        }
    }*/
  }

  makeReservation(cottageId: number, additionalServices: string[]){
    /*var startMonth =  this.setStartDate.getMonth() + 1
    var startMonthString: String;
    if(startMonth < 10)startMonthString = "0" + startMonth 
    else startMonthString = startMonth.toString()

    var startDay =  this.setStartDate.getDate()
    var startDayString: String;
    if(startDay < 10)startDayString = "0" + startDay 
    else startDayString = startDay.toString()

    var body = {
      cottageReservationTag: additionalServices,
      cottageId: cottageId,
      numberOfDays: this.setNumberOfDays,
      numberOfPeople: this.setNumberOfBeds,
      startDate: this.setStartDate.getFullYear() + "-" + startMonthString + "-" + startDayString + "T00:00:00+0" + (-this.setStartDate.getTimezoneOffset()/60) + ":00",
      clientEmail: localStorage.getItem("email")
    }
    console.log(body)
    this._cottageService.addReservation(body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Action added")
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
    })*/
  }

  

}
