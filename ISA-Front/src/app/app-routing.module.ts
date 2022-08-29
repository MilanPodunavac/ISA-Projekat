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

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register-provider', component: RegisterProviderComponent },
  { path: 'fishing-instructor', component: FishingInstructorComponent },
  { path:'cottage-owner', component: CottageOwnerComponent },
  { path:'cottage-owner/cottage/:id', component: CottageOwnerCottageComponent },
  { path: 'new-cottage', component: NewCottageComponent },
  { path: 'cottage/:id/new-availability-period', component: NewCottageAvailabilityPeriodComponent },
  { path: 'cottage/:id/new-cottage-action', component: NewCottageActionComponent },
  { path: 'cottage/:id/new-cottage-reservation', component: NewCottageReservationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
