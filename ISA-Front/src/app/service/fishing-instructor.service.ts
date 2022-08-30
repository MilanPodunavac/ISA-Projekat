import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingInstructorProfileDataGet } from '../model/fishing-instructor/fishing-instructor-profile-data-get.model';

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
    
    getFishingInstructor(id: number): Observable<any>{
        return this._httpClient.get<any[]>(this.apiUrl+`/fishing-instructor/${id}`);
    }

    public getProfileData(): Observable<FishingInstructorProfileDataGet> {
		return this._httpClient.get<FishingInstructorProfileDataGet>(this.apiUrl + '/fishing-instructor/profileData');
	}
}
