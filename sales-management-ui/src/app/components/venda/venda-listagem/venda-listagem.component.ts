import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { VendaService } from 'src/app/services/venda.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

import { DeleteVendaDialogComponent } from '../delete-venda-dialog/delete-venda-dialog.component';

@Component({
  selector: 'app-venda-listagem',
  templateUrl: './venda-listagem.component.html',
  styleUrls: ['./venda-listagem.component.css']
})
export class VendaListagemComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['nomeCliente', 'noContatoCliente', 'formaPagamento', 'loginVendedor', 'dataVenda', 'totalCompra', 'acoes' ];
  dataSource = new MatTableDataSource<any>();

  respostaMensagem: any;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  isLoading = false;

  constructor(private vendaService: VendaService, public dialog: MatDialog, private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.carregarTabela();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  carregarTabela() {
    this.isLoading = true;
    this.vendaService.getVenda().subscribe(data => {
      this.dataSource.data = data;
      this.isLoading = false;
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openViewDialog(row: any){

  }

  openDeleteDialog(row: any): void {
    const dialogRef = this.dialog.open(DeleteVendaDialogComponent, {
      width: '350px',
      height: '300PX',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.isLoading = true;
        this.vendaService.deleteById(result).subscribe((response: any) => {
          this.isLoading = false;
          this.carregarTabela();
          this.respostaMensagem = response?.mensagem;
          dialogRef.close();
          this.snackbarService.openSnackBar(this.respostaMensagem, "");
        }, (error) => {
          if (error.error?.mensagem) {
            this.respostaMensagem = error.error?.mensagem;
          } else {
            this.respostaMensagem = ConstantesGeral.erroGenerico;
          }
          this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error);
          dialogRef.close();
        });
      }
    });
  }

}
