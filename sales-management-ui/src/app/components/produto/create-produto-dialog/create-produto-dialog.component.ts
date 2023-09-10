import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-create-produto-dialog',
  templateUrl: './create-produto-dialog.component.html',
  styleUrls: ['./create-produto-dialog.component.css']
})
export class CreateProdutoDialogComponent {

  data = {
    categoriaId: '',
    nome: '',
    descricao: '',
    preco: ''
  };

  constructor(public dialogRef: MatDialogRef<CreateProdutoDialogComponent>) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
