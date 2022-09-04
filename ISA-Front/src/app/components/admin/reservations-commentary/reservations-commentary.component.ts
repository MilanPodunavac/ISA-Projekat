import { Component, OnInit } from '@angular/core';
import { ActionGet } from 'src/app/model/action-get.model';
import { FishingActionGet } from 'src/app/model/fishing-action-get.model';
import { FishingReservationGet } from 'src/app/model/fishing-reservation-get.model';
import { ReservationGet } from 'src/app/model/reservation-get.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
    selector: 'app-reservations-commentary',
    templateUrl: './reservations-commentary.component.html',
    styleUrls: ['./reservations-commentary.component.scss']
})
export class ReservationsCommentaryComponent implements OnInit {
    displayedColumns: string[] = ['commentary'];
    dataSourceReservations: ReservationGet[];
    clickedRowReservations: ReservationGet;
    dataSourceActions: ActionGet[];
    clickedRowActions: ActionGet;
    dataSourceFishingReservations: FishingReservationGet[];
    clickedRowFishingReservations: FishingReservationGet;
    dataSourceFishingActions: FishingActionGet[];
    clickedRowFishingActions: FishingActionGet;

    constructor(private adminService: AdminService) { 
        this.adminService.getReservationsWithCommentariesForAdmin().subscribe(data => {
            this.dataSourceReservations = data;
        });

        this.clickedRowReservations = new ReservationGet();

        this.adminService.getQuickReservationsWithCommentariesForAdmin().subscribe(data => {
            this.dataSourceActions = data;
        });

        this.clickedRowActions = new ActionGet();

        this.adminService.getFishingReservationsWithCommentariesForAdmin().subscribe(data => {
            this.dataSourceFishingReservations = data;
        });

        this.clickedRowFishingReservations = new FishingReservationGet();

        this.adminService.getFishingQuickReservationsWithCommentariesForAdmin().subscribe(data => {
            this.dataSourceFishingActions = data;
        });

        this.clickedRowFishingActions = new FishingActionGet();
    }

    ngOnInit(): void {
    }

    public updateClickedRowReservations(row: ReservationGet): void {
        if (this.clickedRowReservations.id === row.id) {
            this.clickedRowReservations = new ReservationGet(); 
        } else {
            this.clickedRowReservations = row;
        }
    }

    public updateClickedRowActions(row: ActionGet): void {
        if (this.clickedRowActions.id === row.id) {
            this.clickedRowActions = new ActionGet(); 
        } else {
            this.clickedRowActions = row;
        }
    }

    public updateClickedRowFishingReservations(row: FishingReservationGet): void {
        if (this.clickedRowFishingReservations.id === row.id) {
            this.clickedRowFishingReservations = new FishingReservationGet(); 
        } else {
            this.clickedRowFishingReservations = row;
        }
    }

    public updateClickedRowFishingActions(row: FishingActionGet): void {
        if (this.clickedRowFishingActions.id === row.id) {
            this.clickedRowFishingActions = new FishingActionGet(); 
        } else {
            this.clickedRowFishingActions = row;
        }
    }

    public acceptCommentaryReservation(): void {
        this.adminService.reservationCommentaryAccept(this.clickedRowReservations.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public declineCommentaryReservation(): void {
        this.adminService.reservationCommentaryDecline(this.clickedRowReservations.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public acceptCommentaryAction(): void {
        this.adminService.quickReservationCommentaryAccept(this.clickedRowActions.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public declineCommentaryAction(): void {
        this.adminService.quickReservationCommentaryDecline(this.clickedRowActions.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public acceptCommentaryFishingReservation(): void {
        this.adminService.fishingReservationCommentaryAccept(this.clickedRowFishingReservations.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public declineCommentaryFishingReservation(): void {
        this.adminService.fishingReservationCommentaryDecline(this.clickedRowFishingReservations.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public acceptCommentaryFishingAction(): void {
        this.adminService.fishingQuickReservationCommentaryAccept(this.clickedRowFishingActions.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }

    public declineCommentaryFishingAction(): void {
        this.adminService.fishingQuickReservationCommentaryDecline(this.clickedRowFishingActions.id).subscribe(data => {
            window.location.reload();
            alert(data);
        });
    }
}
