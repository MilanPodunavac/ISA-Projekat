import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClientGet } from '../model/client-get';
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

  constructor(private router: Router, private _clientService: ClientService) {
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

  doubleClickFunction(cottageId: any, reservationId: any){
    this.router.navigate(['/cottage/'+cottageId+'/reservation/'+reservationId])
  }

}
