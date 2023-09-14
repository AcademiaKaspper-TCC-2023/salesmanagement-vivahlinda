import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ProdutoService } from 'src/app/services/produto.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

import { CreateProdutoDialogComponent } from '../create-produto-dialog/create-produto-dialog.component';
import { DeleteProdutoDialogComponent } from '../delete-produto-dialog/delete-produto-dialog.component';
import { EditProdutoDialogComponent } from '../edit-produto-dialog/edit-produto-dialog.component';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-listagem-produto',
  templateUrl: './listagem-produto.component.html',
  styleUrls: ['./listagem-produto.component.css'],
})
export class ListagemProdutoComponent implements AfterViewInit, OnInit {
  displayedColumns: string[] = [
    'nome',
    'descricao',
    'preco',
    'nomeCategoria',
    'acoes',
  ];
  dataSource = new MatTableDataSource<any>();

  respostaMensagem: any;

  usuarioLogado: any;

  data: any = {};

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  isLoading = false;

  constructor(
    private produtoService: ProdutoService,
    public dialog: MatDialog,
    private snackbarService: SnackbarService,
    private usuarioService: UsuarioService
  ) { }

  ngOnInit() {
    this.carregarTabela();
    this.getPerfil();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  carregarTabela() {
    this.isLoading = true;
    this.produtoService.getAllProduto().subscribe((data) => {
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
      width: '850px',
      height: '600px',
      data: row,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.isLoading = true;
        this.produtoService.updateProduto(result).subscribe(
          (response) => {
            this.isLoading = false;
            this.carregarTabela();
            this.respostaMensagem =
              response?.mensagem || 'Produto atualizado com sucesso!';
            this.snackbarService.openSnackBar(this.respostaMensagem, '');
          },
          (error) => {
            this.respostaMensagem =
              error.error?.mensagem || ConstantesGeral.erroGenerico;
            this.snackbarService.openSnackBar(
              this.respostaMensagem,
              ConstantesGeral.error
            );
          }
        );
      }
    });
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(CreateProdutoDialogComponent, {
      width: '800px',
      height: '500px',
      data: { ...this.data },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.isLoading = true;
        this.produtoService.addNewProduto(result).subscribe(
          (response) => {
            this.isLoading = false;
            this.carregarTabela();
            this.respostaMensagem =
              response?.mensagem || 'Produto criado com sucesso!';
            this.snackbarService.openSnackBar(this.respostaMensagem, '');
          },
          (error) => {
            this.respostaMensagem =
              error.error?.mensagem || ConstantesGeral.erroGenerico;
            this.snackbarService.openSnackBar(
              this.respostaMensagem,
              ConstantesGeral.error
            );
          }
        );
      }
    });
  }

  openDeleteDialog(row: any): void {
    const dialogRef = this.dialog.open(DeleteProdutoDialogComponent, {
      width: '350px',
      height: '300PX',
      data: row,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.isLoading = true;
        this.produtoService.deleteProduto(result).subscribe(
          (response) => {
            this.isLoading = false;
            this.carregarTabela();
            this.respostaMensagem = response?.mensagem;
            dialogRef.close();
            this.snackbarService.openSnackBar(this.respostaMensagem, '');
          },
          (error) => {
            if (error.error?.mensagem) {
              this.respostaMensagem = error.error?.mensagem;
            } else {
              this.respostaMensagem = ConstantesGeral.erroGenerico;
            }
            this.snackbarService.openSnackBar(
              this.respostaMensagem,
              ConstantesGeral.error
            );
            dialogRef.close();
          }
        );
      }
    });
  }

  onAtivaDesativa(status: any, id: any) {
    this.isLoading = true;
    var dados = {
      status: status.toString(),
      id: id,
    };
    this.produtoService.updateStatus(dados).subscribe(
      (resp: any) => {
        this.isLoading = false;
        this.carregarTabela();
        this.respostaMensagem = resp?.mensagem;
        this.snackbarService.openSnackBar(
          this.respostaMensagem,
          ConstantesGeral.success
        );
      },
      (error: any) => {
        this.isLoading = false;

        if (error.error?.message) {
          this.respostaMensagem = error.error?.message;
        } else {
          this.respostaMensagem = ConstantesGeral.erroGenerico;
        }

        this.snackbarService.openSnackBar(
          this.respostaMensagem,
          ConstantesGeral.error
        );
      }
    );
  }

  getPerfil() {
    this.usuarioService.perfil().subscribe({
      next: (resp: any) => {
        this.usuarioLogado = resp;
      },
      error: (error) => {
        console.log('Erro ao buscar perfil do usuario', error);
      }
    });
  }
}
