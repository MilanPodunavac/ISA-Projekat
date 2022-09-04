import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { PointsNeeded } from 'src/app/model/points-needed.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-loyalty-system-provider-points-needed',
    templateUrl: './loyalty-system-provider-points-needed.component.html',
    styleUrls: ['./loyalty-system-provider-points-needed.component.scss']
})
export class LoyaltySystemProviderPointsNeededComponent implements OnInit {
    form: FormGroup;
    errorMessage: string;

    constructor(formBuilder: FormBuilder, private router: Router, private adminService: AdminService, private _route: ActivatedRoute) { 
        this.form = formBuilder.group({
            pointsNeeded: ['', [Validators.required, Validators.min(1)]]
        });

        let id = Number(this._route.snapshot.paramMap.get('id'));
        adminService.getLoyaltyProviderCategories().subscribe(data => {
            for (let i = 0; i < data.length; i++) {
                if (data[i].id === id) {
                    this.form.setValue({
                        pointsNeeded: Number(data[i].pointsNeeded)
                    });
                }
            }
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        this.errorMessage = "";
        let request = new PointsNeeded();
        request.pointsNeeded = this.form.get('pointsNeeded').value;

        let id = Number(this._route.snapshot.paramMap.get('id'));
        this.adminService.changePointsNeededForProvider(id, request).subscribe({
            next: data => {
                this.router.navigate(['loyalty-system']).then(() => {
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
