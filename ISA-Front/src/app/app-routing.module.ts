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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
