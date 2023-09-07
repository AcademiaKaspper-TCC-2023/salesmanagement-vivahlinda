import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  salesData = [
    { mes: 'JANUARY', totalVendas: 3000.57 },
    { mes: 'FEBRUARY', totalVendas: 1000.54 },
    { mes: 'MARCH', totalVendas: 420.57 }
  ];
}
