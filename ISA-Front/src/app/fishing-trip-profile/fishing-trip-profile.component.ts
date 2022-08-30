import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FishingTripGet } from '../model/fishing-trip-get';
import { FishingTripService } from '../service/fishing-trip.service';

@Component({
  selector: 'app-fishing-trip-profile',
  templateUrl: './fishing-trip-profile.component.html',
  styleUrls: ['./fishing-trip-profile.component.scss']
})
export class FishingTripProfileComponent implements OnInit {

  fishingTrip: FishingTripGet;

  constructor(private _route: ActivatedRoute, private fishingTripService: FishingTripService) {}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.fishingTripService.getFishingTrip(id).subscribe(data => {
      this.fishingTrip = data;
    });
  }
}
