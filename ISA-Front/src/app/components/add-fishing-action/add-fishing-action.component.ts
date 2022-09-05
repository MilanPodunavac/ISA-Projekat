import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NewFishingAction } from 'src/app/model/new-fishing-action.model';
import { ClientService } from 'src/app/service/client.service';
import { FishingTripService } from 'src/app/service/fishing-trip.service';

@Component({
    selector: 'app-add-fishing-action',
    templateUrl: './add-fishing-action.component.html',
    styleUrls: ['./add-fishing-action.component.scss']
})
export class AddFishingActionComponent implements OnInit {
    errorMessage: string;
    addActionForm: FormGroup;
    fishingTripTags: string[];

    constructor(formBuilder: FormBuilder, private fishingTripService: FishingTripService, private clientService: ClientService, private router: Router, private _route: ActivatedRoute) {
        this.addActionForm = formBuilder.group({
            start: ['', [Validators.required]],
            validUntil: ['', [Validators.required]],
            durationInDays: ['', [Validators.required, Validators.min(1)]],
            maxPeople: ['', [Validators.required, Validators.min(1)]],
            price: ['', [Validators.required, Validators.min(1)]],
            address: ['', [Validators.required]],
            city: ['', [Validators.required]],
            country: ['', [Validators.required]],
            longitude: [''],
            latitude: [''],
            fishingTripTags: ['']
        });    

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.fishingTripService.getFishingTrip(id).subscribe(data => {
            this.fishingTripTags = data.fishingTripReservationTags;
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let addActionRequest = new NewFishingAction();
        addActionRequest.start = this.addActionForm.get('start').value;
        addActionRequest.durationInDays = this.addActionForm.get('durationInDays').value;
        addActionRequest.validUntilAndIncluding = this.addActionForm.get('validUntil').value;
        addActionRequest.maxPeople = this.addActionForm.get('maxPeople').value;
        addActionRequest.price = this.addActionForm.get('price').value;
        addActionRequest.address = this.addActionForm.get('address').value;
        addActionRequest.city = this.addActionForm.get('city').value;
        addActionRequest.country = this.addActionForm.get('country').value;
        addActionRequest.longitude = this.addActionForm.get('longitude').value;
        addActionRequest.latitude = this.addActionForm.get('latitude').value;
        addActionRequest.fishingTripReservationTags = this.addActionForm.get('fishingTripTags').value;

        if (!addActionRequest.fishingTripReservationTags) {
            addActionRequest.fishingTripReservationTags = [];
        }

        let idFishingTrip = Number(this._route.snapshot.paramMap.get('id'));
        this.fishingTripService.addFishingTripAction(idFishingTrip, addActionRequest).subscribe({
            next: data => {
                this.router.navigate(['fishing-instructor-home']).then(() => {
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
