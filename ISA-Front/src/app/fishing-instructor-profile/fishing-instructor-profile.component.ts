import { Component, OnInit } from '@angular/core';
import { FishingInstructorProfileDataGet } from '../model/fishing-instructor-profile-data-get.model';
import { FishingInstructorService } from '../service/fishing-instructor.service';
import { FishingTripService } from '../service/fishing-trip.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FishingInstructorGet } from '../model/fishing-instructor-get';
import { FishingInstructorFishingTripTableGet } from '../model/fishing-instructor-fishing-trip-table-get.model';

@Component({
  selector: 'app-fishing-instructor-profile',
  templateUrl: './fishing-instructor-profile.component.html',
  styleUrls: ['./fishing-instructor-profile.component.scss']
})
export class FishingInstructorProfileComponent implements OnInit {
  fishingInstructor: FishingInstructorGet;

  constructor(private _route: ActivatedRoute, private fishingInstructorService: FishingInstructorService, private fishingTripService: FishingTripService, private router: Router) {}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.fishingInstructorService.getFishingInstructor(id).subscribe(data => {
      this.fishingInstructor = data;
    });

  }

  doubleClickFunction(id: any){
    this.router.navigate(['/fishing-trip', id])
  }

}
