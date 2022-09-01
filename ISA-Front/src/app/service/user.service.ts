import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountDeletionRequestGet } from '../model/account-deletion-request-get.model';

@Injectable({
 	providedIn: 'root'
})
export class UserService {
  	private apiUrl: string;

	constructor(private http: HttpClient) {
		this.apiUrl = 'http://localhost:8080/ISA/api/users';
	}

	public loggedInRole(): Observable<string> {
		return this.http.get(this.apiUrl + '/role', { responseType: 'text'});
	}

	getLoggedInUser(): Observable<any>{
		return this.http.get<any>(this.apiUrl);
	}

	public sendAccountDeletionRequest(accountDeletionRequest: AccountDeletionRequestGet): Observable<string> {
		return this.http.post(this.apiUrl + '/accountDeletionRequest', accountDeletionRequest, { responseType: 'text'});
	}
	updatePersonalInfo(body: any): Observable<string> {
		return this.http.put(this.apiUrl, body, { responseType: 'text'});
	}
	updatePassword(body: any): Observable<string> {
		return this.http.put(this.apiUrl+`/password`, body, { responseType: 'text'});
	}
}
