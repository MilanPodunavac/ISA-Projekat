import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BoatService } from '../service/boat.service';
import { ClientService } from '../service/client.service';

@Component({
  selector: 'app-new-boat-complaint',
  templateUrl: './new-boat-complaint.component.html',
  styleUrls: ['./new-boat-complaint.component.scss']
})
export class NewBoatComplaintComponent implements OnInit {

  addComplaintForm: FormGroup;
  errorMessage: string;

  constructor(private formBuilder: FormBuilder, private clientService: ClientService, private boatService: BoatService, private router: Router, private _route: ActivatedRoute) { 
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
    
        this.boatService.addComplaint(body.saleEntityId, body).subscribe({
            next: data => {
                this.router.navigate(['boat/'+body.saleEntityId]).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                if(error.status === 200){
                  this.router.navigate(['boat/'+body.saleEntityId]).then(() => {
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
