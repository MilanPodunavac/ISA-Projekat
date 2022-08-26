import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
 	providedIn: 'root'
})
export class UserService {
  	private roleUrl: string;

	constructor(private http: HttpClient) {
		this.roleUrl = 'http://localhost:8080/ISA/api/users/role';
	}

	public loggedInRole(): Observable<string> {
		return this.http.get(this.roleUrl, { responseType: 'text'});
	}
}
