import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CottageService } from 'src/app/service/cottage.service';

@Component({
  selector: 'app-cottage-action-owner-commentary',
  templateUrl: './cottage-action-owner-commentary.component.html',
  styleUrls: ['./cottage-action-owner-commentary.component.scss']
})
export class CottageActionOwnerCommentaryComponent implements OnInit {
  sanctionSuggested: boolean;
  commentary: string;
  clientCame: boolean;
  cottageId: number
  actId: number

  constructor(private _cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { }

  ngOnInit(): void {
    this.cottageId = Number(this._route.snapshot.paramMap.get('id'));
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
    this._cottageService.addActionCommentary(this.cottageId, this.actId, body).subscribe({
      next: data => {
        if(data.status === 200){
          alert("Commentary added")
        }
        this.router.navigate(['/cottage/', this.cottageId,'action', this.actId]).then(() => {
          window.location.reload();
        });
      },
      error: data => {
        console.log(data)
        if(data.status === 200){
          alert(data.error.text)
          this.router.navigate(['/cottage/', this.cottageId,'action', this.actId]).then(() => {
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
