import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CottageGet } from '../model/cottage-get';
import { CottageService } from '../service/cottage.service';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['./cottage-profile.component.scss']
})
export class CottageProfileComponent implements OnInit {
  cottage: CottageGet;

  constructor(private _route: ActivatedRoute, private cottageService: CottageService) {}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.cottageService.getCottage(id).subscribe(data => {
      this.cottage = data;
    });

  }
}
