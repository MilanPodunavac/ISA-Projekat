import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterUser } from '../model/register-user';

@Injectable({
  providedIn: 'root'
})
export class FishingInstructorService {
  private registerUrl: string;

  constructor(private http: HttpClient) {
    this.registerUrl = 'http://localhost:8080/ISA/fishing-instructor/register';
  }

  public register(user: RegisterUser): Observable<string> {
    return this.http.post<string>(this.registerUrl, user);
  }
}
