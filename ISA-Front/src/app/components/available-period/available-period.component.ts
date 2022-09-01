import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FishingInstructorAvailablePeriodGet } from 'src/app/model/fishing-instructor-available-period-get.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';

@Component({
    selector: 'app-available-period',
    templateUrl: './available-period.component.html',
    styleUrls: ['./available-period.component.scss']
})
export class AvailablePeriodComponent implements OnInit {
    availablePeriodForm: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private router: Router, private fishingInstructorService: FishingInstructorService) {
        this.availablePeriodForm = formBuilder.group({
            availableFrom: ['', [Validators.required]],
            availableTo: ['', [Validators.required]]
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let availabilityPeriodRequest = new FishingInstructorAvailablePeriodGet();
        availabilityPeriodRequest.availableFrom = this.availablePeriodForm.get('availableFrom').value;
        availabilityPeriodRequest.availableTo = this.availablePeriodForm.get('availableTo').value;

        this.fishingInstructorService.addAvailabilityPeriod(availabilityPeriodRequest).subscribe({
            next: data => {
                this.router.navigate(['profile']).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                this.errorMessage = error.error;
            }
        });
    }   
}
