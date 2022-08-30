import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/model/login-user';
import { AuthService } from 'src/app/service/auth.service';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private router: Router, private authService: AuthService, private userService: UserService, private fishingInstructorService: FishingInstructorService) {
        this.loginForm = formBuilder.group({
            email: [''],
            password: ['']
        });
    }

    ngOnInit(): void {
    }

    login() {
        this.errorMessage = "";
        let loginUser = new LoginUser();
        loginUser.email = this.loginForm.get('email').value;
        loginUser.password = this.loginForm.get('password').value;

        this.authService.login(loginUser).subscribe({
            next: data => {
                localStorage.clear();
                localStorage.setItem("jwt", data.accessToken);
                this.userService.loggedInRole().subscribe(data => {
                    localStorage.setItem("role", data);
                    
                    if (data === "ROLE_FISHING_INSTRUCTOR") {
                        this.router.navigate(['fishing-instructor-home']).then(() => {
                            window.location.reload();
                        });
                    }
                    if (data === "ROLE_COTTAGE_OWNER") {
                        this.router.navigate(['cottage-owner']).then(() => {
                            window.location.reload();
                        });
                    }
                });
            },
            error: () => {
                this.errorMessage = "User doesn't exist!";
            }
        });
    }
}
