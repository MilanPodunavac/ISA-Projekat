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
    return this._httpClient.get<any[]>(this._APIUrl);
  }

  getBoat(id: number): Observable<any>{
    return this._httpClient.get<any[]>(this._APIUrl+`/${id}`);
  }
  updateBoat(id: number, val): Observable<any>{
    return this._httpClient.put<any>(this._APIUrl+`/${id}`, val);
  }
  removeImage(boatId: number, picId: number): Observable<any>{
    return this._httpClient.delete<any>(this._APIUrl+`/${boatId}/picture/${picId}`);
  }
  addImage(boatId: number, pic: any){
    const formData = new FormData();
    formData.append("pictures", pic);
    return this._httpClient.post<any>(this._APIUrl+`/${boatId}/picture`, formData)//, {headers: {"Content-Type": 'multipart/form-data'}})
  }
  addBoat(boat: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl, boat);
  }
  addAvailabilityPeriod(period: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/availabilityPeriod`, period);
  }
  addAction(body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/action`, body);
  }
  addReservation(body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/reservation`, body);
  }
  getReservation(boatId: number, resId: number): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/${boatId}/reservation/${resId}`);
  }
  addReservationCommentary(id: number, resId: number, body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/${id}/reservation/${resId}/commentary`, body);
  }
  addActionCommentary(id: number, actId: number, body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/${id}/action/${actId}/commentary`, body);
  }
  getAction(boatId: number, actId: number): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/${boatId}/action/${actId}`);
  }
  getVisitReport(id: number): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/${id}/visit-report`)
  }
  deleteBoat(id: number): Observable<any>{
    return this._httpClient.delete<any>(this._APIUrl+`/${id}`);
  }
}
