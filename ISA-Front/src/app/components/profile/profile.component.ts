import { Component, OnInit } from '@angular/core';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { LoyaltyProgramProvider } from 'src/app/model/loyalty-program-provider.model';
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
    loyaltyProgramCategoryAboveInstructor: LoyaltyProgramProvider;
    
    constructor(private fishingInstructorService: FishingInstructorService, private loyaltyProgramService: LoyaltyProgramService) {
        this.role = localStorage.getItem('role');        

        this.fishingInstructorService.getLoggedInInstructor().subscribe(data => {
            this.fishingInstructorData = data;

            this.loyaltyProgramService.getOneHigherLoyaltyProviderCategory(this.fishingInstructorData.category.id).subscribe(data => {
                this.loyaltyProgramCategoryAboveInstructor = data;
            });
        });
    }

    ngOnInit(): void {
    }

}
