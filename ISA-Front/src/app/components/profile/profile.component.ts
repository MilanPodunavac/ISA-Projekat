import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminGet } from 'src/app/model/admin-get.model';
import { ClientGet } from 'src/app/model/client-get';
import { FishingInstructorAvailablePeriodGet } from 'src/app/model/fishing-instructor-available-period-get.model';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { LoyaltyProgramProvider } from 'src/app/model/loyalty-program-provider.model';
import { AdminService } from 'src/app/service/admin.service';
import { ClientService } from 'src/app/service/client.service';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { LoyaltyProgramService } from 'src/app/service/loyalty-program.service';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    role: string;
    fishingInstructorData: FishingInstructorGet;
    adminData: AdminGet;
    clientData: ClientGet;
    loyaltyProgramCategoryAboveInstructor: LoyaltyProgramProvider;
    displayedColumnsFishingInstructorAvailablePeriods: string[] = ['available_from', 'available_to'];
    dataSourceFishingInstructorAvailablePeriods: FishingInstructorAvailablePeriodGet[];
    user: any;
    
    constructor(private fishingInstructorService: FishingInstructorService, private clientService: ClientService, private loyaltyProgramService: LoyaltyProgramService, private _usersService: UserService, private router: Router, private adminService: AdminService) {
        
        this.role = localStorage.getItem('role');        
        
        if(this.role === 'ROLE_FISHING_INSTRUCTOR'){
            this.fishingInstructorService.getLoggedInInstructor().subscribe(data => {
                this.fishingInstructorData = data;

                this.loyaltyProgramService.getOneHigherLoyaltyProviderCategory(this.fishingInstructorData.category.id).subscribe(data => {
                    this.loyaltyProgramCategoryAboveInstructor = data;
                });
            });

            this.fishingInstructorService.getFishingInstructorAvailablePeriods().subscribe(data => {
                this.dataSourceFishingInstructorAvailablePeriods = data;
            });
        }
        if(this.role === 'ROLE_ADMIN'){
            this.adminService.getLoggedInAdmin().subscribe(data => {
                this.adminData = data;
            });
        }
        if(this.role === 'ROLE_CLIENT'){
            this.clientService.getLoggedInClient().subscribe(data => {
                console.log(data)
                this.clientData = data;
            });
        }
        if(this.role === "ROLE_COTTAGE_OWNER" || this.role === "ROLE_BOAT_OWNER"){
            this._usersService.getLoggedInUser().subscribe(
                {next: data => {
                  this.user = data;
                  this.loyaltyProgramService.getOneHigherLoyaltyProviderCategory(this.user.category.id).subscribe(data => {
                    this.loyaltyProgramCategoryAboveInstructor = data;
                    });
                },
                error: data => {
                  console.log(data)
                  alert(data.error)
                }
            });
        }
    }

    ngOnInit(): void {
    }

    public goToChangePersonalData(): void {
        this.router.navigate(['change-personal-data']).then(() => {
            window.location.reload();
        });
    }

    public goToChangePassword(): void {
        this.router.navigate(['change-password']).then(() => {
            window.location.reload();
        });
    }

    public goToAccountDeletion(): void {
        this.router.navigate(['account-deletion-request']).then(() => {
            window.location.reload();
        });
    }

    public goToAddAvailabilityPeriod(): void {
        this.router.navigate(['add-availability-period']).then(() => {
            window.location.reload();
        });
    }

    public goToBusinessReport(): void {
        if (this.role === 'ROLE_ADMIN') {
            this.router.navigate(['business-report-admin']).then(() => {
                window.location.reload();
            });
        } else if (this.role === 'ROLE_FISHING_INSTRUCTOR') {
            this.router.navigate(['business-report']).then(() => {
                window.location.reload();
            });
        }
    }
}
