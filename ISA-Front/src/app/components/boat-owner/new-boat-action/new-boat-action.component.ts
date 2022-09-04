import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BoatService } from 'src/app/service/boat.service';

interface AdditionalServiceMap {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-new-boat-action',
  templateUrl: './new-boat-action.component.html',
  styleUrls: ['./new-boat-action.component.scss']
})
export class NewBoatActionComponent implements OnInit {
  name: string
  numberOfPeople: number
  numberOfDays: number
  discount: number
  start: Date
  validUntil: Date
  newActionServices: string[] = [];
  additionalServicesTableMap: string[] = [];
  additionalServices: AdditionalServiceMap[] = [];
  selectedNewService: string;
  ownerNeeded: boolean = false;

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this.additionalServices.push({ value: "wiFi", viewValue: "Wi-Fi" });
    this.additionalServices.push({ value: "petFriendly", viewValue: "Pet friendly" });
    this.additionalServices.push({ value: "ecoFriendly", viewValue: "Eco friendly" });
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
  submit() {
    var errors = ""
    var notValid = false
    if (this.start === undefined || this.validUntil === undefined) {
      errors += "Select start date and valid until and including date\n"
      notValid = true;
    }
    if (isNaN(Number(this.numberOfDays)) || this.numberOfDays <= 0) {
      errors += "Number of days must be greater than 0\n"
      notValid = true;
    }
    if (isNaN(Number(this.numberOfPeople)) || this.numberOfPeople <= 0) {
      errors += "Number of people must be greater than 0\n"
      notValid = true;
    }
    if (isNaN(Number(this.discount)) || this.discount < 0 || this.discount > 50) {
      errors += "Discount must be between 0 and 50\n"
      notValid = true;
    }
    if (notValid) {
      alert(errors)
      return;
    }
    var startMonth = this.start.getMonth() + 1
    var startMonthString: String;
    if (startMonth < 10) startMonthString = "0" + startMonth
    else startMonthString = startMonth.toString()

    var startDay = this.start.getDate()
    var startDayString: String;
    if (startDay < 10) startDayString = "0" + startDay
    else startDayString = startDay.toString()

    var validUntilMonth = this.validUntil.getMonth() + 1
    var validUntilMonthString: String;
    if (validUntilMonth < 10) validUntilMonthString = "0" + validUntilMonth
    else validUntilMonthString = validUntilMonth.toString()

    var validUntilDay = this.validUntil.getDate()
    var validUntilDayString: String;
    if (validUntilDay < 10) validUntilDayString = "0" + validUntilDay
    else validUntilDayString = validUntilDay.toString()

    var id = Number(this._route.snapshot.paramMap.get('id'));
    var body = {
      additionalServices: this.newActionServices,
      discount: this.discount,
      numberOfDays: this.numberOfDays,
      numberOfPeople: this.numberOfPeople,
      startDate: this.start.getFullYear() + "-" + startMonthString + "-" + startDayString + "T00:00:00+0" + (-this.start.getTimezoneOffset() / 60) + ":00",
      validUntilAndIncluding: this.validUntil.getFullYear() + "-" + validUntilMonthString + "-" + validUntilDayString + "T00:00:00+0" + (-this.validUntil.getTimezoneOffset() / 60) + ":00",
      ownerNeeded: this.ownerNeeded
    }
    this._boatService.addAction(id, body).subscribe({
      next: data => {
        if (data.status === 200) {
          alert("Action added")
        }
        this.router.navigate(['boat/' + id]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if (data.status === 200) {
          alert(data.error.text)
          this.router.navigate(['boat/' + id]).then(() => {
            window.location.reload();
          });
        }
        else {
          alert(data.error)
        }
      }
    })
  }

  AddService(){
    if(this.selectedNewService == undefined)return;
    let exists = false;
    for(let i=0;i<this.newActionServices.length;i++){
      if(this.selectedNewService === this.newActionServices[i])exists = true;
    }
    if(!exists)this.newActionServices.push(this.selectedNewService)
    this.UpdateServicesTable();
    console.log(this.newActionServices)
  }

  UpdateServicesTable(){
    this.additionalServicesTableMap = [];
    for(let i=0;i<this.newActionServices.length;i++){
      for(let j=0;j<this.additionalServices.length;j++){
        if(this.newActionServices[i] == this.additionalServices[j].value){
          this.additionalServicesTableMap.push(this.additionalServices[j].viewValue)
        }
      }
    }
  }

  RemoveService(service: any){
    for(var i = 0; i < this.newActionServices.length; i++){
      if(this.additionalServicesTableMap[i] === service){
        this.additionalServicesTableMap.splice(i,1);
        for(let j=0;j<this.additionalServices.length;j++){
          if(this.additionalServices[j].viewValue === service){
            for(let k=0;k<this.newActionServices.length;k++){
              if(this.additionalServices[j].value === this.newActionServices[k]){
                this.newActionServices.splice(k,1);
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
