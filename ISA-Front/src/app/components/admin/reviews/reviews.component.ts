import { Component, OnInit } from '@angular/core';
import { ReviewFishingTripGet } from 'src/app/model/review-fishing-trip-get.model';
import { ReviewGet } from 'src/app/model/review-get.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-reviews',
    templateUrl: './reviews.component.html',
    styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {
    displayedColumnsReviews: string[] = ['description', 'grade', 'client', 'email', 'saleEntity'];
    dataSourceReviews: ReviewGet[];
    clickedRowReviews: ReviewGet;
    displayedColumnsFishingTripReviews: string[] = ['description', 'grade', 'client', 'email', 'fishingTrip'];
    dataSourceFishingTripReviews: ReviewFishingTripGet[];
    clickedRowFishingTripReviews: ReviewFishingTripGet;

    constructor(private adminService: AdminService) { 
        this.adminService.getAllUnapprovedReviews().subscribe(data => {
            this.dataSourceReviews = data;
        });

        this.clickedRowReviews = new ReviewGet();

        this.adminService.getAllUnapprovedFishingTripReviews().subscribe(data => {
            this.dataSourceFishingTripReviews = data;
        });

        this.clickedRowFishingTripReviews = new ReviewFishingTripGet();
    }

    ngOnInit(): void {
    }

    public updateClickedRowReviews(row: ReviewGet): void {
        if (this.clickedRowReviews.id === row.id) {
            this.clickedRowReviews = new ReviewGet(); 
        } else {
            this.clickedRowReviews = row;
        }
    }

    public updateClickedRowFishingTripReviews(row: ReviewFishingTripGet): void {
        if (this.clickedRowFishingTripReviews.id === row.id) {
            this.clickedRowFishingTripReviews = new ReviewFishingTripGet(); 
        } else {
            this.clickedRowFishingTripReviews = row;
        }
    }

    public acceptReview(): void {
        this.adminService.acceptReview(this.clickedRowReviews.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public acceptFishingTripReview(): void {
        this.adminService.acceptReviewFishingTrip(this.clickedRowFishingTripReviews.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public declineReview(): void {
        this.adminService.declineReview(this.clickedRowReviews.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public declineFishingTripReview(): void {
        this.adminService.declineReviewFishingTrip(this.clickedRowFishingTripReviews.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }
}
