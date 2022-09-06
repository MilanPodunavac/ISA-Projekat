import { Component, OnInit } from '@angular/core';
import { FishingInstructorService } from '../service/fishing-instructor.service';
import { FishingTripService } from '../service/fishing-trip.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FishingInstructorGet } from '../model/fishing-instructor-get';
import { ClientService } from '../service/client.service';

@Component({
  selector: 'app-fishing-instructor-profile',
  templateUrl: './fishing-instructor-profile.component.html',
  styleUrls: ['./fishing-instructor-profile.component.scss']
})
export class FishingInstructorProfileComponent implements OnInit {
  fishingInstructor: FishingInstructorGet;
  role: string;
  canAddReview: boolean;
  canAddComplaint: boolean;

  constructor(private _route: ActivatedRoute, private fishingInstructorService: FishingInstructorService, private _clientService: ClientService, private fishingTripService: FishingTripService, private router: Router) {
    this.role = localStorage.getItem('role');
  }

  ngOnInit(): void {
    let id = Number(this._route.snapshot.paramMap.get('id'));
    this.fishingInstructorService.getFishingInstructor(id).subscribe(data => {
      this.fishingInstructor = data;
    });
    if(this.role ==='ROLE_CLIENT'){
      this._clientService.getLoggedInClient().subscribe({
        next: data => {
          if(this.fishingInstructor.fishingTrips.filter(e => e.fishingTripQuickReservations.filter(e => e.id === data.id).length > 0).length > 0){
            this.canAddReview = true
          }
          if(this.fishingInstructor.fishingTrips.filter(e => e.fishingTripQuickReservations.filter(e => e.id === data.id).length > 0).length > 0){
            this.canAddComplaint = true
          }
        }
      })
    }
  }

  doubleClickFunction(id: any){
    this.router.navigate(['/fishing-trip', id])
  }

  addReview(){
    this.router.navigate(['fishing-instructor/'+this.fishingInstructor.id+'/new-fishing-instructor-review'])
  }
  addComplaint(){
    this.router.navigate(['fishing-instructor/'+this.fishingInstructor.id+'/new-fishing-instructor-complaint'])
  }

}
