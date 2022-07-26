import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginUser } from '../model/login-user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl: string;

  constructor(private http: HttpClient) {
    this.loginUrl = 'http://localhost:8080/ISA/auth/login';
  }

  public login(user: LoginUser): Observable<any> {
    return this.http.post<any>(this.loginUrl, user);
  }

  tokenIsPresent() {
    return localStorage.getItem('jwt') != undefined && localStorage.getItem('jwt') != null;
  }

  getToken() {
    return localStorage.getItem('jwt');
  }
}
