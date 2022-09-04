import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdminGet } from '../model/admin-get.model';
import { BoatGet } from '../model/boat-get';
import { BoatOwnerGet } from '../model/boat-owner-get.model';
import { ClientGet } from '../model/client-get';
import { CottageGet } from '../model/cottage-get';
import { CottageOwnerGet } from '../model/cottage-owner-get.model';
import { FishingInstructorGet } from '../model/fishing-instructor-get';
import { IncomeRecordGet } from '../model/income-record-get.model';
import { LoyaltyPointsClientGets } from '../model/loyalty-points-client-gets.model';
import { LoyaltyPointsProviderGets } from '../model/loyalty-points-provider-gets.model';
import { LoyaltyProgramClient } from '../model/loyalty-program-client.model';
import { LoyaltyProgramProvider } from '../model/loyalty-program-provider.model';
import { Password } from '../model/password.model';
import { PersonalData } from '../model/personal-data.model';
import { PointsNeeded } from '../model/points-needed.model';
import { ProfitInInterval } from '../model/profit-in-interval.model';
import { RegisterProvider } from '../model/register-provider';
import { SystemTax } from '../model/system-tax.model';

@Injectable({
    providedIn: 'root'
})
export class AdminService {
    private apiUrl: string;

    constructor(private http: HttpClient) {
        this.apiUrl = 'http://localhost:8080/ISA/api/admin';
    }

    public getLoggedInAdmin(): Observable<AdminGet> {
		return this.http.get<AdminGet>(this.apiUrl + '/loggedInAdmin');
	}

    public changePassword(password: Password): Observable<string> {
        return this.http.put(this.apiUrl + '/changePassword', password, { responseType: 'text'});
    }

    public changePersonalData(personalData: PersonalData): Observable<string> {
        return this.http.put(this.apiUrl + '/changePersonalData', personalData, { responseType: 'text'});
    }

    public incomeInTimeInterval(profitInInterval: ProfitInInterval): Observable<string> {
        return this.http.post(this.apiUrl + '/incomeInTimeInterval', profitInInterval, { responseType: 'text'});
    }

    public registerAdmin(registerProvider: RegisterProvider): Observable<string> {
        return this.http.post(this.apiUrl + '/register', registerProvider, { responseType: 'text'});
    }

    public getAllIncomeRecords(): Observable<IncomeRecordGet[]> {
        return this.http.get<IncomeRecordGet[]>(this.apiUrl + '/allIncomeRecords');
    }

    public changeSystemTax(systemTax: SystemTax): Observable<string> {
        return this.http.put(this.apiUrl + '/currentSystemTaxPercentage', systemTax, { responseType: 'text'});
    }

    public getCurrentSystemTax(): Observable<string> {
        return this.http.get(this.apiUrl + '/getCurrentSystemTax', { responseType: 'text'});
    }

    public getLoyaltyProviderCategories(): Observable<LoyaltyProgramProvider[]> {
        return this.http.get<LoyaltyProgramProvider[]>(this.apiUrl + '/allLoyaltyProviderCategories');
    }

    public getLoyaltyClientCategories(): Observable<LoyaltyProgramClient[]> {
        return this.http.get<LoyaltyProgramClient[]>(this.apiUrl + '/allLoyaltyClientCategories');
    }

    public getPointsClientGetsAfterReservation(): Observable<string> {
        return this.http.get(this.apiUrl + '/getCurrentPointsClientGetsAfterReservation', { responseType: 'text'});
    }

    public getPointsProviderGetsAfterReservation(): Observable<string> {
        return this.http.get(this.apiUrl + '/getCurrentPointsProviderGetsAfterReservation', { responseType: 'text'});
    }

    public changePointsClientGets(loyaltyPointsClientGets: LoyaltyPointsClientGets): Observable<string> {
        return this.http.put(this.apiUrl + '/currentPointsClientGetsAfterReservation', loyaltyPointsClientGets, { responseType: 'text'});
    }

    public changePointsProviderGets(loyaltyPointsProviderGets: LoyaltyPointsProviderGets): Observable<string> {
        return this.http.put(this.apiUrl + '/currentPointsProviderGetsAfterReservation', loyaltyPointsProviderGets, { responseType: 'text'});
    }

    public changePointsNeededForProvider(id: number, pointsNeeded: PointsNeeded): Observable<string> {
        return this.http.put(this.apiUrl + '/changeProviderPointsNeededForLoyaltyProgramCategory/' + id, pointsNeeded, { responseType: 'text'});
    }

    public changePointsNeededForClient(id: number, pointsNeeded: PointsNeeded): Observable<string> {
        return this.http.put(this.apiUrl + '/changeClientPointsNeededForLoyaltyProgramCategory/' + id, pointsNeeded, { responseType: 'text'});
    }

    public getAllCottageOwners(): Observable<CottageOwnerGet[]> {
        return this.http.get<CottageOwnerGet[]>(this.apiUrl + '/getAllCottageOwners');
    }

    public getAllCottages(): Observable<CottageGet[]> {
        return this.http.get<CottageGet[]>(this.apiUrl + '/getAllCottages');
    }

    public getAllBoatOwners(): Observable<BoatOwnerGet[]> {
        return this.http.get<BoatOwnerGet[]>(this.apiUrl + '/getAllBoatOwners');
    }

    public getAllBoats(): Observable<BoatGet[]> {
        return this.http.get<BoatGet[]>(this.apiUrl + '/getAllBoats');
    }

    public getAllFishingInstructors(): Observable<FishingInstructorGet[]> {
        return this.http.get<FishingInstructorGet[]>(this.apiUrl + '/getAllFishingInstructors');
    }

    public getAllClients(): Observable<ClientGet[]> {
        return this.http.get<ClientGet[]>(this.apiUrl + '/getAllClients');
    }

    public deleteFishingInstructor(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/deleteFishingInstructor/' + id, { responseType: 'text'});
    }

    public deleteCottageOwner(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/deleteCottageOwner/' + id, { responseType: 'text'});
    }

    public deleteBoatOwner(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/deleteBoatOwner/' + id, { responseType: 'text'});
    }

    public deleteClient(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/deleteClient/' + id, { responseType: 'text'});
    }

    public deleteCottage(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/deleteCottage/' + id, { responseType: 'text'});
    }

    public deleteBoat(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/deleteBoat/' + id, { responseType: 'text'});
    }
}
