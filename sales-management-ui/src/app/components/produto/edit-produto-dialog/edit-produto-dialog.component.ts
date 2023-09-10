import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoriaService } from 'src/app/services/categoria.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

@Component({
  selector: 'app-edit-produto-dialog',
  templateUrl: './edit-produto-dialog.component.html',
  styleUrls: ['./edit-produto-dialog.component.css']
})
export class EditProdutoDialogComponent implements OnInit {

  respostaMensagem: any;
  categorias: any = [];

  constructor(
    public dialogRef: MatDialogRef<EditProdutoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private categoriaService: CategoriaService,
    private snackbarService: SnackbarService
  ) { }

  ngOnInit(): void {
    this.getCategoria();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  getCategoria() {
    this.categoriaService.getAllCategoria().subscribe((resp: any) => {
      this.categorias = resp;
    }, (error) => {
      console.log("Erro ao buscar as categorias", error);

      if (error.error?.message) {
        this.respostaMensagem = error.error?.message;
      } else {
        this.respostaMensagem = "Ocorreu um erro inesperado ao tentar carregar lista de categoria!";
      }

      this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error);
    })
  }
}
