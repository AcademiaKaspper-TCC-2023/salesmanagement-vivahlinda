import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit {
  usuarioLogado: any = {
    id: null,
    nome: 'vivah linda adm',
    numeroContato: null,
    email: 'vivahlinda@mailinator.com',
    isAtivo: null,
    role: 'admin',
    endereco: null,
    cpf: null,
    dataNascimento: null,
    dataCriacao: null,
  };

  constructor(private router: Router){

  }

  ngOnInit(): void {
    this.router.navigate(['dashboard']);
  }
}
