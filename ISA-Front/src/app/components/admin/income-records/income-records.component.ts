import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IncomeRecordGet } from 'src/app/model/income-record-get.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-income-records',
    templateUrl: './income-records.component.html',
    styleUrls: ['./income-records.component.scss']
})
export class IncomeRecordsComponent implements OnInit {
    displayedColumnsIncomeRecords: string[] = ['dateOfEntry', 'reserved', 'reservationPrice', 'systemTaxPercentage', 'percentageProviderKeepsIfReservationCancelled', 'systemIncome'];
    dataSourceIncomeRecords: IncomeRecordGet[];
    currentSystemTax: number;

    constructor(private router: Router, private adminService: AdminService) {
        adminService.getAllIncomeRecords().subscribe(data => {
            this.dataSourceIncomeRecords = data;
        });

        adminService.getCurrentSystemTax().subscribe(data => {
            this.currentSystemTax = Number(data);
        });
    }

    ngOnInit(): void {
    }

    public goToChangeSystemTax(): void {
        this.router.navigate(['system-tax']).then(() => {
            window.location.reload();
        });
    }
}
