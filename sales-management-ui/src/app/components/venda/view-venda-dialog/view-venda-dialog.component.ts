import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-view-venda-dialog',
  templateUrl: './view-venda-dialog.component.html',
  styleUrls: ['./view-venda-dialog.component.css']
})
export class ViewVendaDialogComponent implements OnInit {
  detalheProdutosVendidos: any[] = [];
  dataVenda: any = '';
  colunas: string[] = ['nomeProduto', 'categoria', 'preco', 'quantidade', 'total'];

  constructor(
    public dialogRef: MatDialogRef<ViewVendaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }

  ngOnInit(): void {
    this.detalheProdutosVendidos = JSON.parse(this.data.detalheProdutosVendidos);
    this.dataVenda = this.data.dataVenda;
  }

  fecharDialog(): void {
    this.dialogRef.close();
  }
}
