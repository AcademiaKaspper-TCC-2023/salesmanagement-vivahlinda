import { Component, OnInit } from '@angular/core';
import { VendaService } from 'src/app/services/venda.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';
import { delay } from 'rxjs/operators';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  salesData!: any[];
  isLoading: boolean = true;

  constructor(private vendaService: VendaService) {}

  ngOnInit(): void {
    this.vendaService.getVendasMensais().pipe(
      delay(2500)
    ).subscribe({
      next: data => {
        this.salesData = data;
        this.isLoading = false;
      },
      error: error => {
        console.error(ConstantesGeral.erroGenerico, error);
        this.isLoading = false;
      }
    });

  }
}
