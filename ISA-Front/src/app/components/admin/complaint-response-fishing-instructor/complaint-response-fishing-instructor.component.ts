import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ComplaintResponse } from 'src/app/model/complaint-response.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-complaint-response-fishing-instructor',
    templateUrl: './complaint-response-fishing-instructor.component.html',
    styleUrls: ['./complaint-response-fishing-instructor.component.scss']
})
export class ComplaintResponseFishingInstructorComponent implements OnInit {
    form: FormGroup;
    
    constructor(formBuilder: FormBuilder, private _route: ActivatedRoute, private router: Router, private adminService: AdminService) { 
        this.form = formBuilder.group({
            responseToProvider: ['', [Validators.required]],
            responseToClient: ['', [Validators.required]]
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let request = new ComplaintResponse();
        request.responseToClient = this.form.get('responseToClient').value;
        request.responseToProvider = this.form.get('responseToProvider').value;

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.adminService.respondToComplaintFishingInstructor(id, request).subscribe(data => {
            this.router.navigate(['complaints']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }   
}
