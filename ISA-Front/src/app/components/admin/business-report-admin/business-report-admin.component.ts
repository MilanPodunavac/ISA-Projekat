import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfitInInterval } from 'src/app/model/profit-in-interval.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-business-report-admin',
    templateUrl: './business-report-admin.component.html',
    styleUrls: ['./business-report-admin.component.scss']
})
export class BusinessReportAdminComponent implements OnInit {
    profitForm: FormGroup;
    profitInInterval: number;

    constructor(formBuilder: FormBuilder, private adminService: AdminService) {
        this.profitForm = formBuilder.group({
            from: ['', [Validators.required]],
            to: ['', [Validators.required]]
        });
        
        this.profitInInterval = 0;
    }

    ngOnInit(): void {
    }

    public onSubmit(): void {
        let incomeInTimeIntervalRequest = new ProfitInInterval();
        incomeInTimeIntervalRequest.from = this.profitForm.get('from').value;
        incomeInTimeIntervalRequest.to = this.profitForm.get('to').value;
        this.adminService.incomeInTimeInterval(incomeInTimeIntervalRequest).subscribe(data => {
            this.profitInInterval = Number(data);
        });
    }   
}
