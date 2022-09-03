import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { BoatService } from 'src/app/service/boat.service';

@Component({
  selector: 'app-boat-action-owner-commentary',
  templateUrl: './boat-action-owner-commentary.component.html',
  styleUrls: ['./boat-action-owner-commentary.component.scss']
})
export class BoatActionOwnerCommentaryComponent implements OnInit {
  sanctionSuggested: boolean;
  commentary: string;
  clientCame: boolean;
  boatId: number
  actId: number

  constructor(private _boatService: BoatService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this.boatId = Number(this._route.snapshot.paramMap.get('id'));
    this.actId = Number(this._route.snapshot.paramMap.get('actId'))
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
    this._boatService.addActionCommentary(this.boatId, this.actId, body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Commentary added")
        }
        this.router.navigate(['/boat/', this.boatId,'action', this.actId]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['/boat/', this.boatId,'action', this.actId]).then(() => {
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
