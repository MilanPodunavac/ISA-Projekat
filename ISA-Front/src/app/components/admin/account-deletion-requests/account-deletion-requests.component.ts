import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountDeletionRequestGet } from 'src/app/model/account-deletion-request-get.model';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-account-deletion-requests',
    templateUrl: './account-deletion-requests.component.html',
    styleUrls: ['./account-deletion-requests.component.scss']
})
export class AccountDeletionRequestsComponent implements OnInit {
    displayedColumnsAccountDeletionRequests: string[] = ['text', 'name', 'email'];
    dataSourceAccountDeletionRequests: AccountDeletionRequestGet[];
    clickedRowAccountDeletionRequests: AccountDeletionRequestGet;

    constructor(private router: Router, private userService: UserService) { 
        this.userService.getAllAccountDeletionRequests().subscribe(data => {
            this.dataSourceAccountDeletionRequests = data;
        });

        this.clickedRowAccountDeletionRequests = new AccountDeletionRequestGet();
    }

    ngOnInit(): void {
    }

    public updateClickedRowAccountDeletionRequests(row: AccountDeletionRequestGet): void {
        if (this.clickedRowAccountDeletionRequests.id === row.id) {
            this.clickedRowAccountDeletionRequests = new AccountDeletionRequestGet(); 
        } else {
            this.clickedRowAccountDeletionRequests = row;
        }
    }

    public acceptAccountDeletionRequest(): void {
        this.router.navigate(['account-deletion-request/' + this.clickedRowAccountDeletionRequests.id + '/accept']).then(() => {
            window.location.reload();
        });
    }

    public declineAccountDeletionRequest(): void {
        this.router.navigate(['account-deletion-request/' + this.clickedRowAccountDeletionRequests.id + '/decline']).then(() => {
            window.location.reload();
        });
    }
}
