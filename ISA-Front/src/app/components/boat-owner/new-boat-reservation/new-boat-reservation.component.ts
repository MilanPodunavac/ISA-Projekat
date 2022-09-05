import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BoatService } from 'src/app/service/boat.service';

interface AdditionalServiceMap{
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-new-boat-reservation',
  templateUrl: './new-boat-reservation.component.html',
  styleUrls: ['./new-boat-reservation.component.scss']
})
export class NewBoatReservationComponent implements OnInit {
  name: string
  numberOfPeople: number
  numberOfDays: number
  start: Date
  clientEmail: string
  newReservationServices: string[] = [];
  additionalServicesTableMap: string[] = [];
  additionalServices: AdditionalServiceMap[] = [];
  selectedNewService: string;
  ownerNeeded: boolean = false;

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this.additionalServices.push({value:"wiFi", viewValue:"Wi-Fi"});
    this.additionalServices.push({value:"petFriendly", viewValue:"Pet friendly"});
    this.additionalServices.push({value:"ecoFriendly", viewValue:"Eco friendly"});
    this._boatService.getBoat(Number(this._route.snapshot.paramMap.get('id'))).subscribe({
      next: data => {
        this.name = data.name;
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
  }

  submit(){
    var errors = ""
    var notValid = false
    if(this.start === undefined){
      errors += "Select start date\n"
      notValid = true;
    }
    if(isNaN(Number(this.numberOfDays)) || this.numberOfDays <= 0){
      errors += "Number of days must be greater than 0\n"
      notValid = true;
    }
    if(isNaN(Number(this.numberOfPeople)) || this.numberOfPeople <= 0){
      errors += "Number of people must be greater than 0\n"
      notValid = true;
    }
    if(this.clientEmail == ""){
      errors += "Client email is required"
      notValid = true;
    }
    if(notValid){
      alert(errors)
      return;
    }
    var startMonth =  this.start.getMonth() + 1
    var startMonthString: String;
    if(startMonth < 10)startMonthString = "0" + startMonth 
    else startMonthString = startMonth.toString()

    var startDay =  this.start.getDate()
    var startDayString: String;
    if(startDay < 10)startDayString = "0" + startDay 
    else startDayString = startDay.toString()

    var id = Number(this._route.snapshot.paramMap.get('id'));
    var body = {
      boatReservationTag: this.newReservationServices,
      numberOfDays: this.numberOfDays,
      numberOfPeople: this.numberOfPeople,
      startDate: this.start.getFullYear() + "-" + startMonthString + "-" + startDayString + "T00:00:00+0" + (-this.start.getTimezoneOffset()/60) + ":00",
      clientEmail: this.clientEmail,
      ownerNeeded: this.ownerNeeded
    }
    this._boatService.addReservation(id, body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Reservation added")
        }
        this.router.navigate(['boat/' + id]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['boat/' + id]).then(() => {
            window.location.reload();
          });
        }
        else{
          alert(data.error)
        }     
      }
    })
  }
  AddService(){
    if(this.selectedNewService == undefined)return;
    let exists = false;
    for(let i=0;i<this.newReservationServices.length;i++){
      if(this.selectedNewService === this.newReservationServices[i])exists = true;
    }
    if(!exists)this.newReservationServices.push(this.selectedNewService)
    this.UpdateServicesTable();
    console.log(this.newReservationServices)
  }
  UpdateServicesTable(){
    this.additionalServicesTableMap = [];
    for(let i=0;i<this.newReservationServices.length;i++){
      for(let j=0;j<this.additionalServices.length;j++){
        if(this.newReservationServices[i] == this.additionalServices[j].value){
          this.additionalServicesTableMap.push(this.additionalServices[j].viewValue)
        }
      }
    }
  }
  RemoveService(service: any){
    for(var i = 0; i < this.newReservationServices.length; i++){
      if(this.additionalServicesTableMap[i] === service){
        this.additionalServicesTableMap.splice(i,1);
        for(let j=0;j<this.additionalServices.length;j++){
          if(this.additionalServices[j].viewValue === service){
            for(let k=0;k<this.newReservationServices.length;k++){
              if(this.additionalServices[j].value === this.newReservationServices[k]){
                this.newReservationServices.splice(k,1);
              }
            }
          }
        }
        break;
      }
    }
    this.UpdateServicesTable();
  }
}
