import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FishingActionGet } from 'src/app/model/fishing-action-get.model';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { FishingReservationGet } from 'src/app/model/fishing-reservation-get.model';
import { FishingTripGet } from 'src/app/model/fishing-trip-get';
import { LoyaltyProgramProvider } from 'src/app/model/loyalty-program-provider.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { FishingTripService } from 'src/app/service/fishing-trip.service';
import { LoyaltyProgramService } from 'src/app/service/loyalty-program.service';

@Component({
  selector: 'app-fishing-instructor',
  templateUrl: './fishing-instructor.component.html',
  styleUrls: ['./fishing-instructor.component.scss']
})
export class FishingInstructorComponent implements OnInit {
    displayedColumnsFishingTrips: string[] = ['name', 'equipment', 'max_people', 'cost_per_day', 'price_kept_if_reservation_cancelled', 'location', 'reservation_tags', 'grade'];
    allInstructorFishingTrips: FishingTripGet[];
    dataSourceFishingTrips: FishingTripGet[];
    clickedRowFishingTrips: FishingTripGet;
    searchText: string;
    displayedColumnsReservations: string[] = ['start', 'end', 'number_of_people', 'price', 'system_tax', 'reservation_tags', 'fishing_trip', 'client', 'client_came'];
    dataSourceReservations: FishingReservationGet[];
    clickedRowReservations: FishingReservationGet;
    displayedColumnsActions: string[] = ['start', 'end', 'valid_until', 'max_people', 'price', 'system_tax', 'location', 'action_tags', 'fishing_trip', 'client', 'client_came'];
    dataSourceActions: FishingActionGet[];
    clickedRowActions: FishingActionGet;

    constructor(private router: Router, private fishingTripService: FishingTripService) {
        this.fishingTripService.getFishingInstructorFishingTrips().subscribe(data => {
            this.allInstructorFishingTrips = data;
            this.allInstructorFishingTrips.forEach(function (fishingTrip) {
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

            this.dataSourceFishingTrips = this.allInstructorFishingTrips;
        });

        this.clickedRowFishingTrips = new FishingTripGet();

        this.fishingTripService.getFishingInstructorReservations().subscribe(data => {
            this.dataSourceReservations = data;
            this.dataSourceReservations.forEach(function (instructorReservation) {
                instructorReservation.end = new Date();
                instructorReservation.end.setDate(new Date(instructorReservation.start).getDate());
                instructorReservation.end.setMonth(new Date(instructorReservation.start).getMonth());
                instructorReservation.end.setFullYear(new Date(instructorReservation.start).getFullYear());
                instructorReservation.end.setDate(instructorReservation.end.getDate() + instructorReservation.durationInDays - 1);
            }); 
        });

        this.clickedRowReservations = new FishingReservationGet();

        this.fishingTripService.getFishingInstructorActions().subscribe(data => {
            this.dataSourceActions = data;
            this.dataSourceActions.forEach(function (instructorAction) {
                instructorAction.end = new Date();
                instructorAction.end.setDate(new Date(instructorAction.start).getDate());
                instructorAction.end.setMonth(new Date(instructorAction.start).getMonth());
                instructorAction.end.setFullYear(new Date(instructorAction.start).getFullYear());
                instructorAction.end.setDate(instructorAction.end.getDate() + instructorAction.durationInDays - 1);
            }); 
        });

        this.clickedRowActions = new FishingActionGet();
    }

    ngOnInit(): void {
    }

    public updateClickedRowFishingTrips(row: FishingTripGet): void {
        if (this.clickedRowFishingTrips.id === row.id) {
            this.clickedRowFishingTrips = new FishingTripGet(); 
        } else {
            this.clickedRowFishingTrips = row;
        }
    }
    
    public searchFishingTrips(): void {
        if (this.searchText !== undefined && this.searchText !== "") {
            this.clickedRowFishingTrips = new FishingTripGet();

            this.fishingTripService.getSearchedFishingTrips(this.searchText).subscribe(data => {
                this.dataSourceFishingTrips = data;
            });
        } else {
            this.dataSourceFishingTrips = this.allInstructorFishingTrips;
        }
    }

    public updateClickedRowReservations(row: FishingReservationGet): void {
        if (this.clickedRowReservations.id === row.id) {
            this.clickedRowReservations = new FishingReservationGet(); 
        } else {
            this.clickedRowReservations = row;
        }
    }

    public updateClickedRowActions(row: FishingActionGet): void {
        if (this.clickedRowActions.id === row.id) {
            this.clickedRowActions = new FishingActionGet(); 
        } else {
            this.clickedRowActions = row;
        }
    }

    public goToFishingTrip(row: FishingTripGet): void {
        this.router.navigate(['fishing-trip/' + row.id]).then(() => {
            window.location.reload();
        });
    }

    public goToEditFishingTripPictures(): void {
        this.router.navigate(['edit-fishing-trip-pictures/' + this.clickedRowFishingTrips.id]).then(() => {
            window.location.reload();
        });
    }
}
