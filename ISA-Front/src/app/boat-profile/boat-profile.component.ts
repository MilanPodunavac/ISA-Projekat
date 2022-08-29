import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BoatGet } from '../model/boat-get';
import { BoatService } from '../service/boat.service';

@Component({
  selector: 'app-boat-profile',
  templateUrl: './boat-profile.component.html',
  styleUrls: ['./boat-profile.component.scss']
})
export class BoatProfileComponent implements OnInit {

  boat: BoatGet;

  constructor(private _route: ActivatedRoute, private boatService: BoatService) {}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.boatService.getBoat(id).subscribe(data => {
      this.boat = data;
    });

  }

}
