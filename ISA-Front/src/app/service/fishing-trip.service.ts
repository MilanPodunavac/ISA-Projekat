import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingReservationGet } from '../model/fishing-reservation-get.model';
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

    public getFishingInstructorFishingTrips(): Observable<FishingTripGet[]> {
		return this.http.get<FishingTripGet[]>(this.apiUrl + '/fishingInstructorFishingTrips');
	}

    public getFishingInstructorReservations(): Observable<FishingReservationGet[]> {
		return this.http.get<FishingReservationGet[]>(this.apiUrl + '/fishingInstructorReservations');
	}

    public getSearchedFishingTrips(searchText: string): Observable<FishingTripGet[]> {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("searchText", searchText);
        
		return this.http.get<FishingTripGet[]>(this.apiUrl + '/searchInstructorFishingTrips', {params: queryParams});
	}
}
