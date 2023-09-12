import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit {
  usuarioLogado: any = {};

  constructor(private router: Router, private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.usuarioService.perfil().subscribe(
      (data) => {
        this.usuarioLogado = data;
      },
      (error) => {
        console.log(error);

      }
    );

    this.router.navigate(['dashboard']);
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['/entrar']);
  }
}
