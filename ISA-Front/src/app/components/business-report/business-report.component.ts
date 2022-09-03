import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Chart, registerables } from 'chart.js';
import { FishingTripGet } from 'src/app/model/fishing-trip-get';
import { PeriodicalReservationsGet } from 'src/app/model/periodical-reservations-get.model';
import { ProfitInInterval } from 'src/app/model/profit-in-interval.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { FishingTripService } from 'src/app/service/fishing-trip.service';

@Component({
    selector: 'app-business-report',
    templateUrl: './business-report.component.html',
    styleUrls: ['./business-report.component.scss']
})
export class BusinessReportComponent implements OnInit {
    displayedColumnsFishingTrips: string[] = ['name', 'grade'];
    dataSourceFishingTrips: FishingTripGet[];
    profitForm: FormGroup;
    profitInInterval: number;
    displayedColumnsReservations: string[] = ['start', 'numberOfReservations'];
    dataSourceWeekly: PeriodicalReservationsGet[];
    dataSourceMonthly: PeriodicalReservationsGet[];
    dataSourceYearly: PeriodicalReservationsGet[];
    yearChart: Chart
    monthChart: Chart
    weekChart: Chart

    constructor(formBuilder: FormBuilder, private router: Router, private fishingTripService: FishingTripService, private fishingInstructorService: FishingInstructorService) {
        Chart.register(...registerables)
        this.profitForm = formBuilder.group({
            from: ['', [Validators.required]],
            to: ['', [Validators.required]]
        });
        
        this.profitInInterval = 0;

        this.fishingTripService.getFishingInstructorFishingTrips().subscribe(data => {
            this.dataSourceFishingTrips = data;
            this.dataSourceFishingTrips.forEach(function (fishingTrip) {
                for (let i = 0; i < fishingTrip.reviews.length; i++) {
                    if (!fishingTrip.reviews[i].approved) {
                        fishingTrip.reviews.splice(i, 1);
                    }
                }

                fishingTrip.grade = 0;
                for (let i = 0; i < fishingTrip.reviews.length; i++) {
                    fishingTrip.grade += fishingTrip.reviews[i].grade;
                }
                fishingTrip.grade /= fishingTrip.reviews.length;
            });
        });

        this.fishingInstructorService.weeklyReservations().subscribe(data => {
            this.dataSourceWeekly = data;
            var labels = [];
            var visits = [];
            for(let i=0; i<this.dataSourceWeekly.length; i++){
                labels.push(this.dataSourceWeekly[i].start)
                visits.push(this.dataSourceWeekly[i].numOfReservations)
            }
            this.weekChart = new Chart("weekChartCanv", {
                type: 'line',
                data: {
                  labels: labels,
                  datasets: [{
                    label: 'Visits in last 7 days',
                    data: visits,
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
        });

        this.fishingInstructorService.monthlyReservations().subscribe(data => {
            this.dataSourceMonthly = data;
            var labels = [];
            var visits = [];
            for(let i=0; i<this.dataSourceMonthly.length; i++){
                labels.push(this.dataSourceMonthly[i].start)
                visits.push(this.dataSourceMonthly[i].numOfReservations)
            }
            visits[0] = 52
            visits[1] = 32
            visits[2] = 150
            visits[3] = 3
            this.monthChart = new Chart("monthChartCanv", {
                type: 'line',
                data: {
                  labels: labels,
                  datasets: [{
                    label: 'Visits in last 4 weeks',
                    data: visits,
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
        });

        this.fishingInstructorService.yearlyReservations().subscribe(data => {
            this.dataSourceYearly = data;
            console.log(this.dataSourceYearly)
            var labels = [];
            var visits = [];
            for(let i=0; i<this.dataSourceYearly.length; i++){
                labels.push(this.dataSourceYearly[i].start)
                visits.push(this.dataSourceYearly[i].numOfReservations)
            }
            this.yearChart = new Chart("yearChartCanv", {
                type: 'line',
                data: {
                  labels: labels,
                  datasets: [{
                    label: 'Visits in last 12 months',
                    data: visits,
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
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let incomeInTimeIntervalRequest = new ProfitInInterval();
        incomeInTimeIntervalRequest.from = this.profitForm.get('from').value;
        incomeInTimeIntervalRequest.to = this.profitForm.get('to').value;
        this.fishingInstructorService.incomeInTimeInterval(incomeInTimeIntervalRequest).subscribe(data => {
            this.profitInInterval = Number(data);
        });
    }   
}
