import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/model/login-user';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  inputEmail: string = "";
  inputPassword: string = "";

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    let loginUser = new LoginUser();
    loginUser.email = this.inputEmail;
    loginUser.password = this.inputPassword;

    this.authService.login(loginUser)
      .subscribe(data => {
        this.router.navigate(['/']);
      });
  }
}
