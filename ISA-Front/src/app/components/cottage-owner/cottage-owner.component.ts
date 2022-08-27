import { Component, OnInit } from '@angular/core';
import { CottageOwnerService } from 'src/app/service/cottage-owner.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cottage-owner',
  templateUrl: './cottage-owner.component.html',
  styleUrls: ['./cottage-owner.component.scss']
})
export class CottageOwnerComponent implements OnInit {
  cottages: any[] = [];
  unFilteredCottages: any[] = [];
  SearchString: string = "";

  constructor(private _cottageOwnerService: CottageOwnerService, private router: Router) {}


  ngOnInit(): void {
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
    this.router.navigate(['/cottage-owner/cottage', id])
  }

}
