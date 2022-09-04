import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DeclineReason } from '../model/decline-reason.model';
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

    public getAllProviderRegistrationRequests(): Observable<RegisterProvider[]> {
        return this.http.get<RegisterProvider[]>(this.apiUrl + '/requests');
    }

    public acceptProviderRegistrationRequest(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/accept-request/' + id, { responseType: 'text'});
    }

    public declineProviderRegistrationRequest(id: number, declineReason: DeclineReason): Observable<string> {
        return this.http.put(this.apiUrl + '/decline-request/' + id, declineReason, { responseType: 'text'});
    }
}
