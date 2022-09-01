import { Component, OnInit } from '@angular/core';
import { CottageService } from 'src/app/service/cottage.service';
import { Router } from '@angular/router';
import { CottageOwnerService } from '../service/cottage-owner.service';

@Component({
  selector: 'app-cottage-list',
  templateUrl: './cottage-list.component.html',
  styleUrls: ['./cottage-list.component.scss']
})
export class CottageListComponent implements OnInit {
  cottages: any[] = [];
  unFilteredCottages: any[] = [];
  SearchString: string = "";
  role: string;

  constructor(private _cottageService: CottageService, private router: Router, private _cottageOwnerService: CottageOwnerService) {this.role = localStorage.getItem('role');}


  ngOnInit(): void {
    if(this.role === "ROLE_COTTAGE_OWNER"){
        this._cottageOwnerService.getOwnersCottages().subscribe(
        {next: data => {
          this.cottages = data;
        },
        error: data => {
          console.log(data)
          alert(data.error)
        }
      });
      this._cottageOwnerService.getOwnersCottages().subscribe(
        {next: data => {
          this.unFilteredCottages = data;
        }
      });
      return;
    }
    this._cottageService.getAllCottages().subscribe(
      {next: data => {
        this.cottages = data;
      },
      error: data => {
        console.log(data)
        alert(data.error)
      }
    });
    this._cottageService.getAllCottages().subscribe(
      {next: data => {
        this.unFilteredCottages = data;
      }
    });
  }

  FilterFn(){
    var searchString = this.SearchString.trim().toLowerCase();
    
    if(searchString == ""){
      this.cottages = this.unFilteredCottages;
      return;
    }

    this.cottages = [];
    for(var i = 0; i < this.unFilteredCottages.length; i++){
        if(this.unFilteredCottages[i].name.toLowerCase().includes(searchString) ||
        (this.unFilteredCottages[i].location.streetName + 
        this.unFilteredCottages[i].location.cityName + " " + this.unFilteredCottages[i].location.countryName).toLowerCase().includes(searchString)){
          this.cottages.push(this.unFilteredCottages[i]);
        }
    }
  }

  doubleClickFunction(id: any){
    this.router.navigate(['/cottage', id])
  }

}
