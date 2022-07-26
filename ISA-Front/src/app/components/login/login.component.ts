import { Component, OnInit } from '@angular/core';
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

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  login() {
    let loginUser = new LoginUser();
    loginUser.email = this.inputEmail;
    loginUser.password = this.inputPassword;

    this.authService.login(loginUser)
      .subscribe(data => {
        localStorage.clear();
        localStorage.setItem("jwt", data.accessToken)
      });
  }
}
