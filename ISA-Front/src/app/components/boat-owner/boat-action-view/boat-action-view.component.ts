import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BoatService } from 'src/app/service/boat.service';

@Component({
  selector: 'app-boat-action-view',
  templateUrl: './boat-action-view.component.html',
  styleUrls: ['./boat-action-view.component.scss']
})
export class BoatActionViewComponent implements OnInit {
  action: any;
  role: string;
  boatId: number;
  actId: number;

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute) { this.role = localStorage.getItem('role'); }

  ngOnInit(): void {
    this.boatId = Number(this._route.snapshot.paramMap.get('id'));
    this.actId = Number(this._route.snapshot.paramMap.get('actId'))
    this._boatService.getAction(this.boatId, this.actId).subscribe({
      next: data => {
        this.action = data
        var tempStart = new Date(this.action.startDate)
        var tempEnd = new Date(this.action.endDate)
        var tempValid = new Date(this.action.validUntilAndIncluding)
        //this.action.startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.action.endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        //this.action.validUntilAndIncluding = tempValid.getFullYear() + "-" + (tempValid.getMonth() + 1) + "-" + tempValid.getDate()// + ":" + tempValid.getHours()
        console.log(this.action)
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
  }
  routeToOwnerCommentary(){
    this.router.navigate(['boat', this.boatId, 'action', this.actId, 'new-commentary']).then(() => {
      window.location.reload();
    });
  }

}
