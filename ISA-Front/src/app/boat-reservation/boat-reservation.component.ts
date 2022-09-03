import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BoatGet } from '../model/boat-get';
import { BoatService } from '../service/boat.service';

@Component({
  selector: 'app-boat-reservation',
  templateUrl: './boat-reservation.component.html',
  styleUrls: ['./boat-reservation.component.scss']
})
export class BoatReservationComponent implements OnInit {

  boats: BoatGet[] = [];
  unfilteredBoats: BoatGet[] = [];
  SearchString: string = "";
  role: string;
  startDate: Date;
  numberOfDays: number;
  numberOfPeople: number;
  setStartDate: Date;
  setNumberOfDays: number;
  setNumberOfPeople: number;
  setEndDate : Date;

  constructor(private _boatService: BoatService, private router: Router) {
    this.role = localStorage.getItem('role');
  }


  ngOnInit(): void {
    this._boatService.getAllBoats().subscribe(
      {next: data => {
        this.unfilteredBoats = data;
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

    this.boats = [];
    for(var i = 0; i < this.unfilteredBoats.length; i++){
      console.log(this.unfilteredBoats[i])
      //console.log(new Date(this.unFilteredCottages[i].availabilityPeriods[1].startDate))
        if(this.unfilteredBoats[i].maxPeople >= this.numberOfPeople
          && this.unfilteredBoats[i].availabilityPeriods.filter(e => new Date(e.startDate) <= this.setStartDate && new Date(e.endDate) > this.setEndDate).length > 0){
          this.boats.push(this.unfilteredBoats[i]);
        }
    }
  }

  makeReservation(cottageId: number, additionalServices: string[]){
    var startMonth =  this.setStartDate.getMonth() + 1
    var startMonthString: String;
    if(startMonth < 10)startMonthString = "0" + startMonth 
    else startMonthString = startMonth.toString()

    var startDay =  this.setStartDate.getDate()
    var startDayString: String;
    if(startDay < 10)startDayString = "0" + startDay 
    else startDayString = startDay.toString()

    var body = {
      boatReservationTag: additionalServices,
      numberOfDays: this.setNumberOfDays,
      numberOfPeople: this.setNumberOfPeople,
      startDate: this.setStartDate.getFullYear() + "-" + startMonthString + "-" + startDayString + "T00:00:00+0" + (-this.setStartDate.getTimezoneOffset()/60) + ":00",
      clientEmail: localStorage.getItem("email")
    }
    console.log(body)
    this._boatService.addReservation(body, cottageId).subscribe({
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
    })
  }

}
