import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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
}
