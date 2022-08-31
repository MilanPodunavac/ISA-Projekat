import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientGet } from '../model/client-get';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private _APIUrl = `http://localhost:8080/ISA/api/client`;

  constructor(private _httpClient: HttpClient) { }

  getAllClients(): Observable<ClientGet[]>{
    return this._httpClient.get<ClientGet[]>(this._APIUrl+``);
  }

  getClient(id: number): Observable<ClientGet>{
    return this._httpClient.get<ClientGet>(this._APIUrl+`/${id}`);
  }

  getLoggedInClient(): Observable<ClientGet> {
		return this._httpClient.get<ClientGet>(this._APIUrl + '/getLoggedInClient');
	}
}
