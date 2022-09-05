import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BoatGet } from 'src/app/model/boat-get';
import { BoatOwnerGet } from 'src/app/model/boat-owner-get.model';
import { ClientGet } from 'src/app/model/client-get';
import { CottageGet } from 'src/app/model/cottage-get';
import { CottageOwnerGet } from 'src/app/model/cottage-owner-get.model';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-system-entities',
    templateUrl: './system-entities.component.html',
    styleUrls: ['./system-entities.component.scss']
})
export class SystemEntitiesComponent implements OnInit {
    displayedColumnsCottageOwners: string[] = ['name', 'email', 'phoneNumber', 'location', 'loyaltyPoints', 'category'];
    dataSourceCottageOwners: CottageOwnerGet[];
    clickedRowCottageOwners: CottageOwnerGet;
    displayedColumnsCottages: string[] = ['name', 'pricePerDay', 'cottageOwner', 'location', 'grade'];
    dataSourceCottages: CottageGet[];
    clickedRowCottages: CottageGet;
    displayedColumnsBoatOwners: string[] = ['name', 'email', 'phoneNumber', 'location', 'loyaltyPoints', 'category'];
    dataSourceBoatOwners: BoatOwnerGet[];
    clickedRowBoatOwners: BoatOwnerGet;
    displayedColumnsBoats: string[] = ['name', 'pricePerDay', 'boatOwner', 'location', 'grade'];
    dataSourceBoats: BoatGet[];
    clickedRowBoats: BoatGet;
    displayedColumnsFishingInstructors: string[] = ['name', 'email', 'phoneNumber', 'location', 'loyaltyPoints', 'category'];
    dataSourceFishingInstructors: FishingInstructorGet[];
    clickedRowFishingInstructors: FishingInstructorGet;
    displayedColumnsClients: string[] = ['name', 'email', 'phoneNumber', 'location', 'loyaltyPoints', 'category'];
    dataSourceClients: ClientGet[];
    clickedRowClients: ClientGet;

    constructor(private adminService: AdminService) { 
        this.adminService.getAllCottageOwners().subscribe(data => {
            this.dataSourceCottageOwners = data;
        });

        this.clickedRowCottageOwners = new CottageOwnerGet();

        this.adminService.getAllCottages().subscribe(data => {
            this.dataSourceCottages = data;
        });

        this.clickedRowCottages = new CottageGet();

        this.adminService.getAllBoatOwners().subscribe(data => {
            this.dataSourceBoatOwners = data;
        });

        this.clickedRowBoatOwners = new BoatOwnerGet();

        this.adminService.getAllBoats().subscribe(data => {
            this.dataSourceBoats = data;
        });

        this.clickedRowBoats = new BoatGet();

        this.adminService.getAllFishingInstructors().subscribe(data => {
            this.dataSourceFishingInstructors = data;
        });

        this.clickedRowFishingInstructors = new FishingInstructorGet();

        this.adminService.getAllClients().subscribe(data => {
            this.dataSourceClients = data;
        });

        this.clickedRowClients = new ClientGet();
    }

    ngOnInit(): void {
    }

    public updateClickedRowCottageOwners(row: CottageOwnerGet): void {
        if (this.clickedRowCottageOwners.id === row.id) {
            this.clickedRowCottageOwners = new CottageOwnerGet(); 
        } else {
            this.clickedRowCottageOwners = row;
        }
    }

    public updateClickedRowCottages(row: CottageGet): void {
        if (this.clickedRowCottages.id === row.id) {
            this.clickedRowCottages = new CottageGet(); 
        } else {
            this.clickedRowCottages = row;
        }
    }

    public updateClickedRowBoatOwners(row: BoatOwnerGet): void {
        if (this.clickedRowBoatOwners.id === row.id) {
            this.clickedRowBoatOwners = new BoatOwnerGet(); 
        } else {
            this.clickedRowBoatOwners = row;
        }
    }

    public updateClickedRowBoats(row: BoatGet): void {
        if (this.clickedRowBoats.id === row.id) {
            this.clickedRowBoats = new BoatGet(); 
        } else {
            this.clickedRowBoats = row;
        }
    }

    public updateClickedRowFishingInstructors(row: FishingInstructorGet): void {
        if (this.clickedRowFishingInstructors.id === row.id) {
            this.clickedRowFishingInstructors = new FishingInstructorGet(); 
        } else {
            this.clickedRowFishingInstructors = row;
        }
    }

    public updateClickedRowClients(row: ClientGet): void {
        if (this.clickedRowClients.id === row.id) {
            this.clickedRowClients = new ClientGet(); 
        } else {
            this.clickedRowClients = row;
        }
    }

    public deleteCottageOwner(): void {
        this.adminService.deleteCottageOwner(this.clickedRowCottageOwners.id).subscribe({
            next: data => {
                window.location.reload();
                alert(data);
            },
            error: error => {
                alert(error.error);
            }
        });
    }

    public deleteCottage(): void {
        this.adminService.deleteCottage(this.clickedRowCottages.id).subscribe({
            next: data => {
                window.location.reload();
                alert(data);
            },
            error: error => {
                alert(error.error);
            }
        });
    }

    public deleteBoatOwner(): void {
        this.adminService.deleteBoatOwner(this.clickedRowBoatOwners.id).subscribe({
            next: data => {
                window.location.reload();
                alert(data);
            },
            error: error => {
                alert(error.error);
            }
        });
    }

    public deleteBoat(): void {
        this.adminService.deleteBoat(this.clickedRowBoats.id).subscribe({
            next: data => {
                window.location.reload();
                alert(data);
            },
            error: error => {
                alert(error.error);
            }
        });
    }

    public deleteFishingInstructor(): void {
        this.adminService.deleteFishingInstructor(this.clickedRowFishingInstructors.id).subscribe({
            next: data => {
                window.location.reload();
                alert(data);
            },
            error: error => {
                alert(error.error);
            }
        });
    }

    public deleteClient(): void {
        this.adminService.deleteClient(this.clickedRowClients.id).subscribe({
            next: data => {
                window.location.reload();
                alert(data);
            },
            error: error => {
                alert(error.error);
            }
        });
    }
}
