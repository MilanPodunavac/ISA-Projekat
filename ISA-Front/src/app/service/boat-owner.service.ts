import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoatOwnerService {

  private _APIUrl = `http://localhost:8080/ISA/api`;

  constructor(private _httpClient: HttpClient) {}

  getOwnersBoats(): Observable<any[]>{
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
    return this._httpClient.get<any[]>(this._APIUrl + "/boatOwner/boats", {headers: headers})
  }
  getProfit(): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/boatOwner/profit`);
  }
}
