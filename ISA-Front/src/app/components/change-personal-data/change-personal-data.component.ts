import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { PersonalData } from 'src/app/model/personal-data.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';

@Component({
    selector: 'app-change-personal-data',
    templateUrl: './change-personal-data.component.html',
    styleUrls: ['./change-personal-data.component.scss']
})
export class ChangePersonalDataComponent implements OnInit {
    personalDataForm: FormGroup;
    loggedInFishingInstructor: FishingInstructorGet;

    constructor(formBuilder: FormBuilder, private router: Router, private fishingInstructorService: FishingInstructorService) {
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

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let changePersonalDataRequest = new PersonalData();
        changePersonalDataRequest.firstName = this.personalDataForm.get('firstName').value;
        changePersonalDataRequest.lastName = this.personalDataForm.get('lastName').value;
        changePersonalDataRequest.address = this.personalDataForm.get('streetAddress').value;
        changePersonalDataRequest.city = this.personalDataForm.get('city').value;
        changePersonalDataRequest.country = this.personalDataForm.get('country').value;
        changePersonalDataRequest.phoneNumber = this.personalDataForm.get('phoneNumber').value;
        changePersonalDataRequest.biography = this.personalDataForm.get('biography').value;

        this.fishingInstructorService.changePersonalData(changePersonalDataRequest).subscribe(data => {
            this.router.navigate(['profile']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }
}
