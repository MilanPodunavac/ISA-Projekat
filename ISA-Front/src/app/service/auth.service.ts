import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ApiService } from './api.service';
import { ConfigService } from './config.service';
import { map } from 'rxjs/operators';

@Injectable()
export class AuthService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService,
  ) {
  }

  login(user) {
    const loginHeaders = new HttpHeaders({
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    });
    // const body = `username=${user.username}&password=${user.password}`;
    const body = {
      'email': user.email,
      'password': user.password
    };
    return this.apiService.post(this.config.login_url, JSON.stringify(body), loginHeaders)
      .pipe(map((res) => {
        localStorage.clear();
        localStorage.setItem("jwt", res.accessToken)
      }));
  }

  tokenIsPresent() {
    return localStorage.getItem('jwt') != undefined && localStorage.getItem('jwt') != null;
  }

  getToken() {
    return localStorage.getItem('jwt');
  }

}
