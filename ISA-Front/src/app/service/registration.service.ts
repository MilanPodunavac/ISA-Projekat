import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterProvider } from '../model/register-provider';

@Injectable({
    providedIn: 'root'
})
export class RegistrationService {
    private apiUrl: string;

    constructor(private http: HttpClient) {
        this.apiUrl = 'http://localhost:8080/ISA/api/registration';
    }

    public register(user: RegisterProvider): Observable<string> {
        return this.http.post(this.apiUrl, user, { responseType: 'text'});
    }
}
