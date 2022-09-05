import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NewFishingTrip } from 'src/app/model/new-fishing-trip.model';
import { FishingTripService } from 'src/app/service/fishing-trip.service';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';

@Component({
    selector: 'app-add-fishing-trip',
    templateUrl: './add-fishing-trip.component.html',
    styleUrls: ['./add-fishing-trip.component.scss']
})
export class AddFishingTripComponent implements OnInit {
    addFishingTripForm: FormGroup;
    fishingTripTags: string[] = ['boat', 'equipment', 'lesson', 'adventure'];
    map: Map

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
        this.map = new Map({
            layers: [
                new TileLayer({
                    source: new OSM(),
                }),
            ],
            target: 'map',
            view: new View({
                center: olProj.fromLonLat([19.8162181, 45.2610478]),
                zoom: 15, maxZoom: 20,
            }),
        });
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
    getCoord(event: any) {
        var coordinate = this.map.getEventCoordinate(event);
        var lonLatCoords = olProj.toLonLat(coordinate)
        this.reverseGeocode(lonLatCoords)
    }
    reverseGeocode(coords) {
        fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
            .then(function (response) {
                return response.json();
            }).then(json => {
                console.log(json)
                this.addFishingTripForm.patchValue({longitude: json.lon, latitude: json.lat, city: json.address.city, country: json.address.country, streetAddress: json.address.road + " " + json.address.house_number})
            });
    }
}
