import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingInstructorFishingTripTableGet } from '../model/fishing-instructor-fishing-trip-table-get.model';
import { FishingTripGet } from '../model/fishing-trip-get';

@Injectable({
    providedIn: 'root'
})
export class FishingTripService {
    private apiUrl: string;
    
    constructor(private http: HttpClient) {
        this.apiUrl = 'http://localhost:8080/ISA/api/fishing-trip';
    }

    public getFishingTrip(id: number): Observable<FishingTripGet> {
		return this.http.get<FishingTripGet>(this.apiUrl + '/' + id);
	}

    public getFishingInstructorFishingTrips(): Observable<FishingInstructorFishingTripTableGet[]> {
		return this.http.get<FishingInstructorFishingTripTableGet[]>(this.apiUrl + '/fishingInstructorFishingTrips');
	}
}
