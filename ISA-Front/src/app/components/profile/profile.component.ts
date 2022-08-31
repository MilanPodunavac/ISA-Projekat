import { Component, OnInit } from '@angular/core';
import { ClientGet } from 'src/app/model/client-get';
import { FishingInstructorAvailablePeriodGet } from 'src/app/model/fishing-instructor-available-period-get.model';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { LoyaltyProgramProvider } from 'src/app/model/loyalty-program-provider.model';
import { ClientService } from 'src/app/service/client.service';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { LoyaltyProgramService } from 'src/app/service/loyalty-program.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    role: string;
    fishingInstructorData: FishingInstructorGet;
    clientData: ClientGet;
    loyaltyProgramCategoryAboveInstructor: LoyaltyProgramProvider;
    displayedColumnsFishingInstructorAvailablePeriods: string[] = ['available_from', 'available_to'];
    dataSourceFishingInstructorAvailablePeriods: FishingInstructorAvailablePeriodGet[];
    
    constructor(private fishingInstructorService: FishingInstructorService, private clientService: ClientService, private loyaltyProgramService: LoyaltyProgramService) {
        
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
        if(this.role === 'ROLE_CLIENT'){
            this.clientService.getLoggedInClient().subscribe(data => {
                console.log(data)
                this.clientData = data;
            });
        }
    }

    ngOnInit(): void {
    }

}
