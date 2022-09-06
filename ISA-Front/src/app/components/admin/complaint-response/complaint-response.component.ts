import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ComplaintResponse } from 'src/app/model/complaint-response.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-complaint-response',
    templateUrl: './complaint-response.component.html',
    styleUrls: ['./complaint-response.component.scss']
})
export class ComplaintResponseComponent implements OnInit {
    form: FormGroup;
    errorMessage: string;
    
    constructor(formBuilder: FormBuilder, private _route: ActivatedRoute, private router: Router, private adminService: AdminService) { 
        this.form = formBuilder.group({
            responseToProvider: ['', [Validators.required]],
            responseToClient: ['', [Validators.required]]
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let request = new ComplaintResponse();
        request.responseToClient = this.form.get('responseToClient').value;
        request.responseToProvider = this.form.get('responseToProvider').value;

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.adminService.respondToComplaint(id, request).subscribe({
            next: data => {
                this.router.navigate(['complaints']).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                this.errorMessage = error.error;
            }
        });
    }   
}
