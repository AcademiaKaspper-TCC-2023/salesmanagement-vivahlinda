import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-produto-dialog',
  templateUrl: './edit-produto-dialog.component.html',
  styleUrls: ['./edit-produto-dialog.component.css']
})
export class EditProdutoDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<EditProdutoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
