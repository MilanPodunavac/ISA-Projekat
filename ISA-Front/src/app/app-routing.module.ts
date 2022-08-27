import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FishingInstructorComponent } from './components/fishing-instructor/fishing-instructor.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterProviderComponent } from './components/register-provider/register-provider.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register-provider', component: RegisterProviderComponent },
  { path: 'fishing-instructor', component: FishingInstructorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
