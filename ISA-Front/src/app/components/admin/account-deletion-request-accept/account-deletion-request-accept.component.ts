import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountDeletionResponse } from 'src/app/model/account-deletion-response.model';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-account-deletion-request-accept',
    templateUrl: './account-deletion-request-accept.component.html',
    styleUrls: ['./account-deletion-request-accept.component.scss']
})
export class AccountDeletionRequestAcceptComponent implements OnInit {
    form: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private _route: ActivatedRoute, private router: Router, private userService: UserService) { 
        this.form = formBuilder.group({
            responseText: ['', [Validators.required]]
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let request = new AccountDeletionResponse();
        request.responseText = this.form.get('responseText').value;

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.userService.acceptAccountDeletionRequest(id, request).subscribe({
            next: data => {
                this.router.navigate(['account-deletion-requests']).then(() => {
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
