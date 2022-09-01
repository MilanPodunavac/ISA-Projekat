import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { toStringHDMS } from 'ol/coordinate';
import { CottageService } from '../service/cottage.service';

@Component({
  selector: 'app-cottage-reservation',
  templateUrl: './cottage-reservation.component.html',
  styleUrls: ['./cottage-reservation.component.scss']
})
export class CottageReservationComponent implements OnInit {

  cottages: any[] = [];
  unFilteredCottages: any[] = [];
  SearchString: string = "";
  role: string;
  startDate: Date;
  numberOfDays: number;
  numberOfBeds: number;
  setStartDate: Date;
  setNumberOfDays: number;
  setNumberOfBeds: number;
  setEndDate : Date;

  constructor(private _cottageService: CottageService, private router: Router) {
    this.role = localStorage.getItem('role');
  }


  ngOnInit(): void {
    this._cottageService.getAllCottages().subscribe(
      {next: data => {
        this.unFilteredCottages = data;
      }
    });
  }

  filterCottages(){
    this.setStartDate = this.startDate;
    this.setNumberOfDays = this.numberOfDays;
    this.setNumberOfBeds = this.numberOfBeds;
    this.setEndDate = new Date(this.setStartDate.getTime()+(this.numberOfDays*24*60*60*1000))
    console.log(this.setStartDate)
    console.log(this.setNumberOfDays)
    console.log(this.setNumberOfBeds)
    console.log(this.setEndDate)
    
    if(this.setStartDate === undefined || this.setNumberOfDays === 0 || this.setNumberOfBeds === 0){
      return;
    }

    this.cottages = [];
    for(var i = 0; i < this.unFilteredCottages.length; i++){
      console.log(this.unFilteredCottages[i])
      //console.log(new Date(this.unFilteredCottages[i].availabilityPeriods[1].startDate))
        if(this.unFilteredCottages[i].bedNumber >= this.numberOfBeds
          && this.unFilteredCottages[i].availabilityPeriods.filter(e => new Date(e.startDate) <= this.setStartDate && new Date(e.endDate) > this.setEndDate).length > 0){
          this.cottages.push(this.unFilteredCottages[i]);
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

