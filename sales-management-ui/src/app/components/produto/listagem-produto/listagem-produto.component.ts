import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CategoriaService } from 'src/app/services/categoria.service';
import { MatDialog } from '@angular/material/dialog';
import { EditProdutoDialogComponent } from '../edit-produto-dialog/edit-produto-dialog.component';
import { CreateProdutoDialogComponent } from '../create-produto-dialog/create-produto-dialog.component';
import { ProdutoService } from 'src/app/services/produto.service';
import { DeleteProdutoDialogComponent } from '../delete-produto-dialog/delete-produto-dialog.component';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

@Component({
  selector: 'app-listagem-produto',
  templateUrl: './listagem-produto.component.html',
  styleUrls: ['./listagem-produto.component.css']
})
export class ListagemProdutoComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = ['nome', 'descricao', 'preco', 'status', 'nomeCategoria', 'acoes' ];
  dataSource = new MatTableDataSource<any>();

  respostaMensagem: any;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  isLoading = false;

  constructor(private produtoService: ProdutoService, public dialog: MatDialog, private snackbarService: SnackbarService) { }

  ngOnInit() {
    this.carregarTabela();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  carregarTabela() {
    this.isLoading = true;
    this.produtoService.getAllProduto().subscribe(data => {
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

  openEditDialog(row: any): void {
    const dialogRef = this.dialog.open(EditProdutoDialogComponent, {
      width: '700px',
      height: '400px',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.isLoading = true;
        this.produtoService.updateProduto(result).subscribe(response => {
          this.isLoading = false;
          this.carregarTabela();
          this.respostaMensagem = response?.mensagem || "Produto atualizado com sucesso!";
          this.snackbarService.openSnackBar(this.respostaMensagem, "");
        }, (error) => {
          this.respostaMensagem = error.error?.mensagem || ConstantesGeral.erroGenerico;
          this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error);
        });
      }
    });
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(CreateProdutoDialogComponent, {
      width: '800px',
      height: '500px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.isLoading = true;
        this.produtoService.addNewProduto(result).subscribe(response => {
          this.isLoading = false;
          this.carregarTabela();
          this.respostaMensagem = response?.mensagem || "Produto criado com sucesso!";
          this.snackbarService.openSnackBar(this.respostaMensagem, "");
        }, (error) => {
          this.respostaMensagem = error.error?.mensagem || ConstantesGeral.erroGenerico;
          this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error);
        });
      }
    });
  }

  openDeleteDialog(row: any): void {
    const dialogRef = this.dialog.open(DeleteProdutoDialogComponent, {
      width: '450px',
      height: '400PX',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.isLoading = true;
        this.produtoService.deleteProduto(result).subscribe(response => {
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
