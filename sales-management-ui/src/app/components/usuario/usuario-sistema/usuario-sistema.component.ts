import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

@Component({
  selector: 'app-usuario-sistema',
  templateUrl: './usuario-sistema.component.html',
  styleUrls: ['./usuario-sistema.component.css']
})
export class UsuarioSistemaComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['nome', 'cpf', 'email', 'numeroContato', 'dataCriacao', 'isAtivo'];
  dataSource = new MatTableDataSource<any>();

  respostaMensagem: any;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  isLoading = false;

  constructor(
    private usuarioService: UsuarioService,
    private snackbarService: SnackbarService,
  ) { }

  ngOnInit() {
    this.carregarTabela();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  carregarTabela() {
    this.isLoading = true;
    this.usuarioService.findAllUsuario().subscribe((resp: any) => {
      this.isLoading = false;
      this.dataSource.data = resp;
    }, (error: any) => {
      this.isLoading = false;
      console.log(error);
      this.respostaMensagem = error.error?.message || ConstantesGeral.erroGenerico;
      this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  onAtivaDesativa(isAtivo: any, id: any) {
    this.isLoading = true;
    var dados = {
      isAtivo: isAtivo.toString(),
      id: id,
    };
    this.usuarioService.update(dados).subscribe(
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
}
