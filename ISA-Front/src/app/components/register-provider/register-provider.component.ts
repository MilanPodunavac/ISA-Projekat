import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterProvider } from 'src/app/model/register-provider';
import { RegistrationService } from 'src/app/service/registration.service';

@Component({
    selector: 'app-register-provider',
    templateUrl: './register-provider.component.html',
    styleUrls: ['./register-provider.component.scss']
})
export class RegisterProviderComponent implements OnInit {
    registrationForm: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private router: Router, private registrationService: RegistrationService) {
        this.registrationForm = formBuilder.group({
            firstName: ['', [Validators.required]],
            lastName: ['', [Validators.required]],
            streetAddress: ['', [Validators.required]],
            city: ['', [Validators.required]],
            country: ['', [Validators.required]],
            phoneNumber: ['', [Validators.required, Validators.pattern("[0-9]{6,12}")]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(3)]],
            repeatPassword: ['', [Validators.required, Validators.minLength(3)]],
            providerType: ['', [Validators.required]],
            reasonForRegistration: ['', [Validators.required]],
            biography: ['']
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let registrationRequest = new RegisterProvider();
        registrationRequest.firstName = this.registrationForm.get('firstName').value;
        registrationRequest.lastName = this.registrationForm.get('lastName').value;
        registrationRequest.address = this.registrationForm.get('streetAddress').value;
        registrationRequest.city = this.registrationForm.get('city').value;
        registrationRequest.country = this.registrationForm.get('country').value;
        registrationRequest.phoneNumber = this.registrationForm.get('phoneNumber').value;
        registrationRequest.email = this.registrationForm.get('email').value;
        registrationRequest.password = this.registrationForm.get('password').value;
        registrationRequest.providerType = this.registrationForm.get('providerType').value;
        registrationRequest.reasonForRegistration = this.registrationForm.get('reasonForRegistration').value;
        registrationRequest.biography = this.registrationForm.get('biography').value;

        this.registrationService.register(registrationRequest).subscribe({
            next: data => {
                this.router.navigate([''])
                alert(data);
            },
            error: error => {
                this.errorMessage = error.error;
            }
        });
    }   
}
