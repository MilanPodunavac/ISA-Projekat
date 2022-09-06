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
  getReservation(cottageId: number, resId: number): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/${cottageId}/reservation/${resId}`);
  }
  cancelReservation(resId: number): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/reservation/${resId}/cancel`, resId);
  }
  addReservationCommentary(id: number, resId: number, body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/${id}/reservation/${resId}/commentary`, body);
  }
  addActionCommentary(id: number, actId: number, body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/${id}/action/${actId}/commentary`, body);
  }
  addReview(id: number, body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/${id}/review`, body);
  }
  addComplaint(id: number, body: any): Observable<any>{
    return this._httpClient.post<any>(this._APIUrl+`/${id}/complaint`, body);
  }
  getAction(cottageId: number, actId: number): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/${cottageId}/action/${actId}`);
  }
  getVisitReport(id: number): Observable<any>{
    return this._httpClient.get<any>(this._APIUrl+`/${id}/visit-report`)
  }
  deleteCottage(id: number): Observable<any>{
    return this._httpClient.delete<any>(this._APIUrl+`/${id}`);
  }
}
