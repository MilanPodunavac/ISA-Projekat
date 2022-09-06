import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-public-info',
  templateUrl: './user-public-info.component.html',
  styleUrls: ['./user-public-info.component.scss']
})
export class UserPublicInfoComponent implements OnInit {
  user: any

  constructor(private _userService: UserService,  private _route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this._userService.getPublicInfo(Number(this._route.snapshot.paramMap.get('id'))).subscribe({
      next: data =>{
        this.user = data
      },
      error: data =>{
        alert(data.error.message)
      }
    })
  }

}
