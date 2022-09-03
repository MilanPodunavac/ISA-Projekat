import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FishingTripService } from 'src/app/service/fishing-trip.service';

@Component({
    selector: 'app-edit-fishing-trip-pictures',
    templateUrl: './edit-fishing-trip-pictures.component.html',
    styleUrls: ['./edit-fishing-trip-pictures.component.scss']
})
export class EditFishingTripPicturesComponent implements OnInit {
    editPicturesForm: FormGroup;
    errorMessage: string;
    myFiles: string[];

    constructor(formBuilder: FormBuilder, private fishingTripService: FishingTripService, private _route: ActivatedRoute, private router: Router) {
        this.editPicturesForm = formBuilder.group({
            file: ['', [Validators.required]]
        });

        this.myFiles = [];
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        const formData: FormData = new FormData();

        for (var i = 0; i < this.myFiles.length; i++) { 
            formData.append("pictures", this.myFiles[i]);
        }
        
        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.fishingTripService.editPictures(id, formData).subscribe({
            next: data => {
                this.router.navigate(['fishing-trip/' + id]).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                this.errorMessage = error.error;
            }
        });
    }

    onFileSelected(event: any) {
        for (var i = 0; i < event.target.files.length; i++) { 
            this.myFiles.push(event.target.files[i]);
        }
    }
}
