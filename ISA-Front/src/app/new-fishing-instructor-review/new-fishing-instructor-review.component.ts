import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '../service/client.service';
import { CottageService } from '../service/cottage.service';
import { FishingInstructorService } from '../service/fishing-instructor.service';

@Component({
  selector: 'app-new-fishing-instructor-review',
  templateUrl: './new-fishing-instructor-review.component.html',
  styleUrls: ['./new-fishing-instructor-review.component.scss']
})
export class NewFishingInstructorReviewComponent implements OnInit {

  addReviewForm: FormGroup;
  errorMessage: string;

  constructor(private formBuilder: FormBuilder, private clientService: ClientService, private fishingInstructorService: FishingInstructorService, private router: Router, private _route: ActivatedRoute) { 
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
          saleEntityId: Number(this._route.snapshot.paramMap.get('id'))
        }
    
        this.fishingInstructorService.addReview(body.saleEntityId, body).subscribe({
            next: data => {
                this.router.navigate(['fishing-instructor/'+body.saleEntityId]).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                if(error.status === 200){
                  this.router.navigate(['fishing-instructor/'+body.saleEntityId]).then(() => {
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
