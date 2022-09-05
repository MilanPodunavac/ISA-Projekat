import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoyaltyProgramClient } from 'src/app/model/loyalty-program-client.model';
import { LoyaltyProgramProvider } from 'src/app/model/loyalty-program-provider.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-loyalty-system',
    templateUrl: './loyalty-system.component.html',
    styleUrls: ['./loyalty-system.component.scss']
})
export class LoyaltySystemComponent implements OnInit {
    displayedColumnsLoyaltyProgramClient: string[] = ['category', 'pointsNeeded', 'discountPercentage'];
    dataSourceLoyaltyProgramClient: LoyaltyProgramClient[];
    clickedRowLoyaltyProgramClient: LoyaltyProgramClient;
    displayedColumnsLoyaltyProgramProvider: string[] = ['category', 'pointsNeeded', 'lesserSystemTaxPercentage'];
    dataSourceLoyaltyProgramProvider: LoyaltyProgramProvider[];
    clickedRowLoyaltyProgramProvider: LoyaltyProgramProvider;
    currentPointsProviderGets: number;
    currentPointsClientGets: number;

    constructor(private router: Router, private adminService: AdminService) { 
        this.adminService.getLoyaltyClientCategories().subscribe(data => {
            data = data.sort(({pointsNeeded: a}, {pointsNeeded: b}) => a - b);
            this.dataSourceLoyaltyProgramClient = data;
        });

        this.clickedRowLoyaltyProgramClient = new LoyaltyProgramClient();

        adminService.getPointsClientGetsAfterReservation().subscribe(data => {
            this.currentPointsClientGets = Number(data);
        });

        this.adminService.getLoyaltyProviderCategories().subscribe(data => {
            data = data.sort(({pointsNeeded: a}, {pointsNeeded: b}) => a - b);
            this.dataSourceLoyaltyProgramProvider = data;
        });

        this.clickedRowLoyaltyProgramProvider = new LoyaltyProgramProvider();

        adminService.getPointsProviderGetsAfterReservation().subscribe(data => {
            this.currentPointsProviderGets = Number(data);
        });
    }

    ngOnInit(): void {
    }

    public updateClickedRowLoyaltyProgramClient(row: LoyaltyProgramClient): void {
        if (this.clickedRowLoyaltyProgramClient.id === row.id) {
            this.clickedRowLoyaltyProgramClient = new LoyaltyProgramClient(); 
        } else {
            this.clickedRowLoyaltyProgramClient = row;
        }
    }

    public updateClickedRowLoyaltyProgramProvider(row: LoyaltyProgramProvider): void {
        if (this.clickedRowLoyaltyProgramProvider.id === row.id) {
            this.clickedRowLoyaltyProgramProvider = new LoyaltyProgramProvider(); 
        } else {
            this.clickedRowLoyaltyProgramProvider = row;
        }
    }

    public goToChangePointsNeededClient(): void {
        this.router.navigate(['loyalty-system/client-category/' + this.clickedRowLoyaltyProgramClient.id + '/points-needed']).then(() => {
            window.location.reload();
        });
    }

    public goToChangePointsNeededProvider(): void {
        this.router.navigate(['loyalty-system/provider-category/' + this.clickedRowLoyaltyProgramProvider.id + '/points-needed']).then(() => {
            window.location.reload();
        });
    }

    public goToChangePointsClientGets(): void {
        this.router.navigate(['loyalty-system/client-points']).then(() => {
            window.location.reload();
        });
    }

    public goToChangePointsProviderGets(): void {
        this.router.navigate(['loyalty-system/provider-points']).then(() => {
            window.location.reload();
        });
    }
}
