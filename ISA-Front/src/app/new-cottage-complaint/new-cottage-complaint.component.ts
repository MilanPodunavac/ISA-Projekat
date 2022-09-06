import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientService } from '../service/client.service';
import { CottageService } from '../service/cottage.service';

@Component({
  selector: 'app-new-cottage-complaint',
  templateUrl: './new-cottage-complaint.component.html',
  styleUrls: ['./new-cottage-complaint.component.scss']
})
export class NewCottageComplaintComponent implements OnInit {

  addComplaintForm: FormGroup;
  errorMessage: string;

  constructor(private formBuilder: FormBuilder, private clientService: ClientService, private cottageService: CottageService, private router: Router, private _route: ActivatedRoute) { 
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
    
        this.cottageService.addComplaint(body.saleEntityId, body).subscribe({
            next: data => {
                this.router.navigate(['cottage/'+body.saleEntityId]).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                if(error.status === 200){
                  this.router.navigate(['cottage/'+body.saleEntityId]).then(() => {
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
