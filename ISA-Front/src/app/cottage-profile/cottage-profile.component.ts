import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { CottageGet } from '../model/cottage-get';
import { CottageService } from '../service/cottage.service';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['./cottage-profile.component.scss']
})
export class CottageProfileComponent implements OnInit {
  role: string;
  cottage: CottageGet;

  constructor(private _route: ActivatedRoute, private cottageService: CottageService, private sanitizer: DomSanitizer) {this.role = localStorage.getItem('role');}

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.cottageService.getCottage(id).subscribe(data => {
      this.cottage = data;
      for(let i = 0; i<this.cottage.pictures.length; i++){
        this.cottage.pictures[i].data = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + this.cottage.pictures[i].data);
      }
    });

  }
}
