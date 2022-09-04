import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Password } from 'src/app/model/password.model';
import { AdminService } from 'src/app/service/admin.service';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-change-password',
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
    role: string
    passwordForm: FormGroup;

    constructor(formBuilder: FormBuilder, private router: Router, private fishingInstructorService: FishingInstructorService, private _userService: UserService, private adminService: AdminService) {
        this.role = localStorage.getItem("role");
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
        if(this.role === "ROLE_FISHING_INSTRUCTOR"){
            this.fishingInstructorService.changePassword(passwordRequest).subscribe(data => {
                this.router.navigate(['profile']).then(() => {
                    window.location.reload();
                });
                alert(data);
            });
        }
        if(this.role === "ROLE_COTTAGE_OWNER" || this.role === "ROLE_BOAT_OWNER"){
            this._userService.updatePassword({newPassword: this.passwordForm.get('password').value, repeatedNewPassword: this.passwordForm.get("repeatPassword").value}).subscribe(data => {
                this.router.navigate(['profile']).then(() => {
                    window.location.reload();
                });
                alert(data);
            });
        }
        if(this.role === "ROLE_ADMIN"){
            this.adminService.changePassword(passwordRequest).subscribe(data => {
                this.router.navigate(['income-records']).then(() => {
                    window.location.reload();
                });
                alert(data);
            });
        }
    }   
}
