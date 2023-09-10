import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { EsqueciMinhaSenhaDialogComponent } from 'src/app/esqueci-minha-senha-dialog/esqueci-minha-senha-dialog.component';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UsuarioService } from 'src/app/services/usuario.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';

import { exibirErro, fieldLabels, getErrorMessage } from '../../utils/form-utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string = '';
  senha: string = '';
  cadastroMode: boolean = false;

  isLoading = false;

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
    this.checkToken();
    this.initFormInscriver();
    this.initFormLogin();
  }

  checkToken() {
    this.usuarioService.checkToken().subscribe({
      next: (resp: any) => {
        console.log("token checadoooooo", resp);
        this.router.navigate(['/dashboard']);
      },
      error: (error: any) => {
        console.log('token não encontrado', error);
      }
    });

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
    this.isLoading = true;
    var formData = this.loginForm.value;
    var dados = {
      email: formData.email,
      senha: formData.senha
    }
    this.usuarioService.login(dados).subscribe((resp: any) => {
      this.isLoading = false;
      localStorage.setItem('token', resp.token);
      this.router.navigate(['/dashboard'])
    }, (error) => {
      this.isLoading = false;
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
    this.isLoading = true;
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
      this.isLoading = false;
      this.respostaMensagem = resp?.mensagem;
      this.snackbarService.openSnackBar(this.respostaMensagem, "");
      this.router.navigate(['/entrar']);
      this.toggleCadastroMode();
    }, (error) => {
      this.isLoading = false;
      if (error.error?.mensagem) {
        this.respostaMensagem = error.error?.mensagem;
      } else {
        this.respostaMensagem = ConstantesGeral.erroGenerico;
      }

      this.router.navigate(['/entrar']);
      this.inscreverForm.reset();

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
}
