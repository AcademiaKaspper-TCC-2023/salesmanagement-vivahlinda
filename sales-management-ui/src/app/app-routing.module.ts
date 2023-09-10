import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AlterarSenhaComponent } from './components/usuario/alterar-senha/alterar-senha.component';
import { LoginComponent } from './components/login/login.component';
import { RouteGuardService } from './services/route-guard.service';

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
      { path: 'alterarSenha', component: AlterarSenhaComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
