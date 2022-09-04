import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FishingInstructorService } from '../service/fishing-instructor.service';

@Component({
  selector: 'app-fishing-instructor-list',
  templateUrl: './fishing-instructor-list.component.html',
  styleUrls: ['./fishing-instructor-list.component.scss']
})
export class FishingInstructorListComponent implements OnInit {

  fishingInstructors: any[] = [];
  unfilteredFishingInstructors: any[] = [];
  SearchString: string = "";
  role: string;

  constructor(private _fishingInstructorService: FishingInstructorService, private router: Router) {
    this.role = localStorage.getItem('role');
  }


  ngOnInit(): void {
    this._fishingInstructorService.getAllFishingInstructors().subscribe(
      {next: data => {
        this.fishingInstructors = data;
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
    this._fishingInstructorService.getAllFishingInstructors().subscribe(
      {next: data => {
        this.unfilteredFishingInstructors = data;
      }
    });
  }

  FilterFn(){
    var searchString = this.SearchString.trim().toLowerCase();
    
    if(searchString == ""){
      this.fishingInstructors = this.unfilteredFishingInstructors;
      return;
    }

    this.fishingInstructors = [];
    for(var i = 0; i < this.unfilteredFishingInstructors.length; i++){
        if((this.unfilteredFishingInstructors[i].firstName + " " + this.unfilteredFishingInstructors[i].lastName).toLowerCase().includes(searchString) ||
            this.unfilteredFishingInstructors[i].email.toLowerCase().includes(searchString)){
          this.fishingInstructors.push(this.unfilteredFishingInstructors[i]);
        }
    }
  }

  doubleClickFunction(id: any){
    this.router.navigate(['/fishing-instructor', id])
  }

  makeReservation() {
    this.router.navigate(['/fishing-trip/reservation'])
  }

}
