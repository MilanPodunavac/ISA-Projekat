import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingInstructorAvailablePeriodGet } from '../model/fishing-instructor-available-period-get.model';
import { FishingInstructorGet } from '../model/fishing-instructor-get';
import { Password } from '../model/password.model';
import { PeriodicalReservationsGet } from '../model/periodical-reservations-get.model';
import { PersonalData } from '../model/personal-data.model';
import { ProfitInInterval } from '../model/profit-in-interval.model';

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

    public changePersonalData(personalData: PersonalData): Observable<string> {
		  return this._httpClient.put(this.apiUrl + '/fishing-instructor/changePersonalData', personalData, { responseType: 'text'});
    }

    public changePassword(password: Password): Observable<string> {
		  return this._httpClient.put(this.apiUrl + '/fishing-instructor/changePassword', password, { responseType: 'text'});
    }

    public addAvailabilityPeriod(availabilityPeriod: FishingInstructorAvailablePeriodGet): Observable<string> {
		  return this._httpClient.post(this.apiUrl + '/fishing-instructor/addAvailablePeriod', availabilityPeriod, { responseType: 'text'});
    }

    public incomeInTimeInterval(profitInInterval: ProfitInInterval): Observable<string> {
		  return this._httpClient.post(this.apiUrl + '/fishing-instructor/incomeInTimeInterval', profitInInterval, { responseType: 'text'});
    }

    public weeklyReservations(): Observable<PeriodicalReservationsGet[]> {
		  return this._httpClient.get<PeriodicalReservationsGet[]>(this.apiUrl + '/fishing-instructor/weeklyReservations');
    }

    public monthlyReservations(): Observable<PeriodicalReservationsGet[]> {
		  return this._httpClient.get<PeriodicalReservationsGet[]>(this.apiUrl + '/fishing-instructor/monthlyReservations');
    }

    public yearlyReservations(): Observable<PeriodicalReservationsGet[]> {
		  return this._httpClient.get<PeriodicalReservationsGet[]>(this.apiUrl + '/fishing-instructor/yearlyReservations');
    }
}
