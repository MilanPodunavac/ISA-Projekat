import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterProvider } from 'src/app/model/register-provider';
import { RegistrationService } from 'src/app/service/registration.service';

@Component({
    selector: 'app-provider-registration',
    templateUrl: './provider-registration.component.html',
    styleUrls: ['./provider-registration.component.scss']
})
export class ProviderRegistrationComponent implements OnInit {
    displayedColumnsProviderRequests: string[] = ['name', 'email', 'phoneNumber', 'location', 'reasonForRegistration'];
    dataSourceProviderRequests: RegisterProvider[];
    clickedRowProviderRequests: RegisterProvider;

    constructor(private router: Router, private registrationService: RegistrationService) { 
        this.registrationService.getAllProviderRegistrationRequests().subscribe(data => {
            this.dataSourceProviderRequests = data;
        });

        this.clickedRowProviderRequests = new RegisterProvider();
    }

    ngOnInit(): void {
    }

    public updateClickedRowProviderRequests(row: RegisterProvider): void {
        if (this.clickedRowProviderRequests.id === row.id) {
            this.clickedRowProviderRequests = new RegisterProvider(); 
        } else {
            this.clickedRowProviderRequests = row;
        }
    }

    public acceptProviderRequest(): void {
        this.registrationService.acceptProviderRegistrationRequest(this.clickedRowProviderRequests.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public declineProviderRequest(): void {
        this.router.navigate(['provider-registration/' + this.clickedRowProviderRequests.id + '/decline']).then(() => {
            window.location.reload();
        });
    }
}
