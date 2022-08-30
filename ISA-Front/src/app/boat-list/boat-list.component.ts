import { Component, OnInit } from '@angular/core';
import { BoatService } from 'src/app/service/boat.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-boat-list',
  templateUrl: './boat-list.component.html',
  styleUrls: ['./boat-list.component.scss']
})
export class BoatListComponent implements OnInit {

  boats: any[] = [];
  unfilteredBoats: any[] = [];
  SearchString: string = "";

  constructor(private _boatService: BoatService, private router: Router) {}


  ngOnInit(): void {
    this._boatService.getAllBoats().subscribe(
      {next: data => {
        this.boats = data;
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
    this._boatService.getAllBoats().subscribe(
      {next: data => {
        this.unfilteredBoats = data;
      }
    });
  }

  FilterFn(){
    var searchString = this.SearchString.trim().toLowerCase();
    
    if(searchString == ""){
      this.boats = this.unfilteredBoats;
      return;
    }

    this.boats = [];
    for(var i = 0; i < this.unfilteredBoats.length; i++){
        if(this.unfilteredBoats[i].name.toLowerCase().includes(searchString) ||
            (this.unfilteredBoats[i].streetName + " " + this.unfilteredBoats[i].streetNumber + " " + this.unfilteredBoats[i].city.name + " " + this.unfilteredBoats[i].city.country.name).toLowerCase().includes(searchString) ||
            this.unfilteredBoats[i].type.toLowerCase().includes(searchString) ||
            this.unfilteredBoats[i].additionalServices.toLowerCase().includes(searchString)){
          this.boats.push(this.unfilteredBoats[i]);
        }
    }
  }

  doubleClickFunction(id: any){
    this.router.navigate(['/boat', id])
  }
}
