import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '../service/client.service';
import { CottageService } from '../service/cottage.service';

@Component({
  selector: 'app-new-cottage-review',
  templateUrl: './new-cottage-review.component.html',
  styleUrls: ['./new-cottage-review.component.scss']
})
export class NewCottageReviewComponent implements OnInit {

  addReviewForm: FormGroup;
  errorMessage: string;

  constructor(private formBuilder: FormBuilder, private clientService: ClientService, private cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { 
    this.addReviewForm = formBuilder.group({
      description: ['', [Validators.required]],
      grade: ['', [Validators.required, Validators.min(1), Validators.max(5)]],
  });
  }

  ngOnInit(): void {
  }

  public onSubmit(): void {
    this.errorMessage = "";
    this.clientService.getLoggedInClient().subscribe({
      next: data =>{
        var body = {
          grade: this.addReviewForm.get('grade').value,
          description: this.addReviewForm.get('description').value,
          clientId: data.id,
          cottageId: Number(this._route.snapshot.paramMap.get('id'))
        }
    
        this.cottageService.addReview(body.cottageId, body).subscribe({
            next: data => {
                this.router.navigate(['cottage/'+body.cottageId]).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                if(error.status === 200){
                  this.router.navigate(['cottage/'+body.cottageId]).then(() => {
                    window.location.reload();
                  });
                }
                this.errorMessage = error.error;
            }
        });
      }
    })
    
  }   

}
