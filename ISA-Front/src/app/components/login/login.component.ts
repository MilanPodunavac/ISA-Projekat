import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LoginUser } from 'src/app/model/login-user';
import { AuthService } from 'src/app/service/auth.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;

    constructor(formBuilder: FormBuilder, private authService: AuthService) {
        this.loginForm = formBuilder.group({
            email: [''],
            password: ['']
        });
    }

    ngOnInit(): void {
    }

    login() {
        let loginUser = new LoginUser();
        loginUser.email = this.loginForm.get('email').value;
        loginUser.password = this.loginForm.get('password').value;

        this.authService.login(loginUser).subscribe(data => {
            localStorage.clear();
            localStorage.setItem("jwt", data.accessToken)
        });
    }
}
