import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Chart, registerables } from 'chart.js';
import { FormattedValuesFilteringStrategy } from 'igniteui-angular';
import { BoatService } from 'src/app/service/boat.service';
import { CottageService } from 'src/app/service/cottage.service';

@Component({
  selector: 'app-visit-report',
  templateUrl: './visit-report.component.html',
  styleUrls: ['./visit-report.component.scss']
})
export class VisitReportComponent implements OnInit {
  report: any
  role: string
  yearChart: Chart
  monthChart: Chart
  weekChart: Chart

  constructor(private _boatService: BoatService, private router: Router, private _cottageService: CottageService, private _route: ActivatedRoute) {this.role = localStorage.getItem('role')}

  ngOnInit(): void {
    Chart.register(...registerables)
    if(this.role === "ROLE_COTTAGE_OWNER"){
      this._cottageService.getVisitReport(Number(this._route.snapshot.paramMap.get('id'))).subscribe({
        next: data =>{
          this.report = data
          console.log(this.report)
          this.formatDates()
          //GODISNJI
          this.yearChart = new Chart("yearChartCanv", {
            type: 'line',
            data: {
              labels: [this.report.yearlyVisitReport.first.start, this.report.yearlyVisitReport.second.start,
                this.report.yearlyVisitReport.third.start, this.report.yearlyVisitReport.fourth.start,
                this.report.yearlyVisitReport.fifth.start, this.report.yearlyVisitReport.sixth.start,
                this.report.yearlyVisitReport.seventh.start, this.report.yearlyVisitReport.eighth.start,
                this.report.yearlyVisitReport.ninth.start, this.report.yearlyVisitReport.tenth.start,
                this.report.yearlyVisitReport.eleventh.start, this.report.yearlyVisitReport.twelfth.start],
              datasets: [{
                label: 'Visits in last 12 months',
                data: [this.report.yearlyVisitReport.first.visitNumber, this.report.yearlyVisitReport.second.visitNumber, this.report.yearlyVisitReport.third.visitNumber,
                  this.report.yearlyVisitReport.fourth.visitNumber, this.report.yearlyVisitReport.fifth.visitNumber, this.report.yearlyVisitReport.sixth.visitNumber,
                  this.report.yearlyVisitReport.seventh.visitNumber, this.report.yearlyVisitReport.eighth.visitNumber, this.report.yearlyVisitReport.ninth.visitNumber,
                  this.report.yearlyVisitReport.tenth.visitNumber, this.report.yearlyVisitReport.eleventh.visitNumber, this.report.yearlyVisitReport.twelfth.visitNumber],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)',
                    'rgba(255, 159, 64, 0.8)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
            },
          })
          //MESECNI
          this.monthChart = new Chart("monthChartCanv", {
            type: 'line',
            data: {
              labels: [this.report.monthlyVisitReport.first.start, this.report.monthlyVisitReport.second.start,
                this.report.monthlyVisitReport.third.start, this.report.monthlyVisitReport.fourth.start,],
              datasets: [{
                label: 'Visits in last 4 weeks',
                data: [this.report.monthlyVisitReport.first.visitNumber, this.report.monthlyVisitReport.second.visitNumber, this.report.monthlyVisitReport.third.visitNumber,
                  this.report.monthlyVisitReport.fourth.visitNumber],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                ],
                borderWidth: 1
            }]
            },
          })
          //NEDELJNI
          this.weekChart = new Chart("weekChartCanv", {
            type: 'line',
            data: {
              labels: [this.report.weeklyVisitReport.first.start, this.report.weeklyVisitReport.second.start,
                this.report.weeklyVisitReport.third.start, this.report.weeklyVisitReport.fourth.start,
                this.report.weeklyVisitReport.fifth.start, this.report.weeklyVisitReport.sixth.start,
                this.report.weeklyVisitReport.seventh.start],
              datasets: [{
                label: 'Visits in last 7 days',
                data: [this.report.weeklyVisitReport.first.visitNumber, this.report.weeklyVisitReport.second.visitNumber, this.report.weeklyVisitReport.third.visitNumber,
                  this.report.weeklyVisitReport.fourth.visitNumber, this.report.weeklyVisitReport.fifth.visitNumber, this.report.weeklyVisitReport.sixth.visitNumber,
                  this.report.weeklyVisitReport.seventh.visitNumber],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)',
                    'rgba(255, 159, 64, 0.8)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
            },
          })
        }
      })
    }
    if(this.role === "ROLE_BOAT_OWNER"){
      this._boatService.getVisitReport(Number(this._route.snapshot.paramMap.get('id'))).subscribe({
        next: data =>{
          this.report = data
          console.log(this.report)
          this.formatDates()
          //GODISNJI
          this.yearChart = new Chart("yearChartCanv", {
            type: 'line',
            data: {
              labels: [this.report.yearlyVisitReport.first.start, this.report.yearlyVisitReport.second.start,
                this.report.yearlyVisitReport.third.start, this.report.yearlyVisitReport.fourth.start,
                this.report.yearlyVisitReport.fifth.start, this.report.yearlyVisitReport.sixth.start,
                this.report.yearlyVisitReport.seventh.start, this.report.yearlyVisitReport.eighth.start,
                this.report.yearlyVisitReport.ninth.start, this.report.yearlyVisitReport.tenth.start,
                this.report.yearlyVisitReport.eleventh.start, this.report.yearlyVisitReport.twelfth.start],
              datasets: [{
                label: 'Visits in last 12 months',
                data: [this.report.yearlyVisitReport.first.visitNumber, this.report.yearlyVisitReport.second.visitNumber, this.report.yearlyVisitReport.third.visitNumber,
                  this.report.yearlyVisitReport.fourth.visitNumber, this.report.yearlyVisitReport.fifth.visitNumber, this.report.yearlyVisitReport.sixth.visitNumber,
                  this.report.yearlyVisitReport.seventh.visitNumber, this.report.yearlyVisitReport.eighth.visitNumber, this.report.yearlyVisitReport.ninth.visitNumber,
                  this.report.yearlyVisitReport.tenth.visitNumber, this.report.yearlyVisitReport.eleventh.visitNumber, this.report.yearlyVisitReport.twelfth.visitNumber],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)',
                    'rgba(255, 159, 64, 0.8)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
            },
          })
          //MESECNI
          this.monthChart = new Chart("monthChartCanv", {
            type: 'line',
            data: {
              labels: [this.report.monthlyVisitReport.first.start, this.report.monthlyVisitReport.second.start,
                this.report.monthlyVisitReport.third.start, this.report.monthlyVisitReport.fourth.start,],
              datasets: [{
                label: 'Visits in last 4 weeks',
                data: [this.report.monthlyVisitReport.first.visitNumber, this.report.monthlyVisitReport.second.visitNumber, this.report.monthlyVisitReport.third.visitNumber,
                  this.report.monthlyVisitReport.fourth.visitNumber],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                ],
                borderWidth: 1
            }]
            },
          })
          //NEDELJNI
          this.weekChart = new Chart("weekChartCanv", {
            type: 'line',
            data: {
              labels: [this.report.weeklyVisitReport.first.start, this.report.weeklyVisitReport.second.start,
                this.report.weeklyVisitReport.third.start, this.report.weeklyVisitReport.fourth.start,
                this.report.weeklyVisitReport.fifth.start, this.report.weeklyVisitReport.sixth.start,
                this.report.weeklyVisitReport.seventh.start],
              datasets: [{
                label: 'Visits in last 7 days',
                data: [this.report.weeklyVisitReport.first.visitNumber, this.report.weeklyVisitReport.second.visitNumber, this.report.weeklyVisitReport.third.visitNumber,
                  this.report.weeklyVisitReport.fourth.visitNumber, this.report.weeklyVisitReport.fifth.visitNumber, this.report.weeklyVisitReport.sixth.visitNumber,
                  this.report.weeklyVisitReport.seventh.visitNumber],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)',
                    'rgba(255, 159, 64, 0.8)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
            },
          })
        }
      })
    }
  }
  formatDates() {
    var temp = new Date(this.report.yearlyVisitReport.first.start)
    this.report.yearlyVisitReport.first.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.second.start)
    this.report.yearlyVisitReport.second.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.third.start)
    this.report.yearlyVisitReport.third.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.fourth.start)
    this.report.yearlyVisitReport.fourth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.fifth.start)
    this.report.yearlyVisitReport.fifth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.sixth.start)
    this.report.yearlyVisitReport.sixth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.seventh.start)
    this.report.yearlyVisitReport.seventh.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.eighth.start)
    this.report.yearlyVisitReport.eighth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.ninth.start)
    this.report.yearlyVisitReport.ninth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.tenth.start)
    this.report.yearlyVisitReport.tenth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.eleventh.start)
    this.report.yearlyVisitReport.eleventh.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)
  
    var temp = new Date(this.report.yearlyVisitReport.twelfth.start)
    this.report.yearlyVisitReport.twelfth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)


    var temp = new Date(this.report.monthlyVisitReport.first.start)
    this.report.monthlyVisitReport.first.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.monthlyVisitReport.second.start)
    this.report.monthlyVisitReport.second.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.monthlyVisitReport.third.start)
    this.report.monthlyVisitReport.third.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.monthlyVisitReport.fourth.start)
    this.report.monthlyVisitReport.fourth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))


    var temp = new Date(this.report.weeklyVisitReport.first.start)
    this.report.weeklyVisitReport.first.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.weeklyVisitReport.second.start)
    this.report.weeklyVisitReport.second.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.weeklyVisitReport.third.start)
    this.report.weeklyVisitReport.third.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.weeklyVisitReport.fourth.start)
    this.report.weeklyVisitReport.fourth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.weeklyVisitReport.fifth.start)
    this.report.weeklyVisitReport.fifth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.weeklyVisitReport.sixth.start)
    this.report.weeklyVisitReport.sixth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  
    var temp = new Date(this.report.weeklyVisitReport.seventh.start)
    this.report.weeklyVisitReport.seventh.start = temp.getFullYear() + "-" + (temp.getMonth() + 1 + "-" + (temp.getDate()))
  }
}

