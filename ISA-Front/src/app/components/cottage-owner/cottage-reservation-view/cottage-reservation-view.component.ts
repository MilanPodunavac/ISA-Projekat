import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CottageService } from 'src/app/service/cottage.service';

@Component({
  selector: 'app-cottage-reservation-view',
  templateUrl: './cottage-reservation-view.component.html',
  styleUrls: ['./cottage-reservation-view.component.scss']
})
export class CottageReservationViewComponent implements OnInit {
  reservation: any;
  role: string;
  cottageId: number;
  resId: number;

  constructor(private _cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { this.role = localStorage.getItem('role'); }

  ngOnInit(): void {
    this.cottageId = Number(this._route.snapshot.paramMap.get('id'));
    this.resId = Number(this._route.snapshot.paramMap.get('resId'))
    this._cottageService.getReservation(this.cottageId, this.resId).subscribe({
      next: data => {
        this.reservation = data
        var tempStart = new Date(this.reservation.startDate)
        var tempEnd = new Date(this.reservation.endDate)
        this.reservation.startDate = tempStart.getFullYear() + "-" + (tempStart.getMonth() + 1) + "-" + tempStart.getDate()// + ":" + tempStart.getHours()
        this.reservation.endDate = tempEnd.getFullYear() + "-" + (tempEnd.getMonth() + 1) + "-" + tempEnd.getDate()// + ":" + tempEnd.getHours()
        console.log(this.reservation)
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
  }
  routeToOwnerCommentary(){
    this.router.navigate(['cottage', this.cottageId, 'reservation', this.resId, 'new-commentary']).then(() => {
      window.location.reload();
    });
  }
}
