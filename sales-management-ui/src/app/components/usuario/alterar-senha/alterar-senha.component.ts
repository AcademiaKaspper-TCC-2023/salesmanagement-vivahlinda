import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { AlterarSenha } from 'src/app/models/alterarSenha';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-alterar-senha',
  templateUrl: './alterar-senha.component.html',
  styleUrls: ['./alterar-senha.component.css']
})
export class AlterarSenhaComponent implements OnInit {

  hide = true;

  alterarSenha: AlterarSenha = {
    senhaAntiga: "",
    senhaNova: "",
  };

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit(): void {
  }

  senhaAntiga: FormControl = new FormControl(null, [
    Validators.required,
    Validators.minLength(4),
  ]);

  senhaNova: FormControl = new FormControl(null, [
    Validators.required,
    Validators.minLength(4),
  ]);

  validaCampos(): boolean {
    return this.senhaAntiga.valid && this.senhaNova.valid;
  }

  onSubmit() {
    if (this.validaCampos()) {
      const dadosParaAPI = {
        senhaAntiga: this.senhaAntiga.value,
        senhaNova: this.senhaNova.value,
      };

      this.usuarioService.alterarSenha(dadosParaAPI).subscribe(
        (resposta) => {
          console.log('Resposta da API:', resposta);
        },
        (erro) => {
          console.error('Erro na API:', erro);
        }
      );
    }
  }
}
