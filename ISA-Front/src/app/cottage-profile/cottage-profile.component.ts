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
import { CalendarOptions, DateSelectArg } from '@fullcalendar/angular';
import { CalendarEvent } from '../model/calendar-event.model';
import { DatePipe } from '@angular/common';
import { ClientService } from '../service/client.service';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['./cottage-profile.component.scss']
})
export class CottageProfileComponent implements OnInit {
  role: string;
  cottage: CottageGet;
  map: Map;
  canAddReview: boolean;
  canAddComplaint: boolean;
  calendarOptions: CalendarOptions = {
    headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek'
    },
    initialView: 'dayGridMonth',
    selectable: true,
    select: this.handleDateSelect.bind(this)
  };

  handleDateSelect(selectInfo: DateSelectArg) {
    if(this.role != "ROLE_COTTAGE_OWNER") return;
    const calendarApi = selectInfo.view.calendar;

    calendarApi.unselect(); 

    let period: any = new Object();
    period.startDate = new Date(selectInfo.startStr);
    let end = new Date();
    end.setDate(new Date(new Date(selectInfo.endStr)).getDate());
    end.setMonth(new Date(new Date(selectInfo.endStr)).getMonth());
    end.setFullYear(new Date(new Date(selectInfo.endStr)).getFullYear());
    end.setDate(end.getDate());
    period.endDate = end;
    let id = Number(this._route.snapshot.paramMap.get('id'));
    period.saleEntityId = id;

    var startMonth =  period.startDate.getMonth() + 1
    var startMonthString: String;
    if(startMonth < 10)startMonthString = "0" + startMonth 
    else startMonthString = startMonth.toString()

    var startDay =  period.startDate.getDate()
    var startDayString: String;
    if(startDay < 10)startDayString = "0" + startDay 
    else startDayString = startDay.toString()

    var endMonth =  period.endDate.getMonth() + 1
    var endMonthString: String;
    if(endMonth < 10)endMonthString = "0" + endMonth 
    else endMonthString = endMonth.toString()

    var endDay =  period.endDate.getDate()
    var endDayString: String;
    if(endDay < 10)endDayString = "0" + endDay 
    else endDayString = endDay.toString()

    var body = {
      endDate : period.endDate.getFullYear() + "-" + endMonthString + "-" + endDayString + "T00:00:00+0" + (-period.endDate.getTimezoneOffset()/60) + ":00",
      saleEntityId: period.saleEntityId,
      startDate: period.startDate.getFullYear() + "-" + startMonthString + "-" + startDayString + "T00:00:00+0" + (-period.startDate.getTimezoneOffset()/60) + ":00"
    }

    this.cottageService.addAvailabilityPeriod(body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Availability period added")
        }
        this.router.navigate(['cottage', id]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['cottage', id]).then(() => {
            window.location.reload();
          });
        }
        else{
          alert(data.error)
        }     
      }
    })
  }

  constructor(private datePipe: DatePipe, private _route: ActivatedRoute, private cottageService: CottageService, private sanitizer: DomSanitizer, private router: Router, private _clientService: ClientService) {this.role = localStorage.getItem('role');}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.cottageService.getCottage(id).subscribe(data => {
      this.cottage = data;
      let availablePeriodsCalendar : CalendarEvent[] = []
      for(let i = 0; i<this.cottage.pictures.length; i++){
        this.cottage.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.cottage.pictures[i].data);
      }
      for(let i = 0; i<this.cottage.availabilityPeriods.length; i++){
        var tempStart = new Date(this.cottage.availabilityPeriods[i].startDate)
        var tempEnd = new Date(this.cottage.availabilityPeriods[i].endDate)
        //this.cottage.availabilityPeriods[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.cottage.availabilityPeriods[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        let availablePeriodCalendar = new CalendarEvent();
        let end = new Date();
        end.setDate(new Date(tempEnd).getDate());
        end.setMonth(new Date(tempEnd).getMonth());
        end.setFullYear(new Date(tempEnd).getFullYear());
        end.setDate(end.getDate() + 1);
        availablePeriodCalendar.start = this.datePipe.transform(tempStart, "yyyy-MM-dd");
        availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
        availablePeriodCalendar.display = 'background';
        availablePeriodCalendar.overlap = false;
        availablePeriodsCalendar.push(availablePeriodCalendar);
      }
      for(let i = 0; i<this.cottage.cottageReservations.length; i++){
        var tempStart = new Date(this.cottage.cottageReservations[i].startDate)
        var tempEnd = new Date(this.cottage.cottageReservations[i].endDate)
        //this.cottage.cottageReservations[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.cottage.cottageReservations[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        let availablePeriodCalendar = new CalendarEvent();
        let end = new Date();
        end.setDate(new Date(tempEnd).getDate());
        end.setMonth(new Date(tempEnd).getMonth());
        end.setFullYear(new Date(tempEnd).getFullYear());
        end.setDate(end.getDate());
        availablePeriodCalendar.title = this.cottage.cottageReservations[i].clientFullName;
        availablePeriodCalendar.start = this.datePipe.transform(tempStart, "yyyy-MM-dd");
        availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
        availablePeriodCalendar.color = 'blue';
        availablePeriodCalendar.overlap = false;
        availablePeriodsCalendar.push(availablePeriodCalendar);
      }
      for(let i = 0; i<this.cottage.cottageActions.length; i++){
        var tempStart = new Date(this.cottage.cottageActions[i].startDate)
        var tempEnd = new Date(this.cottage.cottageActions[i].endDate)
        var tempValid = new Date(this.cottage.cottageActions[i].validUntilAndIncluding)
        //this.cottage.cottageActions[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.cottage.cottageActions[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        //this.cottage.cottageActions[i].validUntilAndIncluding = tempValid.getFullYear() + "-" + (tempValid.getMonth() + 1)// + "-" + tempValid.getDate() + ":" + tempValid.getHours()
        let availablePeriodCalendar = new CalendarEvent();
        let end = new Date();
        end.setDate(new Date(tempEnd).getDate());
        end.setMonth(new Date(tempEnd).getMonth());
        end.setFullYear(new Date(tempEnd).getFullYear());
        end.setDate(end.getDate());
        if (this.cottage.cottageActions[i].clientFullName) {
          availablePeriodCalendar.title = this.cottage.cottageActions[i].clientFullName;
        }
        availablePeriodCalendar.start = this.datePipe.transform(tempStart, "yyyy-MM-dd");
        availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
        availablePeriodCalendar.color = 'red';
        availablePeriodCalendar.overlap = false;
        availablePeriodsCalendar.push(availablePeriodCalendar);
      }
      this.calendarOptions.events = availablePeriodsCalendar;
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
      console.log(this.cottage)
      if(this.role ==='ROLE_CLIENT'){
        this._clientService.getLoggedInClient().subscribe({
          next: data => {
            if(this.cottage.cottageReservations.filter(e => e.clientId === data.id && e.endDate < new Date()).length > 0){
              this.canAddReview = true
            }
            if(this.cottage.cottageReservations.filter(e => e.clientId === data.id && e.endDate < new Date()).length > 0){
              this.canAddComplaint = true
            }
          }
        })
      }
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
  addReview(){
    this.router.navigate(['cottage/'+this.cottage.id+'/new-cottage-review'])
  }
  addComplaint(){
    this.router.navigate(['cottage/'+this.cottage.id+'/new-cottage-complaint'])
  }
}
