import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountDeletionRequestGet } from 'src/app/model/account-deletion-request-get.model';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-account-deletion-request',
    templateUrl: './account-deletion-request.component.html',
    styleUrls: ['./account-deletion-request.component.scss']
})
export class AccountDeletionRequestComponent implements OnInit {
    accountDeletionRequestForm: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private router: Router, private userService: UserService) {
        this.accountDeletionRequestForm = formBuilder.group({
            reason: ['', [Validators.required]]
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let accountDeletionRequest = new AccountDeletionRequestGet();
        accountDeletionRequest.text = this.accountDeletionRequestForm.get('reason').value;

        this.userService.sendAccountDeletionRequest(accountDeletionRequest).subscribe({
            next: data => {
                this.router.navigate(['profile']).then(() => {
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
