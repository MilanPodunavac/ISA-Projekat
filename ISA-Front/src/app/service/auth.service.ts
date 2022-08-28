import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginUser } from '../model/login-user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl: string;

  constructor(private http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/ISA/auth';
  }

  public login(user: LoginUser): Observable<any> {
    return this.http.post<any>(this.apiUrl + '/login', user);
  }

  tokenIsPresent() {
    return localStorage.getItem('jwt') != undefined && localStorage.getItem('jwt') != null;
  }

  getToken() {
    return localStorage.getItem('jwt');
  }
}
