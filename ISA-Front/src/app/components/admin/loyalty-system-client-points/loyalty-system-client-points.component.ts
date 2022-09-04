import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoyaltyPointsClientGets } from 'src/app/model/loyalty-points-client-gets.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-loyalty-system-client-points',
    templateUrl: './loyalty-system-client-points.component.html',
    styleUrls: ['./loyalty-system-client-points.component.scss']
})
export class LoyaltySystemClientPointsComponent implements OnInit {
    form: FormGroup;

    constructor(formBuilder: FormBuilder, private router: Router, private adminService: AdminService) { 
        this.form = formBuilder.group({
            loyaltyPointsClientGets: ['', [Validators.required, Validators.min(1)]]
        });

        adminService.getPointsClientGetsAfterReservation().subscribe(data => {
            this.form.setValue({
                loyaltyPointsClientGets: Number(data)
            });
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let request = new LoyaltyPointsClientGets();
        request.currentPointsClientGetsAfterReservation = this.form.get('loyaltyPointsClientGets').value;

        this.adminService.changePointsClientGets(request).subscribe(data => {
            this.router.navigate(['loyalty-system']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }   
}
