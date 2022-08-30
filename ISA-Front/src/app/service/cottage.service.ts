import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CottageService {

  private _APIUrl = `http://localhost:8080/ISA/api/cottage`;

  constructor(private _httpClient: HttpClient) { }

  getAllCottages(): Observable<any[]>{
    return this._httpClient.get<any[]>(this._APIUrl+``);
  }

  getCottage(id: number): Observable<any>{
    return this._httpClient.get<any[]>(this._APIUrl+`/${id}`);
  }
  updateCottage(id: number, val): Observable<any>{
    return this._httpClient.put<any>(this._APIUrl+`/${id}`, val);
  }
  removeImage(cottageId: number, picId: number): Observable<any>{
    return this._httpClient.delete<any>(this._APIUrl+`/${cottageId}/picture/${picId}`);
  }
  addImage(cottageId: number, pic: any){
    const formData = new FormData();
    formData.append("pictures", pic);
    return this._httpClient.post<any>(this._APIUrl+`/${cottageId}/picture`, formData)//, {headers: {"Content-Type": 'multipart/form-data'}})
  }
  addCottage(cottage: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl, cottage);
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
}
