import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountDeletionResponse } from 'src/app/model/account-deletion-response.model';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-account-deletion-request-decline',
    templateUrl: './account-deletion-request-decline.component.html',
    styleUrls: ['./account-deletion-request-decline.component.scss']
})
export class AccountDeletionRequestDeclineComponent implements OnInit {
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
        this.userService.declineAccountDeletionRequest(id, request).subscribe({
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
