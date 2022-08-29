import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CottageService } from 'src/app/service/cottage.service';

@Component({
  selector: 'app-new-cottage-availability-period',
  templateUrl: './new-cottage-availability-period.component.html',
  styleUrls: ['./new-cottage-availability-period.component.scss']
})
export class NewCottageAvailabilityPeriodComponent implements OnInit {
  start: Date
  end: Date
  name: string
  constructor(private _cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this._cottageService.getCottage(Number(this._route.snapshot.paramMap.get('id'))).subscribe({
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
    if(this.start === undefined || this.end === undefined){
      alert("Select date range")
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

    var endMonth =  this.end.getMonth() + 1
    var endMonthString: String;
    if(endMonth < 10)endMonthString = "0" + endMonth 
    else endMonthString = endMonth.toString()

    var endDay =  this.end.getDate()
    var endDayString: String;
    if(endDay < 10)endDayString = "0" + endDay 
    else endDayString = endDay.toString()

    var id = Number(this._route.snapshot.paramMap.get('id'));
    var body = {
      endDate: this.end.getFullYear() + "-" + endMonthString + "-" + endDayString + "T00:00:00+0" + (-this.end.getTimezoneOffset()/60) + ":00",
      saleEntityId: id,
      startDate: this.start.getFullYear() + "-" + startMonthString + "-" + startDayString + "T00:00:00+0" + (-this.start.getTimezoneOffset()/60) + ":00"
    }
    this._cottageService.addAvailabilityPeriod(body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Availability period added")
        }
        this.router.navigate(['cottage-owner/cottage/' + id]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['cottage-owner/cottage/' + id]).then(() => {
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
