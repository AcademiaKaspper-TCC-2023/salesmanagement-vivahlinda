import { DatePipe } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ListagemCategoriaComponent } from './components/categoria/listagem-categoria/listagem-categoria.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BarChartComponent } from './components/dashboard/shared/bar-chart/bar-chart.component';
import { LoginComponent } from './components/login/login.component';
import { NavComponent } from './components/nav/nav.component';
import { AlterarSenhaComponent } from './components/usuario/alterar-senha/alterar-senha.component';
import { EsqueciMinhaSenhaDialogComponent } from './esqueci-minha-senha-dialog/esqueci-minha-senha-dialog.component';
import { TokenInterceptorInterceptor } from './services/token-interceptor.interceptor';
import { EditCategoriaDialogComponent } from './components/categoria/edit-categoria-dialog/edit-categoria-dialog.component';
import { CreateCategoriaDialogComponent } from './components/categoria/create-categoria-dialog/create-categoria-dialog.component';
import { ListagemProdutoComponent } from './components/produto/listagem-produto/listagem-produto.component';
import { EditProdutoDialogComponent } from './components/produto/edit-produto-dialog/edit-produto-dialog.component';
import { DeleteProdutoDialogComponent } from './components/produto/delete-produto-dialog/delete-produto-dialog.component';
import { CreateProdutoDialogComponent } from './components/produto/create-produto-dialog/create-produto-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    DashboardComponent,
    BarChartComponent,
    AlterarSenhaComponent,
    LoginComponent,
    EsqueciMinhaSenhaDialogComponent,
    ListagemCategoriaComponent,
    EditCategoriaDialogComponent,
    CreateCategoriaDialogComponent,
    ListagemProdutoComponent,
    EditProdutoDialogComponent,
    DeleteProdutoDialogComponent,
    CreateProdutoDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    MatTableModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatSortModule
  ],
  providers: [
    HttpClientModule,
    DatePipe,

    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
