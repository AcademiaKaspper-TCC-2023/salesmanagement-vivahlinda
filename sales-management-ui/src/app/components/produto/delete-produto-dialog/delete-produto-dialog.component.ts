import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-produto-dialog',
  templateUrl: './delete-produto-dialog.component.html',
  styleUrls: ['./delete-produto-dialog.component.css']
})
export class DeleteProdutoDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DeleteProdutoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
