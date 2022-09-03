import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BoatService {

  private _APIUrl = `http://localhost:8080/ISA/api/boat`;

  constructor(private _httpClient: HttpClient) { }

  getAllBoats(): Observable<any[]>{
    return this._httpClient.get<any[]>(this._APIUrl+``);
  }

  getBoat(id: number): Observable<any>{
    return this._httpClient.get<any[]>(this._APIUrl+`/${id}`);
  }

  addReservation(body: any, cottageId: number): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/`+cottageId+`/reservation`, body);
  }
}
