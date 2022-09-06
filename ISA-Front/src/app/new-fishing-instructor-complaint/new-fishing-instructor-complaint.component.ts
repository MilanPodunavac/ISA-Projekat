import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '../service/client.service';
import { FishingInstructorService } from '../service/fishing-instructor.service';

@Component({
  selector: 'app-new-fishing-instructor-complaint',
  templateUrl: './new-fishing-instructor-complaint.component.html',
  styleUrls: ['./new-fishing-instructor-complaint.component.scss']
})
export class NewFishingInstructorComplaintComponent implements OnInit {

  addComplaintForm: FormGroup;
  errorMessage: string;

  constructor(private formBuilder: FormBuilder, private clientService: ClientService, private fishingInstructorService: FishingInstructorService, private router: Router, private _route: ActivatedRoute) { 
    this.addComplaintForm = formBuilder.group({
      description: ['', [Validators.required]],
  });
  }

  ngOnInit(): void {
  }

  public onSubmit(): void {
    this.errorMessage = "";
    this.clientService.getLoggedInClient().subscribe({
      next: data =>{
        var body = {
          description: this.addComplaintForm.get('description').value,
          clientId: data.id,
          saleEntityId: Number(this._route.snapshot.paramMap.get('id'))
        }
    
        this.fishingInstructorService.addComplaint(body.saleEntityId, body).subscribe({
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
