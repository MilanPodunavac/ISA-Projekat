import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { OwnerCommentary } from 'src/app/model/owner-commentary.model';
import { ClientService } from 'src/app/service/client.service';
import { FishingTripService } from 'src/app/service/fishing-trip.service';

@Component({
    selector: 'app-add-action-commentary',
    templateUrl: './add-action-commentary.component.html',
    styleUrls: ['./add-action-commentary.component.scss']
})
export class AddActionCommentaryComponent implements OnInit {
    commentaryForm: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private fishingTripService: FishingTripService, private clientService: ClientService, private router: Router, private _route: ActivatedRoute) {
        this.commentaryForm = formBuilder.group({
            comment: ['', [Validators.required]],
            clientCame: [''],
            sanctionSuggested: ['']
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let commentaryRequest = new OwnerCommentary();
        commentaryRequest.commentary = this.commentaryForm.get('comment').value;

        if (this.commentaryForm.get('clientCame').value === '') {
            commentaryRequest.clientCame = false;
        } else {
            commentaryRequest.clientCame = this.commentaryForm.get('clientCame').value;
        }

        if (this.commentaryForm.get('sanctionSuggested').value === '') {
            commentaryRequest.sanctionSuggested = false;
        } else {
            commentaryRequest.sanctionSuggested = this.commentaryForm.get('sanctionSuggested').value;
        }

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.fishingTripService.addActionCommentary(id, commentaryRequest).subscribe({
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
