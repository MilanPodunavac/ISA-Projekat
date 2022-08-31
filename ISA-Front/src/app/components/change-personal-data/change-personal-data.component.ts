import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { PersonalData } from 'src/app/model/personal-data.model';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import { OSM } from 'ol/source';
import TileLayer from 'ol/layer/Tile';
import * as olProj from 'ol/proj';

@Component({
    selector: 'app-change-personal-data',
    templateUrl: './change-personal-data.component.html',
    styleUrls: ['./change-personal-data.component.scss']
})
export class ChangePersonalDataComponent implements OnInit {
    personalDataForm: FormGroup;
    loggedInFishingInstructor: FishingInstructorGet;
    map: Map

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
        this.map = new Map({
            layers: [
              new TileLayer({
                source: new OSM(),
              }),
            ],
            target: 'map',
            view: new View({
              center: olProj.fromLonLat([19.8366829, 45.25282]),
              zoom: 14, maxZoom: 20,
            }),
          });
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
            this.personalDataForm.setValue({city : json.address.city})
            alert(this.personalDataForm.get('city').value)
        });
      }
}
