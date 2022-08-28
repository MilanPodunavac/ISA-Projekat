import { Component, OnInit } from '@angular/core';
import { FishingInstructorFishingTripTableGet } from 'src/app/model/fishing-instructor-fishing-trip-table-get.model';
import { FishingInstructorProfileDataGet } from 'src/app/model/fishing-instructor-profile-data-get.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { FishingTripService } from 'src/app/service/fishing-trip.service';

@Component({
  selector: 'app-fishing-instructor',
  templateUrl: './fishing-instructor.component.html',
  styleUrls: ['./fishing-instructor.component.scss']
})
export class FishingInstructorComponent implements OnInit {
    fishingInstructorProfileData: FishingInstructorProfileDataGet;
    displayedColumnsFishingTrips: string[] = ['name', 'max_people', 'cost_per_day', 'address', 'city', 'country'];
    dataSourceFishingTrips: FishingInstructorFishingTripTableGet[];

    constructor(private fishingInstructorService: FishingInstructorService, private fishingTripService: FishingTripService) {
        this.fishingInstructorService.getProfileData().subscribe(data => {
            this.fishingInstructorProfileData = data;
        });

        this.fishingTripService.getFishingInstructorFishingTrips().subscribe(data => {
            this.dataSourceFishingTrips = data;
        });
    }

    ngOnInit(): void {
    }

}
