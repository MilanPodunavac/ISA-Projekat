import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DeclineReason } from 'src/app/model/decline-reason.model';
import { RegistrationService } from 'src/app/service/registration.service';

@Component({
    selector: 'app-provider-registration-decline',
    templateUrl: './provider-registration-decline.component.html',
    styleUrls: ['./provider-registration-decline.component.scss']
})
export class ProviderRegistrationDeclineComponent implements OnInit {
    form: FormGroup;
    
    constructor(formBuilder: FormBuilder, private _route: ActivatedRoute, private router: Router, private registrationService: RegistrationService) { 
        this.form = formBuilder.group({
            declineReason: ['', [Validators.required]]
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let request = new DeclineReason();
        request.declineReason = this.form.get('declineReason').value;

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.registrationService.declineProviderRegistrationRequest(id, request).subscribe(data => {
            this.router.navigate(['provider-registration']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }   
}
