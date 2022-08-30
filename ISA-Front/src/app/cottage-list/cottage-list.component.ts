import { Component, OnInit } from '@angular/core';
import { CottageService } from 'src/app/service/cottage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cottage-list',
  templateUrl: './cottage-list.component.html',
  styleUrls: ['./cottage-list.component.scss']
})
export class CottageListComponent implements OnInit {
  cottages: any[] = [];
  unFilteredCottages: any[] = [];
  SearchString: string = "";

  constructor(private _cottageService: CottageService, private router: Router) {}


  ngOnInit(): void {
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
        (this.unFilteredCottages[i].streetName + " " + this.unFilteredCottages[i].streetNumber + " " + 
        this.unFilteredCottages[i].city.name + " " + this.unFilteredCottages[i].city.country.name).toLowerCase().includes(searchString)){
          this.cottages.push(this.unFilteredCottages[i]);
        }
    }
  }

  doubleClickFunction(id: any){
    this.router.navigate(['/cottage', id])
  }

}
