import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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

    constructor(formBuilder: FormBuilder, private router: Router, private fishingTripService: FishingTripService, private fishingInstructorService: FishingInstructorService) {
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
        });

        this.fishingInstructorService.monthlyReservations().subscribe(data => {
            this.dataSourceMonthly = data;
        });

        this.fishingInstructorService.yearlyReservations().subscribe(data => {
            this.dataSourceYearly = data;
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
