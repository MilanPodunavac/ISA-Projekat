import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CottageService {

  private _APIUrl = `http://localhost:8080/ISA/api`;

  constructor(private _httpClient: HttpClient) { }

  getAllCottages(): Observable<any[]>{
    return this._httpClient.get<any[]>(this._APIUrl+`/cottage`);
  }

  getCottage(id: number): Observable<any>{
    return this._httpClient.get<any[]>(this._APIUrl+`/cottage/${id}`);
  }
}
