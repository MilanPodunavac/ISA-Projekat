import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CottageOwnerService {

  private _APIUrl = `http://localhost:8080/ISA/api`;

  constructor(private _httpClient: HttpClient) {}

  getOwnersCottages(): Observable<any[]>{
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + localStorage.getItem("jwt"));
    return this._httpClient.get<any[]>(this._APIUrl + "/cottageOwner/cottages", {headers: headers})
  }
  getProfit(): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/cottageOwner/profit`);
  }
}
