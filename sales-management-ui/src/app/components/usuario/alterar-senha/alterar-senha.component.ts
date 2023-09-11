import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AlterarSenha } from 'src/app/models/alterarSenha';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

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

  constructor(private usuarioService: UsuarioService, private snackbarService: SnackbarService, private router: Router) { }

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
          if (resposta) {
            this.snackbarService.openSnackBar('Senha alterada com sucesso', '');
            console.log('Resposta da API:', resposta);
            this.router.navigate(['/dashboard']);
          } else {
            this.snackbarService.openSnackBar('Resposta vazia da API', '');
          }
        },
        (erro) => {
          console.error('Erro na API:', erro);
          if (erro.error?.mensagem) {
            this.snackbarService.openSnackBar(erro.error.mensagem, ConstantesGeral.error);
          } else {
            this.snackbarService.openSnackBar(ConstantesGeral.erroGenerico, ConstantesGeral.error);
          }
        }
      );
    }
  }

}
