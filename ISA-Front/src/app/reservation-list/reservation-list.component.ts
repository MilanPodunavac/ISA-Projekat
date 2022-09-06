import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClientGet } from '../model/client-get';
import { BoatService } from '../service/boat.service';
import { ClientService } from '../service/client.service';
import { CottageService } from '../service/cottage.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {

  client: ClientGet;
  role: string;
  now: Date;

  constructor(private router: Router, private _clientService: ClientService, private _cottageService: CottageService, private _boatService: BoatService) {
    this.now = new Date()
    this.role = localStorage.getItem('role');
  }


  ngOnInit(): void {
      this._clientService.getLoggedInClient().subscribe(
      {next: data => {
        this.client = data;
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
    return;
  }

  doubleClickFunctionCottage(cottageId: any, reservationId: any){
    this.router.navigate(['/cottage/'+cottageId+'/reservation/'+reservationId])
  }

  doubleClickFunctionBoat(boatId: any, reservationId: any){
    this.router.navigate(['/boat/'+boatId+'/reservation/'+reservationId])
  }

  cancelReservationCottage(reservationId: any){
    this._cottageService.cancelReservation(reservationId).subscribe(
      {next: data => {
        console.log(data)
        window.location.reload();
      },
      error: data => {
        if(data.status === 200){
          console.log(data)
          window.location.reload();
        }
      }
    });
  }

  cancelReservationBoat(reservationId: any){
    this._boatService.cancelReservation(reservationId).subscribe(
      {next: data => {
        console.log(data)
        window.location.reload();
      },
      error: data => {
        if(data.status === 200){
          console.log(data)
          window.location.reload();
        }
      }
    });
  }

}
