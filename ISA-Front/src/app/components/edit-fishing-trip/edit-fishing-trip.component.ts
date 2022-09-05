import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NewFishingTrip } from 'src/app/model/new-fishing-trip.model';
import { FishingTripService } from 'src/app/service/fishing-trip.service';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';

@Component({
    selector: 'app-edit-fishing-trip',
    templateUrl: './edit-fishing-trip.component.html',
    styleUrls: ['./edit-fishing-trip.component.scss']
})
export class EditFishingTripComponent implements OnInit {
    editFishingTripForm: FormGroup;
    fishingTripTags: string[] = ['boat', 'equipment', 'lesson', 'adventure'];
    errorMessage: string;
    map: Map

    constructor(formBuilder: FormBuilder, private fishingTripService: FishingTripService, private router: Router, private _route: ActivatedRoute) {
        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.fishingTripService.getFishingTrip(id).subscribe(data => {
            this.editFishingTripForm = formBuilder.group({
                name: [data.name, [Validators.required]],
                maxPeople: [data.maxPeople, [Validators.required, Validators.min(1)]],
                description: [data.description, [Validators.required]],
                rules: [data.rules, [Validators.required]],
                equipment: [data.equipment, [Validators.required]],
                longitude: [data.location.longitude],
                latitude: [data.location.latitude],
                costPerDay: [data.costPerDay, [Validators.required, Validators.min(1)]],
                cancellationFee: [data.percentageInstructorKeepsIfReservationCancelled, [Validators.required, Validators.min(0), Validators.max(100)]],
                streetAddress: [data.location.streetName, [Validators.required]],
                city: [data.location.cityName, [Validators.required]],
                country: [data.location.countryName, [Validators.required]],
                fishingTripTags: [data.fishingTripReservationTags]
            });
            this.map = new Map({
                layers: [
                  new TileLayer({
                    source: new OSM(),
                  }),
                ],
                target: 'map',
                view: new View({
                  center: olProj.fromLonLat([data.location.longitude, data.location.latitude]),
                  zoom: 15, maxZoom: 20,
                }),
              });
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let editFishingTripRequest = new NewFishingTrip();
        editFishingTripRequest.id = Number(this._route.snapshot.paramMap.get('id'));
        editFishingTripRequest.name = this.editFishingTripForm.get('name').value;
        editFishingTripRequest.maxPeople = this.editFishingTripForm.get('maxPeople').value;
        editFishingTripRequest.costPerDay = this.editFishingTripForm.get('costPerDay').value;
        editFishingTripRequest.percentageInstructorKeepsIfReservationCancelled = this.editFishingTripForm.get('cancellationFee').value;
        editFishingTripRequest.description = this.editFishingTripForm.get('description').value;
        editFishingTripRequest.rules = this.editFishingTripForm.get('rules').value;
        editFishingTripRequest.equipment = this.editFishingTripForm.get('equipment').value;
        editFishingTripRequest.address = this.editFishingTripForm.get('streetAddress').value;
        editFishingTripRequest.city = this.editFishingTripForm.get('city').value;
        editFishingTripRequest.country = this.editFishingTripForm.get('country').value;
        editFishingTripRequest.longitude = this.editFishingTripForm.get('longitude').value;
        editFishingTripRequest.latitude = this.editFishingTripForm.get('latitude').value;
        editFishingTripRequest.fishingTripReservationTags = this.editFishingTripForm.get('fishingTripTags').value;

        if (!editFishingTripRequest.fishingTripReservationTags) {
            editFishingTripRequest.fishingTripReservationTags = [];
        }

        this.fishingTripService.editFishingTrip(editFishingTripRequest).subscribe({
            next: data => {
                this.router.navigate(['fishing-trip/' + editFishingTripRequest.id]).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                this.errorMessage = error.error;
            }
        });
    }
    getCoord(event: any){
        var coordinate = this.map.getEventCoordinate(event);
        var lonLatCoords = olProj.toLonLat(coordinate)
        this.reverseGeocode(lonLatCoords)
     }
      reverseGeocode(coords) {
        fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
        .then(function(response) {
            return response.json();
        }).then(json => {
            console.log(json)
            this.editFishingTripForm.patchValue({longitude: json.lon, latitude: json.lat, city: json.address.city, country: json.address.country, streetAddress: json.address.road + " " + json.address.house_number})
        });
      } 
}
