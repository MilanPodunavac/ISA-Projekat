import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ComplaintFishingInstructorGet } from 'src/app/model/complaint-fishing-instructor-get.model';
import { ComplaintGet } from 'src/app/model/complaint-get.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-complaints',
    templateUrl: './complaints.component.html',
    styleUrls: ['./complaints.component.scss']
})
export class ComplaintsComponent implements OnInit {
    displayedColumnsComplaints: string[] = ['description', 'client', 'email', 'saleEntity'];
    dataSourceComplaints: ComplaintGet[];
    clickedRowComplaints: ComplaintGet;
    displayedColumnsComplaintsFishingInstructors: string[] = ['description', 'client', 'emailClient', 'fishingInstructor', 'emailFishingInstructor'];
    dataSourceComplaintsFishingInstructors: ComplaintFishingInstructorGet[];
    clickedRowComplaintsFishingInstructors: ComplaintFishingInstructorGet;

    constructor(private router: Router, private adminService: AdminService) { 
        this.adminService.getAllComplaints().subscribe(data => {
            this.dataSourceComplaints = data;
        });

        this.clickedRowComplaints = new ComplaintGet();

        this.adminService.getAllFishingInstructorComplaints().subscribe(data => {
            this.dataSourceComplaintsFishingInstructors = data;
        });

        this.clickedRowComplaintsFishingInstructors = new ComplaintFishingInstructorGet();
    }

    ngOnInit(): void {
    }

    public updateClickedRowComplaints(row: ComplaintGet): void {
        if (this.clickedRowComplaints.id === row.id) {
            this.clickedRowComplaints = new ComplaintGet(); 
        } else {
            this.clickedRowComplaints = row;
        }
    }

    public updateClickedRowComplaintsFishingInstructors(row: ComplaintFishingInstructorGet): void {
        if (this.clickedRowComplaintsFishingInstructors.id === row.id) {
            this.clickedRowComplaintsFishingInstructors = new ComplaintFishingInstructorGet(); 
        } else {
            this.clickedRowComplaintsFishingInstructors = row;
        }
    }

    public respondToComplaint(): void {
        this.router.navigate(['complaint-response/' + this.clickedRowComplaints.id]).then(() => {
            window.location.reload();
        });
    }

    public respondToComplaintFishingInstructor(): void {
        this.router.navigate(['complaint-response-fishing-instructor/' + this.clickedRowComplaintsFishingInstructors.id]).then(() => {
            window.location.reload();
        });
    }
}
