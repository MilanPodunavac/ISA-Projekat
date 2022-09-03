import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FishingInstructorComponent } from './components/fishing-instructor/fishing-instructor.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterProviderComponent } from './components/register-provider/register-provider.component';
import { CottageOwnerComponent } from './components/cottage-owner/cottage-owner.component';
import { CottageOwnerCottageComponent } from './components/cottage-owner/cottage-owner-cottage/cottage-owner-cottage.component';
import { NewCottageComponent } from './components/cottage-owner/new-cottage/new-cottage.component';
import { NewCottageAvailabilityPeriodComponent } from './components/cottage-owner/new-cottage-availability-period/new-cottage-availability-period.component';
import { NewCottageActionComponent } from './components/cottage-owner/new-cottage-action/new-cottage-action.component'
import { NewCottageReservationComponent } from './components/cottage-owner/new-cottage-reservation/new-cottage-reservation.component'
import { HomePageComponent } from './home-page/home-page.component';
import { CottageListComponent } from './cottage-list/cottage-list.component';
import { BoatListComponent } from './boat-list/boat-list.component';
import { FishingInstructorListComponent } from './fishing-instructor-list/fishing-instructor-list.component';
import { FishingInstructorProfileComponent } from './fishing-instructor-profile/fishing-instructor-profile.component';
import { CottageProfileComponent } from './cottage-profile/cottage-profile.component';
import { BoatProfileComponent } from './boat-profile/boat-profile.component';
import { FishingTripProfileComponent } from './fishing-trip-profile/fishing-trip-profile.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CottageReservationViewComponent } from './components/cottage-owner/cottage-reservation-view/cottage-reservation-view.component';
import { CottageReservationOwnerCommentaryComponent } from './components/cottage-owner/cottage-reservation-owner-commentary/cottage-reservation-owner-commentary.component';
import { CottageActionOwnerCommentaryComponent } from './components/cottage-owner/cottage-action-owner-commentary/cottage-action-owner-commentary.component';
import { CottageActionViewComponent } from './components/cottage-owner/cottage-action-view/cottage-action-view.component';
import { ProfitGraphComponent } from './components/profit-graph/profit-graph.component';
import { ChangePersonalDataComponent } from './components/change-personal-data/change-personal-data.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { AccountDeletionRequestComponent } from './components/account-deletion-request/account-deletion-request.component';
import { AvailablePeriodComponent } from './components/available-period/available-period.component';
import { VisitReportComponent } from './components/visit-report/visit-report.component';
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

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register-provider', component: RegisterProviderComponent },
  { path: 'fishing-instructor-home', component: FishingInstructorComponent },
  { path: 'cottage-owner', component: CottageOwnerComponent },
  { path: 'cottage-owner/cottage/:id', component: CottageOwnerCottageComponent },
  { path: 'new-cottage', component: NewCottageComponent },
  { path: 'cottage/:id/new-availability-period', component: NewCottageAvailabilityPeriodComponent },
  { path: 'cottage/:id/new-cottage-action', component: NewCottageActionComponent },
  { path: 'cottage/:id/new-cottage-reservation', component: NewCottageReservationComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'cottage-owner', component: CottageOwnerComponent },
  { path: 'cottage-owner/cottage/:id', component: CottageOwnerCottageComponent },
  { path: 'cottage', component: CottageListComponent},
  { path: 'cottage/:id', component: CottageProfileComponent },
  { path: 'boat', component: BoatListComponent},
  { path: 'boat/:id', component: BoatProfileComponent },
  { path: 'fishing-instructor', component: FishingInstructorListComponent },
  { path: 'fishing-instructor/:id', component: FishingInstructorProfileComponent },
  { path: 'fishing-trip/:id', component: FishingTripProfileComponent },
  { path: 'cottage/:id/reservation/:resId', component: CottageReservationViewComponent },
  { path: 'cottage/:id/reservation/:resId/new-commentary', component: CottageReservationOwnerCommentaryComponent },
  { path: 'cottage/:id/action/:actId/new-commentary', component: CottageActionOwnerCommentaryComponent },
  { path: 'cottage/:id/action/:actId', component: CottageActionViewComponent },
  { path: 'profit', component: ProfitGraphComponent },
  { path: 'change-personal-data', component: ChangePersonalDataComponent },
  { path: 'change-password', component: ChangePasswordComponent },
  { path: 'account-deletion-request', component: AccountDeletionRequestComponent },
  { path: 'add-availability-period', component: AvailablePeriodComponent },
  { path: 'visit-report/:id', component: VisitReportComponent },
  { path: 'boat/:id/action/:actId/new-commentary', component: BoatActionOwnerCommentaryComponent },
  { path: 'boat/:id/action/:actId', component: BoatActionViewComponent },
  { path: 'boat/:id/reservation/:resId', component: BoatReservationViewComponent },
  { path: 'boat/:id/reservation/:resId/new-commentary', component: BoatReservationOwnerCommentaryComponent },
  { path: 'boat/:id/new-availability-period', component: NewBoatAvailabilityPeriodComponent },
  { path: 'boat/:id/new-boat-reservation', component: NewBoatReservationComponent },
  { path: 'boat/:id/new-boat-action', component: NewBoatActionComponent },
  { path: 'new-boat', component: NewBoatComponent },
  { path: 'business-report', component: BusinessReportComponent },
  { path: 'boat-owner/boat/:id', component: BoatOwnerBoatComponent },
  { path: 'business-report', component: BusinessReportComponent },
  { path: 'edit-fishing-trip-pictures/:id', component: EditFishingTripPicturesComponent },
  { path: 'add-fishing-trip', component: AddFishingTripComponent },
  { path: 'edit-fishing-trip/:id', component: EditFishingTripComponent },
  { path: ':id/add-fishing-reservation', component: AddFishingReservationComponent },
  { path: ':id/add-fishing-action', component: AddFishingActionComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
