import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingInstructorProfileDataGet } from '../model/fishing-instructor-profile-data-get.model';
import { RegisterProvider } from '../model/register-provider';

@Injectable({
    providedIn: 'root'
})
export class FishingInstructorService {
    private apiUrl: string;

    constructor(private http: HttpClient) {
        this.apiUrl = 'http://localhost:8080/ISA/api/fishing-instructor';
    }

    public getProfileData(): Observable<FishingInstructorProfileDataGet> {
		return this.http.get<FishingInstructorProfileDataGet>(this.apiUrl + '/profileData');
	}
}
