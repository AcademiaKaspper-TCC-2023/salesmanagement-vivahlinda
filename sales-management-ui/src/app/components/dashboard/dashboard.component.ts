import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  salesData!: any[];

  ngOnInit(): void {
    this.salesData = [
      { mes: 'JANUARY', totalVendas: 30000000.57 },
      { mes: 'FEBRUARY', totalVendas: 10000000.54 },
      { mes: 'MARCH', totalVendas: 30000000.57 },
      { mes: 'APRIL', totalVendas: 120000.07 },
      { mes: 'MAY', totalVendas: 30000000.57 },
      { mes: 'JUNE', totalVendas: 10000000.54 },
      { mes: 'JULY', totalVendas: 30000000.57 },
      { mes: 'AUGUST', totalVendas: 42000.07 },
      { mes: 'SEPTEMBER', totalVendas: 30000000.57 },
      { mes: 'OCTOBER', totalVendas: 0 },
      { mes: 'NOVEMBER', totalVendas: 0 },
      { mes: 'DECEMBER', totalVendas: 0 }
    ];
  }
}
