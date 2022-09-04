import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminGet } from 'src/app/model/admin-get.model';
import { AdminService } from 'src/app/service/admin.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    role: string;
    loggedInAdmin: AdminGet;

    constructor(private router: Router, private adminService: AdminService) {
        this.role = localStorage.getItem('role');

        if (this.role === 'ROLE_ADMIN') {
            adminService.getLoggedInAdmin().subscribe(data => {
                this.loggedInAdmin = data;
            });
        }
    }

    ngOnInit(): void {
    }

    public logout(): void {
        localStorage.clear();
        this.router.navigate(['login']).then(() => {
            window.location.reload();
        });
    }
}
