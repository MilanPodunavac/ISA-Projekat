import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingInstructorAvailablePeriodGet } from '../model/fishing-instructor-available-period-get.model';
import { FishingInstructorGet } from '../model/fishing-instructor-get';

@Injectable({
    providedIn: 'root'
})
export class FishingInstructorService {
    private apiUrl: string;

    constructor(private _httpClient: HttpClient) {
        this.apiUrl = 'http://localhost:8080/ISA/api';
    }

    getAllFishingInstructors(): Observable<any[]>{
        return this._httpClient.get<any[]>(this.apiUrl+`/fishing-instructor`);
    }
    
    public getFishingInstructor(id: number): Observable<FishingInstructorGet>{
        return this._httpClient.get<FishingInstructorGet>(this.apiUrl + '/fishing-instructor/' + id);
    }

    public getLoggedInInstructor(): Observable<FishingInstructorGet> {
		return this._httpClient.get<FishingInstructorGet>(this.apiUrl + '/fishing-instructor/getLoggedInInstructor');
	}

    public getFishingInstructorAvailablePeriods(): Observable<FishingInstructorAvailablePeriodGet[]> {
		return this._httpClient.get<FishingInstructorAvailablePeriodGet[]>(this.apiUrl + '/fishing-instructor/fishingInstructorAvailablePeriods');
	}
}
