import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
    role: string;

    constructor(private router: Router) {
        this.role = localStorage.getItem('role');
    }

    ngOnInit(): void {
    }

    public logout(): void {
        localStorage.clear();
        this.router.navigate(['login']).then(() => {
            window.location.reload();
        });;
    }
}
