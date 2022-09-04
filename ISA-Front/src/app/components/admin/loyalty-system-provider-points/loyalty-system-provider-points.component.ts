import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoyaltyPointsProviderGets } from 'src/app/model/loyalty-points-provider-gets.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-loyalty-system-provider-points',
    templateUrl: './loyalty-system-provider-points.component.html',
    styleUrls: ['./loyalty-system-provider-points.component.scss']
})
export class LoyaltySystemProviderPointsComponent implements OnInit {
    form: FormGroup;

    constructor(formBuilder: FormBuilder, private router: Router, private adminService: AdminService) { 
        this.form = formBuilder.group({
            loyaltyPointsProviderGets: ['', [Validators.required, Validators.min(1)]]
        });

        adminService.getPointsProviderGetsAfterReservation().subscribe(data => {
            this.form.setValue({
                loyaltyPointsProviderGets: Number(data)
            });
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let request = new LoyaltyPointsProviderGets();
        request.currentPointsProviderGetsAfterReservation = this.form.get('loyaltyPointsProviderGets').value;

        this.adminService.changePointsProviderGets(request).subscribe(data => {
            this.router.navigate(['loyalty-system']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }   
}
