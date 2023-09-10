import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-create-categoria-dialog',
  templateUrl: './create-categoria-dialog.component.html',
  styleUrls: ['./create-categoria-dialog.component.css']
})
export class CreateCategoriaDialogComponent {
  nome: string = '';

  constructor(public dialogRef: MatDialogRef<CreateCategoriaDialogComponent>) { }

  onCancel(): void {
    this.dialogRef.close();
  }

  onCreate(): void {
    this.dialogRef.close({ nome: this.nome });
  }
}
