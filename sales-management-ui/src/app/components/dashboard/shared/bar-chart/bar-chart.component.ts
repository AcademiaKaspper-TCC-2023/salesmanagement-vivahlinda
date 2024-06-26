import { Component, Input, OnInit } from '@angular/core';
import { Chart } from 'chart.js/auto';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.css']
})
export class BarChartComponent implements OnInit {
  @Input() salesData!: any[];

  chart: any;

  constructor() { }

  ngOnInit(): void {
    if (this.salesData && this.salesData.length > 0) {
      this.createBarChart();
    }
  }


  createBarChart() {
    this.chart = new Chart('barChart', {
      type: 'bar',
      data: {
        labels: this.salesData.map(item => item.mes),
        datasets: [{
          label: 'Total de Vendas',
          data: this.salesData.map(item => item.totalVendas),
          backgroundColor: 'rgba(75, 192, 192, 0.2)', // Cor das barras
          borderColor: 'rgba(75, 192, 192, 1)', // Cor da borda das barras
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Total de Vendas'
            }
          },
          x: {
            title: {
              display: true,
              text: 'Mês'
            }
          }
        }
      }
    });
  }
}
