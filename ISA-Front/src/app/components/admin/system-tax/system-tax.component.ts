import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SystemTax } from 'src/app/model/system-tax.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-system-tax',
    templateUrl: './system-tax.component.html',
    styleUrls: ['./system-tax.component.scss']
})
export class SystemTaxComponent implements OnInit {
    systemTaxForm: FormGroup;

    constructor(formBuilder: FormBuilder, private router: Router, private adminService: AdminService) { 
        this.systemTaxForm = formBuilder.group({
            currentSystemTaxPercentage: ['', [Validators.required, Validators.min(0), Validators.max(100)]]
        });

        adminService.getCurrentSystemTax().subscribe(data => {
            this.systemTaxForm.setValue({
                currentSystemTaxPercentage: Number(data)
            });
        });
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let systemTaxRequest = new SystemTax();
        systemTaxRequest.currentSystemTaxPercentage = this.systemTaxForm.get('currentSystemTaxPercentage').value;

        this.adminService.changeSystemTax(systemTaxRequest).subscribe(data => {
            this.router.navigate(['income-records']).then(() => {
                window.location.reload();
            });
            alert(data);
        });
    }   
}
