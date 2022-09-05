import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BoatGet } from '../model/boat-get';
import { BoatService } from '../service/boat.service';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';
import { DomSanitizer } from '@angular/platform-browser';
import { CalendarOptions, DateSelectArg } from '@fullcalendar/angular';
import { DatePipe } from '@angular/common';
import { CalendarEvent } from '../model/calendar-event.model';

@Component({
  selector: 'app-boat-profile',
  templateUrl: './boat-profile.component.html',
  styleUrls: ['./boat-profile.component.scss']
})
export class BoatProfileComponent implements OnInit {
  role: string;
  boat: BoatGet;
  map: Map;
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
    const calendarApi = selectInfo.view.calendar;

    calendarApi.unselect(); 

    let period: any = new Object();
    period.startDate = new Date(selectInfo.startStr);
    let end = new Date();
    end.setDate(new Date(new Date(selectInfo.endStr)).getDate());
    end.setMonth(new Date(new Date(selectInfo.endStr)).getMonth());
    end.setFullYear(new Date(new Date(selectInfo.endStr)).getFullYear());
    end.setDate(end.getDate() - 1);
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

    this.boatService.addAvailabilityPeriod(body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Availability period added")
        }
        this.router.navigate(['boat', id]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['boat', id]).then(() => {
            window.location.reload();
          });
        }
        else{
          alert(data.error)
        }     
      }
    })
  }

  constructor(private datePipe: DatePipe, private _route: ActivatedRoute, private boatService: BoatService, private sanitizer: DomSanitizer, private router: Router) {this.role = localStorage.getItem('role')}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.boatService.getBoat(id).subscribe(data => {
      this.boat = data;
      let availablePeriodsCalendar : CalendarEvent[] = []
      for(let i = 0; i<this.boat.pictures.length; i++){
        this.boat.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.boat.pictures[i].data);
      }
      for(let i = 0; i<this.boat.availabilityPeriods.length; i++){
        var tempStart = new Date(this.boat.availabilityPeriods[i].startDate)
        var tempEnd = new Date(this.boat.availabilityPeriods[i].endDate)
        //this.boat.availabilityPeriods[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.boat.availabilityPeriods[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        let availablePeriodCalendar = new CalendarEvent();
        let end = new Date();
        end.setDate(new Date(tempEnd).getDate());
        end.setMonth(new Date(tempEnd).getMonth());
        end.setFullYear(new Date(tempEnd).getFullYear());
        end.setDate(end.getDate());
        end.setDate(end.getDate() + 1);
        availablePeriodCalendar.start = this.datePipe.transform(tempStart, "yyyy-MM-dd");
        availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
        availablePeriodCalendar.display = 'background';
        availablePeriodCalendar.overlap = false;
        availablePeriodsCalendar.push(availablePeriodCalendar);
      }
      for(let i = 0; i<this.boat.boatReservations.length; i++){
        var tempStart = new Date(this.boat.boatReservations[i].startDate)
        var tempEnd = new Date(this.boat.boatReservations[i].endDate)
        //this.boat.boatReservations[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.boat.boatReservations[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        let availablePeriodCalendar = new CalendarEvent();
        let end = new Date();
        end.setDate(new Date(tempEnd).getDate());
        end.setMonth(new Date(tempEnd).getMonth());
        end.setFullYear(new Date(tempEnd).getFullYear());
        end.setDate(end.getDate());
        availablePeriodCalendar.title = this.boat.boatReservations[i].clientFirstName + " " + this.boat.boatReservations[i].clientLastName;
        availablePeriodCalendar.start = this.datePipe.transform(tempStart, "yyyy-MM-dd");
        availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
        availablePeriodCalendar.color = 'blue';
        availablePeriodCalendar.overlap = false;
        availablePeriodsCalendar.push(availablePeriodCalendar);
      }
      for(let i = 0; i<this.boat.boatActions.length; i++){
        var tempStart = new Date(this.boat.boatActions[i].startDate)
        var tempEnd = new Date(this.boat.boatActions[i].endDate)
        var tempValid = new Date(this.boat.boatActions[i].validUntilAndIncluding)
        //this.boat.boatActions[i].startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.boat.boatActions[i].endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        //this.boat.boatActions[i].validUntilAndIncluding = tempValid.getFullYear() + "-" + (tempValid.getMonth() + 1)// + "-" + tempValid.getDate() + ":" + tempValid.getHours()
        let availablePeriodCalendar = new CalendarEvent();
        let end = new Date();
        end.setDate(new Date(tempEnd).getDate());
        end.setMonth(new Date(tempEnd).getMonth());
        end.setFullYear(new Date(tempEnd).getFullYear());
        end.setDate(end.getDate());
        if (this.boat.boatActions[i].clientFirstName) {
          availablePeriodCalendar.title = this.boat.boatActions[i].clientFirstName + " " + this.boat.boatActions[i].clientLastName;
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
          center: olProj.fromLonLat([this.boat.location.longitude, this.boat.location.latitude]),
          zoom: 14, maxZoom: 20,
        }),
      });
    });
    console.log(this.boat)
  }
  updateBoat(){
    this.router.navigate(['/boat-owner/boat/', this.boat.id]).then(() => {
      window.location.reload();
    });
  }
  addAvailabiltiyPeriod(){
    this.router.navigate(['/boat/', this.boat.id, `new-availability-period`]).then(() => {
      window.location.reload();
    });
  }
  addReservation(){
    this.router.navigate(['/boat/', this.boat.id, `new-boat-reservation`]).then(() => {
      window.location.reload();
    });
  }
  doubleClickResFunction(resId: number){
    if(this.role !== "ROLE_BOAT_OWNER")return
    this.router.navigate(['boat', this.boat.id, 'reservation', resId]).then(() => {
      window.location.reload();
    });
  }
  addAction(){
    this.router.navigate(['/boat/', this.boat.id, `new-boat-action`]).then(() => {
      window.location.reload();
    });
  }
  doubleClickActFunction(actId: number){
    if(this.role !== "ROLE_BOAT_OWNER")return
    this.router.navigate(['boat', this.boat.id, 'action', actId]).then(() => {
      window.location.reload();
    });
  }
  deleteBoat(){
    if(confirm("Are you sure to delete this boat?")) {
      this.boatService.deleteBoat(this.boat.id).subscribe({
        next: data =>{
          if(data.status === 200){
            alert("Boat deleted")
          }
          this.router.navigate(['boat']).then(() => {
            window.location.reload();
          });
        },
        error: data => {
          console.log(data)
          if(data.status === 200){
            alert(data.error.text)
            this.router.navigate(['boat']).then(() => {
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
