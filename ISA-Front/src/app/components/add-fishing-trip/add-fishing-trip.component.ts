import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NewFishingTrip } from 'src/app/model/new-fishing-trip.model';
import { FishingTripService } from 'src/app/service/fishing-trip.service';

@Component({
    selector: 'app-add-fishing-trip',
    templateUrl: './add-fishing-trip.component.html',
    styleUrls: ['./add-fishing-trip.component.scss']
})
export class AddFishingTripComponent implements OnInit {
    addFishingTripForm: FormGroup;
    fishingTripTags: string[] = ['boat', 'equipment', 'lesson', 'adventure'];

    constructor(formBuilder: FormBuilder, private fishingTripService: FishingTripService, private router: Router) {
        this.addFishingTripForm = formBuilder.group({
            name: ['', [Validators.required]],
            maxPeople: ['', [Validators.required, Validators.min(1)]],
            description: ['', [Validators.required]],
            rules: ['', [Validators.required]],
            equipment: ['', [Validators.required]],
            longitude: [''],
            latitude: [''],
            costPerDay: ['', [Validators.required, Validators.min(1)]],
            cancellationFee: ['', [Validators.required, Validators.min(1), Validators.max(100)]],
            streetAddress: ['', [Validators.required]],
            city: ['', [Validators.required]],
            country: ['', [Validators.required]],
            fishingTripTags: ['']
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let addFishingTripRequest = new NewFishingTrip();
        addFishingTripRequest.name = this.addFishingTripForm.get('name').value;
        addFishingTripRequest.maxPeople = this.addFishingTripForm.get('maxPeople').value;
        addFishingTripRequest.costPerDay = this.addFishingTripForm.get('costPerDay').value;
        addFishingTripRequest.percentageInstructorKeepsIfReservationCancelled = this.addFishingTripForm.get('cancellationFee').value;
        addFishingTripRequest.description = this.addFishingTripForm.get('description').value;
        addFishingTripRequest.rules = this.addFishingTripForm.get('rules').value;
        addFishingTripRequest.equipment = this.addFishingTripForm.get('equipment').value;
        addFishingTripRequest.address = this.addFishingTripForm.get('streetAddress').value;
        addFishingTripRequest.city = this.addFishingTripForm.get('city').value;
        addFishingTripRequest.country = this.addFishingTripForm.get('country').value;
        addFishingTripRequest.longitude = this.addFishingTripForm.get('longitude').value;
        addFishingTripRequest.latitude = this.addFishingTripForm.get('latitude').value;
        addFishingTripRequest.fishingTripReservationTags = this.addFishingTripForm.get('fishingTripTags').value;

        if (!addFishingTripRequest.fishingTripReservationTags) {
            addFishingTripRequest.fishingTripReservationTags = [];
        }

        this.fishingTripService.addFishingTrip(addFishingTripRequest).subscribe(data => {
            this.router.navigate(['fishing-instructor-home']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }  
}
