import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoyaltyProgramProvider } from '../model/loyalty-program-provider.model';

@Injectable({
    providedIn: 'root'
})
export class LoyaltyProgramService {
    private apiUrl: string;

    constructor(private _httpClient: HttpClient) {
        this.apiUrl = 'http://localhost:8080/ISA/api';
    }

    public getOneHigherLoyaltyProviderCategory(id: number): Observable<LoyaltyProgramProvider> {
		return this._httpClient.get<LoyaltyProgramProvider>(this.apiUrl + '/loyalty-program/getOneHigherLoyaltyProviderCategory/' + id);
	}
}
