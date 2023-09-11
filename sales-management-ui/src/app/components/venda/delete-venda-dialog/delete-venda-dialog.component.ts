import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-venda-dialog',
  templateUrl: './delete-venda-dialog.component.html',
  styleUrls: ['./delete-venda-dialog.component.css']
})
export class DeleteVendaDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<DeleteVendaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
