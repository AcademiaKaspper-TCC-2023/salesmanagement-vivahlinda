import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { saveAs } from 'file-saver';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { VendaService } from 'src/app/services/venda.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

import { DeleteVendaDialogComponent } from '../delete-venda-dialog/delete-venda-dialog.component';
import { ViewVendaDialogComponent } from '../view-venda-dialog/view-venda-dialog.component';

@Component({
  selector: 'app-venda-listagem',
  templateUrl: './venda-listagem.component.html',
  styleUrls: ['./venda-listagem.component.css']
})
export class VendaListagemComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['nomeCliente', 'noContatoCliente', 'formaPagamento', 'loginVendedor', 'dataVenda', 'totalCompra', 'acoes'];
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

  openViewDialog(row: any): void {
    const dialogRef = this.dialog.open(ViewVendaDialogComponent, {
      width: '950px',
      maxHeight: '600px',
      minHeight: '300px',
      data: row
    });
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

  downloadRelatorio(valores: any) {
    this.isLoading
    var dados = {
      nome: valores.nome,
      email: valores.email,
      uuid: valores.uuid,
      numeroContato: valores.numeroContato,
      pagamento: valores.pagamento,
      totalCompra: valores.totalCompra.toString,
      detalheProduto: valores.detalheProduto,
    }

    this.downloadFile(valores.uuid, dados);
  }

  downloadFile(nomeArquivo: any, dados: any) {
    this.isLoading
    this.vendaService.getPdf(dados).subscribe((response) => {
      saveAs(response, nomeArquivo + '.pdf')
    })
  }
}
