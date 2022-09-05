import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterProvider } from 'src/app/model/register-provider';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-register-admin',
    templateUrl: './register-admin.component.html',
    styleUrls: ['./register-admin.component.scss']
})
export class RegisterAdminComponent implements OnInit {
    registrationForm: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private router: Router, private adminService: AdminService) {
        this.registrationForm = formBuilder.group({
            firstName: ['', [Validators.required]],
            lastName: ['', [Validators.required]],
            streetAddress: ['', [Validators.required]],
            city: ['', [Validators.required]],
            country: ['', [Validators.required]],
            longitude: [''],
            latitude: [''],
            phoneNumber: ['', [Validators.required, Validators.pattern("[0-9]{6,12}")]],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(3)]],
            repeatPassword: ['', [Validators.required, Validators.minLength(3)]]
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
        registrationRequest.longitude = this.registrationForm.get('longitude').value;
        registrationRequest.latitude = this.registrationForm.get('latitude').value;
        registrationRequest.phoneNumber = this.registrationForm.get('phoneNumber').value;
        registrationRequest.email = this.registrationForm.get('email').value;
        registrationRequest.password = this.registrationForm.get('password').value;

        this.adminService.registerAdmin(registrationRequest).subscribe({
            next: data => {
                this.router.navigate(['income-records']).then(() => {
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
