import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-categoria-dialog',
  templateUrl: './edit-categoria-dialog.component.html',
  styleUrls: ['./edit-categoria-dialog.component.css']
})
export class EditCategoriaDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<EditCategoriaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    this.dialogRef.close(this.data);
  }
}
