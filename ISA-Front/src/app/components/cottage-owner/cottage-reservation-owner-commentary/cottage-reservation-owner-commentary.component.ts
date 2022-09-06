import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CottageService } from 'src/app/service/cottage.service';

@Component({
  selector: 'app-cottage-reservation-owner-commentary',
  templateUrl: './cottage-reservation-owner-commentary.component.html',
  styleUrls: ['./cottage-reservation-owner-commentary.component.scss']
})
export class CottageReservationOwnerCommentaryComponent implements OnInit {
  sanctionSuggested: boolean;
  commentary: string;
  clientCame: boolean;
  cottageId: number
  resId: number

  constructor(private _cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    if(localStorage.getItem("role") != "ROLE_COTTAGE_OWNER"){
      this.router.navigate(['login']).then(() => {
        window.location.reload();
      });
    }
    this.cottageId = Number(this._route.snapshot.paramMap.get('id'));
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
    this._cottageService.addReservationCommentary(this.cottageId, this.resId, body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Commentary added")
        }
        this.router.navigate(['/cottage/', this.cottageId,'reservation', this.resId]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['/cottage/', this.cottageId,'reservation', this.resId]).then(() => {
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
