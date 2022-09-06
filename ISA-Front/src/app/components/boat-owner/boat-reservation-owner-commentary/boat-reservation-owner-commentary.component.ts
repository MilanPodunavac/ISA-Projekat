import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BoatService } from 'src/app/service/boat.service';

@Component({
  selector: 'app-boat-reservation-owner-commentary',
  templateUrl: './boat-reservation-owner-commentary.component.html',
  styleUrls: ['./boat-reservation-owner-commentary.component.scss']
})
export class BoatReservationOwnerCommentaryComponent implements OnInit {
  sanctionSuggested: boolean;
  commentary: string;
  clientCame: boolean;
  boatId: number
  resId: number

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    if(localStorage.getItem("role") != "ROLE_BOAT_OWNER"){
      this.router.navigate(['login']).then(() => {
        window.location.reload();
      });
    }
    this.boatId = Number(this._route.snapshot.paramMap.get('id'));
    this.resId = Number(this._route.snapshot.paramMap.get('resId'))
  }

  submit(){
    if(this.commentary == ""){
      alert("Commentary is required")
      return
    }
    var body = {
      sanctionSuggested: this.sanctionSuggested,
      clientCame: this.clientCame,
      commentary: this.commentary
    }
    this._boatService.addReservationCommentary(this.boatId, this.resId, body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Commentary added")
        }
        this.router.navigate(['/boat/', this.boatId,'reservation', this.resId]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['/boat/', this.boatId,'reservation', this.resId]).then(() => {
            window.location.reload();
          });
        }
        else{
          alert(data.error)
        }     
      }
    })
  }

}
