import { Component } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {

  usuarioLogado: any = {
    "id": null,
    "nome": "vivah linda adm",
    "numeroContato": null,
    "email": "vivahlinda@mailinator.com",
    "isAtivo": null,
    "role": "admin",
    "endereco": null,
    "cpf": null,
    "dataNascimento": null,
    "dataCriacao": null
}
}
