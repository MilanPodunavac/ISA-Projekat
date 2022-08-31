import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { FishingActionGet } from '../model/fishing-action-get.model';
import { FishingTripGet } from '../model/fishing-trip-get';
import { FishingTripService } from '../service/fishing-trip.service';

@Component({
  selector: 'app-fishing-trip-profile',
  templateUrl: './fishing-trip-profile.component.html',
  styleUrls: ['./fishing-trip-profile.component.scss']
})
export class FishingTripProfileComponent implements OnInit {
  displayedColumnsFreeActions: string[] = ['start', 'end', 'valid_until', 'max_people', 'price', 'location', 'action_tags'];
  dataSourceFreeActions: FishingActionGet[];
  fishingTrip: FishingTripGet;

  constructor(private _route: ActivatedRoute, private fishingTripService: FishingTripService, private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.fishingTripService.getFishingTrip(id).subscribe(data => {
      this.fishingTrip = data;

      for(let i = 0; i < this.fishingTrip.pictures.length; i++){
        this.fishingTrip.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.fishingTrip.pictures[i].data);
      }
    });

    this.fishingTripService.getFishingTripFreeActions(id).subscribe(data => {
      this.dataSourceFreeActions = data;

      this.dataSourceFreeActions.forEach(function (freeAction) {
        freeAction.end = new Date();
        freeAction.end.setDate(new Date(freeAction.start).getDate());
        freeAction.end.setMonth(new Date(freeAction.start).getMonth());
        freeAction.end.setFullYear(new Date(freeAction.start).getFullYear());
        freeAction.end.setDate(freeAction.end.getDate() + freeAction.durationInDays - 1);
      }); 
    });
  }
}
