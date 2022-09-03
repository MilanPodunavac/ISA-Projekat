import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientGet } from 'src/app/model/client-get';
import { FishingReservationGet } from 'src/app/model/fishing-reservation-get.model';
import { FishingTripService } from 'src/app/service/fishing-trip.service';
import { ClientService } from 'src/app/service/client.service';

@Component({
    selector: 'app-add-fishing-reservation',
    templateUrl: './add-fishing-reservation.component.html',
    styleUrls: ['./add-fishing-reservation.component.scss']
})
export class AddFishingReservationComponent implements OnInit {
    errorMessage: string;
    addReservationForm: FormGroup;
    fishingTripTags: string[];
    allClients: ClientGet[];

    constructor(formBuilder: FormBuilder, private fishingTripService: FishingTripService, private clientService: ClientService, private router: Router, private _route: ActivatedRoute) {
        this.addReservationForm = formBuilder.group({
            start: ['', [Validators.required]],
            durationInDays: ['', [Validators.required, Validators.min(1)]],
            numberOfPeople: ['', [Validators.required, Validators.min(1)]],
            fishingTripTags: [''],
            client: ['', [Validators.required]]
        });

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.fishingTripService.getFishingTrip(id).subscribe(data => {
            this.fishingTripTags = data.fishingTripReservationTags;
        });

        this.clientService.getAllClients().subscribe(data => {
            this.allClients = data;
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let addReservationRequest = new FishingReservationGet();
        addReservationRequest.start = this.addReservationForm.get('start').value;
        addReservationRequest.durationInDays = this.addReservationForm.get('durationInDays').value;
        addReservationRequest.numberOfPeople = this.addReservationForm.get('numberOfPeople').value;
        addReservationRequest.fishingTripReservationTags = this.addReservationForm.get('fishingTripTags').value;

        if (!addReservationRequest.fishingTripReservationTags) {
            addReservationRequest.fishingTripReservationTags = [];
        }

        let idFishingTrip = Number(this._route.snapshot.paramMap.get('id'));
        let idClient = this.addReservationForm.get('client').value;
        this.fishingTripService.addFishingTripReservation(idFishingTrip, idClient, addReservationRequest).subscribe({
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
