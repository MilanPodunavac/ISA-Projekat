import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Password } from 'src/app/model/password.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';

@Component({
    selector: 'app-change-password',
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
    passwordForm: FormGroup;

    constructor(formBuilder: FormBuilder, private router: Router, private fishingInstructorService: FishingInstructorService) {
        this.passwordForm = formBuilder.group({
            password: ['', [Validators.required, Validators.minLength(3)]],
            repeatPassword: ['', [Validators.required, Validators.minLength(3)]]
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let passwordRequest = new Password();
        passwordRequest.password = this.passwordForm.get('password').value;

        this.fishingInstructorService.changePassword(passwordRequest).subscribe(data => {
            this.router.navigate(['profile']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }   
}
