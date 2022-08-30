import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { FishingInstructorFishingTripTableGet } from 'src/app/model/fishing-instructor/fishing-instructor-fishing-trip-table-get.model';
import { FishingInstructorProfileDataGet } from 'src/app/model/fishing-instructor/fishing-instructor-profile-data-get.model';
import { ReservationTableGet } from 'src/app/model/fishing-instructor/reservation-table-get.model';
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
    fishingInstructorData: FishingInstructorGet;
    loyaltyProgramCategoryAboveInstructor: LoyaltyProgramProvider;
    displayedColumnsFishingTrips: string[] = ['name', 'max_people', 'cost_per_day', 'address', 'city', 'country'];
    allInstructorFishingTrips: FishingInstructorFishingTripTableGet[];
    dataSourceFishingTrips: FishingInstructorFishingTripTableGet[];
    clickedRowFishingTrips: FishingInstructorFishingTripTableGet;
    searchText: string;
    displayedColumnsReservations: string[] = ['start', 'end', 'number_of_people', 'price', 'system_tax', 'reservation_tags', 'fishing_trip', 'client'];
    allInstructorReservations: ReservationTableGet[];
    dataSourceReservations: ReservationTableGet[];
    clickedRowReservations: ReservationTableGet;

    constructor(private fishingInstructorService: FishingInstructorService, private loyaltyProgramService: LoyaltyProgramService, private fishingTripService: FishingTripService, private _route: ActivatedRoute) {
        this.fishingInstructorService.getLoggedInInstructor().subscribe(data => {
            this.fishingInstructorData = data;

            this.loyaltyProgramService.getOneHigherLoyaltyProviderCategory(this.fishingInstructorData.category.id).subscribe(data => {
                this.loyaltyProgramCategoryAboveInstructor = data;
            });
        });
        
        this.fishingTripService.getFishingInstructorFishingTrips().subscribe(data => {
            this.allInstructorFishingTrips = data;
            this.dataSourceFishingTrips = data;
        });

        this.clickedRowFishingTrips = new FishingInstructorFishingTripTableGet();

        this.fishingTripService.getFishingInstructorReservations().subscribe(data => {
            let id = 1;
            data.forEach(function (value) {
                value.id = id;
                ++id;
            });
            this.allInstructorReservations = data;
            this.dataSourceReservations = data;
        });

        this.clickedRowReservations = new ReservationTableGet();
    }

    ngOnInit(): void {
    }

    public updateClickedRowFishingTrips(row: FishingInstructorFishingTripTableGet): void {
        if (this.clickedRowFishingTrips.id === row.id) {
            this.clickedRowFishingTrips = new FishingInstructorFishingTripTableGet(); 
        } else {
            this.clickedRowFishingTrips = row;
        }
    }
    
    public searchFishingTrips(): void {
        if (this.searchText !== undefined && this.searchText !== "") {
            this.clickedRowFishingTrips = new FishingInstructorFishingTripTableGet();

            this.fishingTripService.getSearchedFishingTrips(this.searchText).subscribe(data => {
                this.dataSourceFishingTrips = data;
            });
        } else {
            this.dataSourceFishingTrips = this.allInstructorFishingTrips;
        }
    }

    public updateClickedRowReservations(row: ReservationTableGet): void {
        if (this.clickedRowReservations.id === row.id) {
            this.clickedRowReservations = new ReservationTableGet(); 
        } else {
            this.clickedRowReservations = row;
        }
    }
}
