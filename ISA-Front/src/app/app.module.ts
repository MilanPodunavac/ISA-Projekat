import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';

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
import { ProfitGraphComponent } from './components/profit-graph/profit-graph.component';
import { ChangePersonalDataComponent } from './components/change-personal-data/change-personal-data.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';

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
    ProfitGraphComponent,
    ChangePersonalDataComponent,
    ChangePasswordComponent,
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
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
