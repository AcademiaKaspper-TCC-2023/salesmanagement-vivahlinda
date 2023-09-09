import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { EsqueciMinhaSenhaDialogComponent } from 'src/app/esqueci-minha-senha-dialog/esqueci-minha-senha-dialog.component';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';
import { DatePipe } from '@angular/common';
import { fieldLabels, exibirErro, getErrorMessage } from '../../utils/form-utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string = '';
  senha: string = '';
  cadastroMode: boolean = false;

  hide = true;

  exibirErro = exibirErro;
  getErrorMessage = getErrorMessage;
  fieldLabels = fieldLabels

  inscreverForm: any = FormGroup;
  loginForm: any = FormGroup;

  respostaMensagem: any;

  constructor(
    public dialog: MatDialog,
    private router: Router,
    private datePipe: DatePipe,
    private formBuilder: FormBuilder,
    private usuarioService: UsuarioService,
    private snackbarService: SnackbarService,
  ) { }

  ngOnInit(): void {
    this.initFormInscriver();
    this.initFormLogin();
  }

  initFormLogin(){
    this.loginForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.pattern(ConstantesGeral.emailRegex)]],
      senha: [null, [Validators.required]],
    })
  }

  initFormInscriver(){
    this.inscreverForm = this.formBuilder.group({
      nome: [null, [Validators.required, Validators.pattern(ConstantesGeral.nomeRegex)]],
      email: [null, [Validators.required, Validators.pattern(ConstantesGeral.emailRegex)]],
      numeroContato: [null, [Validators.required, Validators.pattern(ConstantesGeral.numeroContatoRegex)]],
      senha: [null, [Validators.required]],
      cpf: [null, [Validators.required]],
      endereco: [null, [Validators.required]],
      dataNascimento: [null, [Validators.required]],
    })
  }

  login() {
    // this.ngxUiLoaderService.start();
    var formData = this.loginForm.value;
    var dados = {
      email: formData.email,
      senha: formData.senha
    }
    this.usuarioService.login(dados).subscribe((resp: any) => {
      // this.ngxUiLoaderService.stop();
      localStorage.setItem('token', resp.token);
      this.router.navigate(['/dashboard'])
    }, (error) => {
      // this.ngxUiLoaderService.stop();
      console.log("Erro ao fazer o login", error);
      if (error.error?.message) {
        this.respostaMensagem = error.error?.message;
      } else {
        this.respostaMensagem = ConstantesGeral.erroGenerico;
      }
      this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error);
    })
  }

  cadastrarNovoUsuario() {
    // this.ngxService.start();
    var dadosForm = this.inscreverForm.value;
    var dados = {
      nome: dadosForm.nome,
      email: dadosForm.email,
      numeroContato: dadosForm.numeroContato,
      senha: dadosForm.senha,
      cpf: dadosForm.cpf,
      endereco: dadosForm.endereco,
      dataNascimento: this.formatarDataParaBackend(dadosForm.dataNascimento),
    }

    this.usuarioService.signup(dados).subscribe((resp: any) => {
      // this.ngxService.stop();
      this.respostaMensagem = resp?.mensagem;
      this.snackbarService.openSnackBar(this.respostaMensagem, "");
      this.router.navigate(['/']);
    }, (error) => {
      // this.ngxService.stop();
      if (error.error?.mensagem) {
        this.respostaMensagem = error.error?.mensagem;
      } else {
        this.respostaMensagem = ConstantesGeral.erroGenerico;
      }

      this.snackbarService.openSnackBar(this.respostaMensagem, ConstantesGeral.error)
    })
  }

  toggleCadastroMode() {
    this.cadastroMode = !this.cadastroMode;
  }


  esqueciMinhaSenha() {
    const dialogRef = this.dialog.open(EsqueciMinhaSenhaDialogComponent, {
      width: '600px',
      height: '330px',
      data: { email: this.email }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('O diálogo foi fechado', result);
    });
  }

  formatarDataParaBackend(data: Date): string {
    return this.datePipe.transform(data, 'dd/MM/yyyy') || '';
  }

  // exibirErro(formControlName: string, form: string) {

  //   let control;

  //   if (form === 'inscreverForm') {
  //     control = this.inscreverForm.get(formControlName);
  //   } else {
  //     // control = this.loginForm.get(formControlName);
  //   }

  //   return control?.touched && control?.invalid;
  // }

  // getErrorMessage(formControlName: string, form: string): string {
  //   let control;
  //   if (form === 'inscreverForm') {
  //     control = this.inscreverForm.get(formControlName);
  //   } else {
  //     // control = this.loginForm.get(formControlName);
  //   }

  //   const friendlyName = this.fieldLabels[formControlName] || formControlName;
  //   if (control?.touched && control?.invalid) {
  //     if (control.errors?.required) {
  //       return `O campo ${friendlyName} é requerido`;
  //     }
  //     if (control.errors?.pattern) {
  //       return `O campo ${friendlyName} está inválido`;
  //     }
  //   }
  //   return '';
  // }

}
