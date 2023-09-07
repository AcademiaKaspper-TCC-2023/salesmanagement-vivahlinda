import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AlterarSenhaComponent } from './components/usuario/alterar-senha/alterar-senha.component';

const routes: Routes = [
  {
    path:'', component : NavComponent, children: [
      {path: 'dashboard', component: DashboardComponent},
      {path: 'alterarSenha', component: AlterarSenhaComponent},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
