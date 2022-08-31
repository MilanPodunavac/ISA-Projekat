import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Profit } from 'src/app/model/profit';
import { CottageOwnerService } from 'src/app/service/cottage-owner.service';
import { CottageService } from 'src/app/service/cottage.service';
import { Chart, registerables } from 'node_modules/chart.js'

@Component({
  selector: 'app-profit-graph',
  templateUrl: './profit-graph.component.html',
  styleUrls: ['./profit-graph.component.scss']
})
export class ProfitGraphComponent implements OnInit {
  role: string;
  profit: Profit;
  chartArray: any[] = []
  chart: Chart

  constructor(private _cottageOwnerService: CottageOwnerService, private _route: ActivatedRoute, private cottageService: CottageService, private router: Router) { this.role = localStorage.getItem('role'); }

  ngOnInit(): void {
    Chart.register(...registerables)
    if(this.role === "ROLE_COTTAGE_OWNER"){
      this._cottageOwnerService.getProfit().subscribe({
        next: data => {
          this.profit = data
          var temp = new Date(this.profit.first.start)
          this.profit.first.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.second.start)
          this.profit.second.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.third.start)
          this.profit.third.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.fourth.start)
          this.profit.fourth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.fifth.start)
          this.profit.fifth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.sixth.start)
          this.profit.sixth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.seventh.start)
          this.profit.seventh.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.eighth.start)
          this.profit.eighth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.ninth.start)
          this.profit.ninth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.tenth.start)
          this.profit.tenth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.eleventh.start)
          this.profit.eleventh.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          var temp = new Date(this.profit.twelfth.start)
          this.profit.twelfth.start = temp.getFullYear() + "-" + (temp.getMonth() + 1)

          /*this.profit.first.income = 50
          this.profit.second.income = 20;
          this.profit.third.income = 100;
          this.profit.fourth.income = 74
          this.profit.fifth.income = 100
          this.profit.sixth.income = 120
          this.profit.seventh.income = 110
          this.profit.eighth.income = 152
          this.profit.ninth.income = 108
          this.profit.tenth.income = 400
          this.profit.eleventh.income = 108
          this.profit.twelfth.income = 300*/

          this.chartArray.push(this.profit.first)
          this.chartArray.push(this.profit.second)
          this.chartArray.push(this.profit.third)
          this.chartArray.push(this.profit.fourth)
          this.chartArray.push(this.profit.fifth)
          this.chartArray.push(this.profit.sixth)
          this.chartArray.push(this.profit.seventh)
          this.chartArray.push(this.profit.eighth)
          this.chartArray.push(this.profit.ninth)
          this.chartArray.push(this.profit.tenth)
          this.chartArray.push(this.profit.eleventh)
          this.chartArray.push(this.profit.twelfth)
          console.log(this.chartArray)

          this.chart = new Chart("chartCanv", {
            type: 'bar',
            data: {
                labels: [this.chartArray[0].start, this.chartArray[1].start, this.chartArray[2].start, this.chartArray[3].start,
                 this.chartArray[4].start, this.chartArray[5].start, this.chartArray[6].start, this.chartArray[7].start, this.chartArray[8].start,
                  this.chartArray[9].start, this.chartArray[10].start, this.chartArray[11].start],
                datasets: [{
                    label: 'Income in the previous 12 months',
                    data: [this.chartArray[0].income, this.chartArray[1].income, this.chartArray[2].income, this.chartArray[3].income,
                    this.chartArray[4].income, this.chartArray[5].income, this.chartArray[6].income, this.chartArray[7].income, this.chartArray[8].income,
                     this.chartArray[9].income, this.chartArray[10].income, this.chartArray[11].income],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.8)',
                        'rgba(54, 162, 235, 0.8)',
                        'rgba(255, 206, 86, 0.8)',
                        'rgba(75, 192, 192, 0.8)',
                        'rgba(153, 102, 255, 0.8)',
                        'rgba(255, 159, 64, 0.8)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
          });
        },
        error: data => {
          console.log(data)
          alert(data.error)
        }
      })
    }
  }

}
