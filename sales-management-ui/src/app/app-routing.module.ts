import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AlterarSenhaComponent } from './components/usuario/alterar-senha/alterar-senha.component';
import { LoginComponent } from './components/login/login.component';
import { RouteGuardService } from './services/route-guard.service';
import { ListagemCategoriaComponent } from './components/categoria/listagem-categoria/listagem-categoria.component';
import { ListagemProdutoComponent } from './components/produto/listagem-produto/listagem-produto.component';

const routes: Routes = [
  { path: 'entrar', component: LoginComponent },

  {
    path: '', component: NavComponent, children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        data: { expectedRole: ['usuario', 'admin'] },
        canActivate: [RouteGuardService]
      },
      {
        path: 'categoria/listagem',
        component: ListagemCategoriaComponent,
        data: { expectedRole: ['usuario', 'admin'] },
        canActivate: [RouteGuardService]
      },
      {
        path: 'produto/listagem',
        component: ListagemProdutoComponent,
        data: { expectedRole: ['usuario', 'admin'] },
        canActivate: [RouteGuardService]
      },
      { path: 'alterarSenha', component: AlterarSenhaComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
