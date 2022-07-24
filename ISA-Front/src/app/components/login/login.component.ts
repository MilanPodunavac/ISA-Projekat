import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUser } from 'src/app/model/login-user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  inputEmail: string = "";
  inputPassword: string = "";

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    let loginUser = new LoginUser();
    loginUser.email = this.inputEmail;
    loginUser.password = this.inputPassword;

    this.userService.login(loginUser)
      .subscribe(data => {
        localStorage.clear();
        localStorage.setItem("jwt", data.accessToken)
      });
  }
}
