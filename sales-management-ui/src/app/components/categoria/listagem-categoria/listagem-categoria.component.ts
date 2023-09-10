import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CategoriaService } from 'src/app/services/categoria.service';
import { CreateCategoriaDialogComponent } from '../create-categoria-dialog/create-categoria-dialog.component';
import { EditCategoriaDialogComponent } from '../edit-categoria-dialog/edit-categoria-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-listagem-categoria',
  templateUrl: './listagem-categoria.component.html',
  styleUrls: ['./listagem-categoria.component.css']
})
export class ListagemCategoriaComponent implements AfterViewInit {
  displayedColumns: string[] = ['id', 'nome', 'editar'];
  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private categoriaService: CategoriaService, public dialog: MatDialog) { }

  isLoading = false;

  ngAfterViewInit() {
    this.categoriaService.getAllCategoria().subscribe(data => {
      this.dataSource.data = data;
    });

    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openEditDialog(row: any): void {
    const dialogRef = this.dialog.open(EditCategoriaDialogComponent, {
      width: '700px',
      height: '400px',
      data: row
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.isLoading = true;
        this.categoriaService.updateCategoria(result).subscribe(() => {
          this.isLoading = false;

          this.categoriaService.getAllCategoria().subscribe(data => {
            this.dataSource.data = data;
          });
        });
      }
    });
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(CreateCategoriaDialogComponent, {
      width: '700px',
      height: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.isLoading = true;
        this.categoriaService.addCategoria(result).subscribe(() => {
          this.isLoading = false;
          this.categoriaService.getAllCategoria().subscribe(data => {
            this.dataSource.data = data;
          });
        });
      }
    });
  }
}

