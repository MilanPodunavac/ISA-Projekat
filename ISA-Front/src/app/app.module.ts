import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import timeGridPlugin from '@fullcalendar/timegrid';
import { AppComponent } from './app.component';
import {DatePipe} from '@angular/common';

// MDB Modules
import { MdbAccordionModule } from 'mdb-angular-ui-kit/accordion';
import { MdbCarouselModule } from 'mdb-angular-ui-kit/carousel';
import { MdbCheckboxModule } from 'mdb-angular-ui-kit/checkbox';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { MdbDropdownModule } from 'mdb-angular-ui-kit/dropdown';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { MdbPopoverModule } from 'mdb-angular-ui-kit/popover';
import { MdbRadioModule } from 'mdb-angular-ui-kit/radio';
import { MdbRangeModule } from 'mdb-angular-ui-kit/range';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbScrollspyModule } from 'mdb-angular-ui-kit/scrollspy';
import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';
import { MdbTooltipModule } from 'mdb-angular-ui-kit/tooltip';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './components/login/login.component';
import { RegisterProviderComponent } from './components/register-provider/register-provider.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptor/token-interceptor';
import { MaterialModule } from './angular-material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './service/auth.service';
import { HeaderComponent } from './components/header/header.component';
import { FishingInstructorComponent } from './components/fishing-instructor/fishing-instructor.component';
import { CottageOwnerComponent } from './components/cottage-owner/cottage-owner.component';
import { CottageOwnerCottageComponent } from './components/cottage-owner/cottage-owner-cottage/cottage-owner-cottage.component';
import { NewCottageComponent } from './components/cottage-owner/new-cottage/new-cottage.component';
import { NewCottageAvailabilityPeriodComponent } from './components/cottage-owner/new-cottage-availability-period/new-cottage-availability-period.component';
import { NewCottageActionComponent } from './components/cottage-owner/new-cottage-action/new-cottage-action.component';
import { NewCottageReservationComponent } from './components/cottage-owner/new-cottage-reservation/new-cottage-reservation.component';
import { HomePageComponent } from './home-page/home-page.component';
import { CottageListComponent } from './cottage-list/cottage-list.component';
import { BoatListComponent } from './boat-list/boat-list.component';
import { FishingInstructorListComponent } from './fishing-instructor-list/fishing-instructor-list.component';
import { FishingInstructorProfileComponent } from './fishing-instructor-profile/fishing-instructor-profile.component';
import { BoatProfileComponent } from './boat-profile/boat-profile.component';
import { CottageProfileComponent } from './cottage-profile/cottage-profile.component';
import { FishingTripProfileComponent } from './fishing-trip-profile/fishing-trip-profile.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CottageReservationViewComponent } from './components/cottage-owner/cottage-reservation-view/cottage-reservation-view.component';
import { CottageReservationOwnerCommentaryComponent } from './components/cottage-owner/cottage-reservation-owner-commentary/cottage-reservation-owner-commentary.component';
import { CottageActionOwnerCommentaryComponent } from './components/cottage-owner/cottage-action-owner-commentary/cottage-action-owner-commentary.component';
import { CottageActionViewComponent } from './components/cottage-owner/cottage-action-view/cottage-action-view.component';
import { CottageReservationComponent } from './cottage-reservation/cottage-reservation.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';
import { ProfitGraphComponent } from './components/profit-graph/profit-graph.component';
import { ChangePersonalDataComponent } from './components/change-personal-data/change-personal-data.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AccountDeletionRequestComponent } from './components/account-deletion-request/account-deletion-request.component';
import { AvailablePeriodComponent } from './components/available-period/available-period.component';
import { VisitReportComponent } from './components/visit-report/visit-report.component';
import { BoatReservationComponent } from './boat-reservation/boat-reservation.component';
import { FishingTripReservationComponent } from './fishing-trip-reservation/fishing-trip-reservation.component';
import { BoatActionOwnerCommentaryComponent } from './components/boat-owner/boat-action-owner-commentary/boat-action-owner-commentary.component';
import { BoatActionViewComponent } from './components/boat-owner/boat-action-view/boat-action-view.component';
import { BoatReservationOwnerCommentaryComponent } from './components/boat-owner/boat-reservation-owner-commentary/boat-reservation-owner-commentary.component';
import { BoatReservationViewComponent } from './components/boat-owner/boat-reservation-view/boat-reservation-view.component';
import { NewBoatComponent } from './components/boat-owner/new-boat/new-boat.component';
import { NewBoatActionComponent } from './components/boat-owner/new-boat-action/new-boat-action.component';
import { NewBoatAvailabilityPeriodComponent } from './components/boat-owner/new-boat-availability-period/new-boat-availability-period.component';
import { NewBoatReservationComponent } from './components/boat-owner/new-boat-reservation/new-boat-reservation.component';
import { BusinessReportComponent } from './components/business-report/business-report.component';
import { BoatOwnerBoatComponent } from './components/boat-owner/boat-owner-boat/boat-owner-boat.component';
import { EditFishingTripPicturesComponent } from './components/edit-fishing-trip-pictures/edit-fishing-trip-pictures.component';
import { AddFishingTripComponent } from './components/add-fishing-trip/add-fishing-trip.component';
import { EditFishingTripComponent } from './components/edit-fishing-trip/edit-fishing-trip.component';
import { AddFishingReservationComponent } from './components/add-fishing-reservation/add-fishing-reservation.component';
import { AddFishingActionComponent } from './components/add-fishing-action/add-fishing-action.component';
import { AddReservationCommentaryComponent } from './components/add-reservation-commentary/add-reservation-commentary.component';
import { AddActionCommentaryComponent } from './components/add-action-commentary/add-action-commentary.component';
import { IncomeRecordsComponent } from './components/admin/income-records/income-records.component';
import { SystemEntitiesComponent } from './components/admin/system-entities/system-entities.component';
import { ProviderRegistrationComponent } from './components/admin/provider-registration/provider-registration.component';
import { ReservationsCommentaryComponent } from './components/admin/reservations-commentary/reservations-commentary.component';
import { ReviewsComponent } from './components/admin/reviews/reviews.component';
import { ComplaintsComponent } from './components/admin/complaints/complaints.component';
import { AccountDeletionRequestsComponent } from './components/admin/account-deletion-requests/account-deletion-requests.component';
import { RegisterAdminComponent } from './components/admin/register-admin/register-admin.component';
import { BusinessReportAdminComponent } from './components/admin/business-report-admin/business-report-admin.component';
import { LoyaltySystemComponent } from './components/admin/loyalty-system/loyalty-system.component';
import { ProviderRegistrationDeclineComponent } from './components/admin/provider-registration-decline/provider-registration-decline.component';
import { LoyaltySystemClientPointsComponent } from './components/admin/loyalty-system-client-points/loyalty-system-client-points.component';
import { LoyaltySystemProviderPointsComponent } from './components/admin/loyalty-system-provider-points/loyalty-system-provider-points.component';
import { LoyaltySystemClientPointsNeededComponent } from './components/admin/loyalty-system-client-points-needed/loyalty-system-client-points-needed.component';
import { LoyaltySystemProviderPointsNeededComponent } from './components/admin/loyalty-system-provider-points-needed/loyalty-system-provider-points-needed.component';
import { AccountDeletionRequestAcceptComponent } from './components/admin/account-deletion-request-accept/account-deletion-request-accept.component';
import { AccountDeletionRequestDeclineComponent } from './components/admin/account-deletion-request-decline/account-deletion-request-decline.component';
import { ComplaintResponseComponent } from './components/admin/complaint-response/complaint-response.component';
import { ComplaintResponseFishingInstructorComponent } from './components/admin/complaint-response-fishing-instructor/complaint-response-fishing-instructor.component';
import { SystemTaxComponent } from './components/admin/system-tax/system-tax.component';
import { UserPublicInfoComponent } from './components/user-public-info/user-public-info.component';
import { NewCottageReviewComponent } from './new-cottage-review/new-cottage-review.component';

FullCalendarModule.registerPlugins([
  dayGridPlugin,
  interactionPlugin,
  timeGridPlugin
]);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterProviderComponent,
    HeaderComponent,
    FishingInstructorComponent,
    CottageOwnerComponent,
    CottageOwnerCottageComponent,
    NewCottageComponent,
    NewCottageAvailabilityPeriodComponent,
    NewCottageActionComponent,
    NewCottageReservationComponent,
    HomePageComponent,
    CottageListComponent,
    BoatListComponent,
    FishingInstructorListComponent,
    FishingInstructorProfileComponent,
    BoatProfileComponent,
    CottageProfileComponent,
    FishingTripProfileComponent,
    ProfileComponent,
    CottageReservationViewComponent,
    CottageReservationOwnerCommentaryComponent,
    CottageActionOwnerCommentaryComponent,
    CottageActionViewComponent,
    CottageReservationComponent,
    ReservationListComponent,
    ProfitGraphComponent,
    ChangePersonalDataComponent,
    ChangePasswordComponent,
    AccountDeletionRequestComponent,
    AvailablePeriodComponent,
    VisitReportComponent,
    BoatReservationComponent,
    FishingTripReservationComponent,
    BoatActionOwnerCommentaryComponent,
    BoatActionViewComponent,
    BoatReservationOwnerCommentaryComponent,
    BoatReservationViewComponent,
    NewBoatComponent,
    NewBoatActionComponent,
    NewBoatAvailabilityPeriodComponent,
    NewBoatReservationComponent,
    BusinessReportComponent,
    BoatOwnerBoatComponent,
    EditFishingTripPicturesComponent,
    AddFishingTripComponent,
    EditFishingTripComponent,
    AddFishingReservationComponent,
    AddFishingActionComponent,
    AddReservationCommentaryComponent,
    AddActionCommentaryComponent,
    IncomeRecordsComponent,
    SystemEntitiesComponent,
    ProviderRegistrationComponent,
    ReservationsCommentaryComponent,
    ReviewsComponent,
    ComplaintsComponent,
    AccountDeletionRequestsComponent,
    RegisterAdminComponent,
    BusinessReportAdminComponent,
    LoyaltySystemComponent,
    ProviderRegistrationDeclineComponent,
    LoyaltySystemClientPointsComponent,
    LoyaltySystemProviderPointsComponent,
    LoyaltySystemClientPointsNeededComponent,
    LoyaltySystemProviderPointsNeededComponent,
    AccountDeletionRequestAcceptComponent,
    AccountDeletionRequestDeclineComponent,
    ComplaintResponseComponent,
    ComplaintResponseFishingInstructorComponent,
    SystemTaxComponent,
    UserPublicInfoComponent,
    NewCottageReviewComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MdbAccordionModule,
    MdbCarouselModule,
    MdbCheckboxModule,
    MdbCollapseModule,
    MdbDropdownModule,
    MdbFormsModule,
    MdbModalModule,
    MdbPopoverModule,
    MdbRadioModule,
    MdbRangeModule,
    MdbRippleModule,
    MdbScrollspyModule,
    MdbTabsModule,
    MdbTooltipModule,
    MdbValidationModule,
    AppRoutingModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FullCalendarModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
