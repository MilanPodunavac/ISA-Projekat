import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    displayedColumnsFishingTrips: string[] = ['name', 'equipment', 'max_people', 'cost_per_day', 'price_kept_if_reservation_cancelled', 'location', 'reservation_tags'];
    allInstructorFishingTrips: FishingTripGet[];
    dataSourceFishingTrips: FishingTripGet[];
    clickedRowFishingTrips: FishingTripGet;
    searchText: string;
    displayedColumnsReservations: string[] = ['start', 'end', 'number_of_people', 'price', 'system_tax'];
    allInstructorReservations: FishingReservationGet[];
    dataSourceReservations: FishingReservationGet[];
    clickedRowReservations: FishingReservationGet;

    constructor(private fishingTripService: FishingTripService) {
        this.fishingTripService.getFishingInstructorFishingTrips().subscribe(data => {
            this.allInstructorFishingTrips = data;
            this.dataSourceFishingTrips = data;
        });

        this.clickedRowFishingTrips = new FishingTripGet();

        this.fishingTripService.getFishingInstructorReservations().subscribe(data => {
            this.allInstructorReservations = data;
            this.allInstructorReservations.forEach(function (instructorReservation) {
                instructorReservation.end = new Date();
                instructorReservation.end.setDate(new Date(instructorReservation.start).getDate());
                instructorReservation.end.setMonth(new Date(instructorReservation.start).getMonth());
                instructorReservation.end.setFullYear(new Date(instructorReservation.start).getFullYear());
                instructorReservation.end.setDate(instructorReservation.end.getDate() + instructorReservation.durationInDays - 1);
            }); 

            this.dataSourceReservations = this.allInstructorReservations;
        });

        this.clickedRowReservations = new FishingReservationGet();
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
}
