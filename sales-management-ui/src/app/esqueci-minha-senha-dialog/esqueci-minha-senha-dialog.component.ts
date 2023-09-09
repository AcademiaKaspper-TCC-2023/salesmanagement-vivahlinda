import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UsuarioService } from '../services/usuario.service';
import { SnackbarService } from '../services/snackbar.service';
import { ConstantesGeral } from '../utils/constantes-geral';
import { fieldLabels, exibirErro, getErrorMessage } from '../utils/form-utils';

@Component({
  selector: 'app-esqueci-minha-senha-dialog',
  templateUrl: './esqueci-minha-senha-dialog.component.html',
  styleUrls: ['./esqueci-minha-senha-dialog.component.css'],
  host: {
    'class': 'custom-dialog'
  }
})
export class EsqueciMinhaSenhaDialogComponent implements OnInit {
  email: string = '';
  esqueciMinhaSenhaForm: any;
  respostaMensagem: any;

  exibirErro = exibirErro;
  getErrorMessage = getErrorMessage;
  fieldLabels = fieldLabels;

  constructor(
    public dialogRef: MatDialogRef<EsqueciMinhaSenhaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,
    private usuarioService: UsuarioService,
    private snackbarService: SnackbarService,
  ) {
    this.email = data.email;
  }

  ngOnInit(): void {
    this.esqueciMinhaSenhaForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.pattern(ConstantesGeral.emailRegex)]],
    });
  }

  fecharDialog() {
    this.dialogRef.close();
  }

  enviarEmail() {
    // this.ngxUiLoaderService.start();
    var formData = this.esqueciMinhaSenhaForm.value;
    var dados = {
      email: formData.email
    }
    this.usuarioService.esqueciMinhaSenha(dados).subscribe((resp: any) => {
      // this.ngxUiLoaderService.stop();
      this.respostaMensagem = resp?.mensagem;
      this.dialogRef.close();
      this.snackbarService.openSnackBar(this.respostaMensagem, "");
      console.log("Dados enviados para recuperação de senha", dados);
      this.fecharDialog();

    }, (error) => {
      // this.ngxUiLoaderService.stop();
      if (error.error?.mensagem) {
        this.respostaMensagem = error.error?.mensagem;
      } else {
        this.respostaMensagem = ConstantesGeral.erroGenerico;
      }
      this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error)
      console.log("Ocorreu  um erro!", dados);
      this.fecharDialog();

    })
  }

}
