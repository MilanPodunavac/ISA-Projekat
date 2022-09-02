import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-edit-fishing-trip-pictures',
    templateUrl: './edit-fishing-trip-pictures.component.html',
    styleUrls: ['./edit-fishing-trip-pictures.component.scss']
})
export class EditFishingTripPicturesComponent implements OnInit {
    errorMessage: string;
    file1: any;

    constructor() { }

    ngOnInit(): void {
    }

    public formInvalid(): boolean {
        if (!this.file1) {
            return true;
        }
    }
}
