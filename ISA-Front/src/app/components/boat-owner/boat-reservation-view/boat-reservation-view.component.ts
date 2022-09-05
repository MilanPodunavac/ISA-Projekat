import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BoatService } from 'src/app/service/boat.service';

@Component({
  selector: 'app-boat-reservation-view',
  templateUrl: './boat-reservation-view.component.html',
  styleUrls: ['./boat-reservation-view.component.scss']
})
export class BoatReservationViewComponent implements OnInit {
  reservation: any;
  role: string;
  boatId: number;
  resId: number;

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute) { this.role = localStorage.getItem('role'); }

  ngOnInit(): void {
    this.boatId = Number(this._route.snapshot.paramMap.get('id'));
    this.resId = Number(this._route.snapshot.paramMap.get('resId'))
    this._boatService.getReservation(this.boatId, this.resId).subscribe({
      next: data => {
        this.reservation = data
        var tempStart = new Date(this.reservation.startDate)
        var tempEnd = new Date(this.reservation.endDate)
        //this.reservation.startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        //this.reservation.endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        console.log(this.reservation)
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
  }
  routeToOwnerCommentary(){
    this.router.navigate(['boat', this.boatId, 'reservation', this.resId, 'new-commentary']).then(() => {
      window.location.reload();
    });
  }

}
