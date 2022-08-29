import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FishingInstructorComponent } from './components/fishing-instructor/fishing-instructor.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterProviderComponent } from './components/register-provider/register-provider.component';
import { CottageOwnerComponent } from './components/cottage-owner/cottage-owner.component';
import { CottageOwnerCottageComponent } from './components/cottage-owner/cottage-owner-cottage/cottage-owner-cottage.component';
import { HomePageComponent } from './home-page/home-page.component';
import { CottageListComponent } from './cottage-list/cottage-list.component';
import { BoatListComponent } from './boat-list/boat-list.component';
import { FishingInstructorListComponent } from './fishing-instructor-list/fishing-instructor-list.component';
import { FishingInstructorProfileComponent } from './fishing-instructor-profile/fishing-instructor-profile.component';
import { CottageProfileComponent } from './cottage-profile/cottage-profile.component';
import { BoatProfileComponent } from './boat-profile/boat-profile.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register-provider', component: RegisterProviderComponent },
  { path: 'fishing-instructor-profile', component: FishingInstructorComponent },
  { path: 'cottage-owner', component: CottageOwnerComponent },
  { path: 'cottage-owner/cottage/:id', component: CottageOwnerCottageComponent },
  { path: 'cottage', component: CottageListComponent},
  { path: 'cottage/:id', component: CottageProfileComponent },
  { path: 'boat', component: BoatListComponent},
  { path: 'boat/:id', component: BoatProfileComponent },
  { path: 'fishing-instructor', component: FishingInstructorListComponent },
  { path: 'fishing-instructor/:id', component: FishingInstructorProfileComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
