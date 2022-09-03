import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { PersonalData } from 'src/app/model/personal-data.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-change-personal-data',
  templateUrl: './change-personal-data.component.html',
  styleUrls: ['./change-personal-data.component.scss']
})
export class ChangePersonalDataComponent implements OnInit {
  personalDataForm: FormGroup;
  loggedInFishingInstructor: FishingInstructorGet;
  role: string
  userPersonalData: any;

  constructor(formBuilder: FormBuilder, private router: Router, private fishingInstructorService: FishingInstructorService, private _userService: UserService) {
    this.role = localStorage.getItem('role');
    if (this.role === "ROLE_FISHING_INSTRUCTOR") {
      this.fishingInstructorService.getLoggedInInstructor().subscribe(data => {
        this.loggedInFishingInstructor = data;
        this.personalDataForm = formBuilder.group({
          firstName: [this.loggedInFishingInstructor.firstName, [Validators.required]],
          lastName: [this.loggedInFishingInstructor.lastName, [Validators.required]],
          streetAddress: [this.loggedInFishingInstructor.location.streetName, [Validators.required]],
          city: [this.loggedInFishingInstructor.location.cityName, [Validators.required]],
          country: [this.loggedInFishingInstructor.location.countryName, [Validators.required]],
          phoneNumber: [this.loggedInFishingInstructor.phoneNumber, [Validators.required, Validators.pattern("[0-9]{6,12}")]],
          biography: [this.loggedInFishingInstructor.biography]
        });
      });
    }
    if (this.role === "ROLE_COTTAGE_OWNER" || this.role === "ROLE_BOAT_OWNER") {
      this._userService.getLoggedInUser().subscribe(data => {
        this.userPersonalData = data;

        this.personalDataForm = formBuilder.group({
          firstName: [this.userPersonalData.firstName, [Validators.required]],
          lastName: [this.userPersonalData.lastName, [Validators.required]],
          streetAddress: [this.userPersonalData.location.streetName, [Validators.required]],
          city: [this.userPersonalData.location.cityName, [Validators.required]],
          country: [this.userPersonalData.location.countryName, [Validators.required]],
          phoneNumber: [this.userPersonalData.phoneNumber, [Validators.required, Validators.pattern("[0-9]{6,12}")]],
        });
      });
    }

  }
  ngOnInit(): void {

  }
  public onSubmit(): void {
    let changePersonalDataRequest = new PersonalData();
    if(this.role === "ROLE_FISHING_INSTRUCTOR"){
      changePersonalDataRequest.firstName = this.personalDataForm.get('firstName').value;
      changePersonalDataRequest.lastName = this.personalDataForm.get('lastName').value;
      changePersonalDataRequest.address = this.personalDataForm.get('streetAddress').value;
      changePersonalDataRequest.city = this.personalDataForm.get('city').value;
      changePersonalDataRequest.country = this.personalDataForm.get('country').value;
      changePersonalDataRequest.longitude = this.personalDataForm.get('longitude').value;
      changePersonalDataRequest.latitude = this.personalDataForm.get('latitude').value;
      changePersonalDataRequest.phoneNumber = this.personalDataForm.get('phoneNumber').value;
      changePersonalDataRequest.biography = this.personalDataForm.get('biography').value;
  
  
      this.fishingInstructorService.changePersonalData(changePersonalDataRequest).subscribe(data => {
        this.router.navigate(['profile']).then(() => {
          window.location.reload();
        });
        alert(data);
      });
    }
    if (this.role === "ROLE_COTTAGE_OWNER" || this.role === "ROLE_BOAT_OWNER") {
      var body = {
        firstName: this.personalDataForm.get('firstName').value,
        lastName: this.personalDataForm.get('lastName').value,
        phoneNumber: this.personalDataForm.get('phoneNumber').value,
        streetName: this.personalDataForm.get('streetAddress').value,
        cityName: this.personalDataForm.get('city').value,
        countryName: this.personalDataForm.get('country').value,
        longitude: this.personalDataForm.get('longitude').value,
        latitude: this.personalDataForm.get('latitude').value,
      }
      this._userService.updatePersonalInfo(body).subscribe(data => {
        this.router.navigate(['profile']).then(() => {
          window.location.reload();
        });
        alert(data);
      })
    }
  }
}
