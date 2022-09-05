import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ActionGet } from '../model/action-get.model';
import { AdminGet } from '../model/admin-get.model';
import { BoatGet } from '../model/boat-get';
import { BoatOwnerGet } from '../model/boat-owner-get.model';
import { ClientGet } from '../model/client-get';
import { ComplaintFishingInstructorGet } from '../model/complaint-fishing-instructor-get.model';
import { ComplaintGet } from '../model/complaint-get.model';
import { ComplaintResponse } from '../model/complaint-response.model';
import { CottageGet } from '../model/cottage-get';
import { CottageOwnerGet } from '../model/cottage-owner-get.model';
import { FishingActionGet } from '../model/fishing-action-get.model';
import { FishingInstructorGet } from '../model/fishing-instructor-get';
import { FishingReservationGet } from '../model/fishing-reservation-get.model';
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
import { ReservationGet } from '../model/reservation-get.model';
import { ReviewFishingTripGet } from '../model/review-fishing-trip-get.model';
import { ReviewGet } from '../model/review-get.model';
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

    public getReservationsWithCommentariesForAdmin(): Observable<ReservationGet[]> {
        return this.http.get<ReservationGet[]>(this.apiUrl + '/getReservationsWithCommentariesForAdmin');
    }

    public getQuickReservationsWithCommentariesForAdmin(): Observable<ActionGet[]> {
        return this.http.get<ActionGet[]>(this.apiUrl + '/getQuickReservationsWithCommentariesForAdmin');
    }

    public getFishingReservationsWithCommentariesForAdmin(): Observable<FishingReservationGet[]> {
        return this.http.get<FishingReservationGet[]>(this.apiUrl + '/getFishingReservationsWithCommentariesForAdmin');
    }

    public getFishingQuickReservationsWithCommentariesForAdmin(): Observable<FishingActionGet[]> {
        return this.http.get<FishingActionGet[]>(this.apiUrl + '/getFishingQuickReservationsWithCommentariesForAdmin');
    }

    public fishingReservationCommentaryAccept(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/fishingReservation/' + id + '/commentaryAccept', { responseType: 'text'});
    }

    public fishingReservationCommentaryDecline(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/fishingReservation/' + id + '/commentaryDecline', { responseType: 'text'});
    }

    public fishingQuickReservationCommentaryAccept(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/fishingQuickReservation/' + id + '/commentaryAccept', { responseType: 'text'});
    }

    public fishingQuickReservationCommentaryDecline(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/fishingQuickReservation/' + id + '/commentaryDecline', { responseType: 'text'});
    }

    public reservationCommentaryAccept(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/reservation/' + id + '/commentaryAccept', { responseType: 'text'});
    }

    public reservationCommentaryDecline(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/reservation/' + id + '/commentaryDecline', { responseType: 'text'});
    }

    public quickReservationCommentaryAccept(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/quickReservation/' + id + '/commentaryAccept', { responseType: 'text'});
    }

    public quickReservationCommentaryDecline(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/quickReservation/' + id + '/commentaryDecline', { responseType: 'text'});
    }

    public getAllComplaints(): Observable<ComplaintGet[]> {
        return this.http.get<ComplaintGet[]>(this.apiUrl + '/getAllComplaints');
    }

    public getAllFishingInstructorComplaints(): Observable<ComplaintFishingInstructorGet[]> {
        return this.http.get<ComplaintFishingInstructorGet[]>(this.apiUrl + '/getAllFishingInstructorComplaints');
    }

    public respondToComplaint(id: number, complaintResponse: ComplaintResponse): Observable<string> {
        return this.http.put(this.apiUrl + '/respondToComplaint/' + id, complaintResponse, { responseType: 'text'});
    }

    public respondToComplaintFishingInstructor(id: number, complaintResponse: ComplaintResponse): Observable<string> {
        return this.http.put(this.apiUrl + '/respondToComplaintFishingInstructor/' + id, complaintResponse, { responseType: 'text'});
    }

    public getAllUnapprovedReviews(): Observable<ReviewGet[]> {
        return this.http.get<ReviewGet[]>(this.apiUrl + '/getAllUnapprovedReviews');
    }

    public getAllUnapprovedFishingTripReviews(): Observable<ReviewFishingTripGet[]> {
        return this.http.get<ReviewFishingTripGet[]>(this.apiUrl + '/getAllUnapprovedFishingTripReviews');
    }

    public acceptReview(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/acceptReview/' + id, { responseType: 'text'});
    }

    public acceptReviewFishingTrip(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/acceptReviewFishingTrip/' + id, { responseType: 'text'});
    }

    public declineReview(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/declineReview/' + id, { responseType: 'text'});
    }

    public declineReviewFishingTrip(id: number): Observable<string> {
        return this.http.delete(this.apiUrl + '/declineReviewFishingTrip/' + id, { responseType: 'text'});
    }
}
