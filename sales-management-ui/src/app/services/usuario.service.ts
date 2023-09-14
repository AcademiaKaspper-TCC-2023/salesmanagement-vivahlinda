import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  url = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  signup(dados: any) {
    return this.httpClient.post(this.url + "/usuario/inscrever", dados, { headers: new HttpHeaders().set('Content-Type', 'application/json') })
  }

  alterarSenha(dados: any): Observable<any> {
    return this.httpClient.post(this.url + "/usuario/alterarSenha", dados, { headers: new HttpHeaders().set('Content-Type', 'application/json') });
  }

  login(dados: any) {
    return this.httpClient.post(this.url + "/usuario/entrar", dados, { headers: new HttpHeaders().set('Content-Type', 'application/json') })
  }

  esqueciMinhaSenha(dados: any) {
    return this.httpClient.post(this.url + "/usuario/recuperarSenha", dados, { headers: new HttpHeaders().set('Content-Type', 'application/json') })
  }

  checkToken() {
    return this.httpClient.get(this.url + "/usuario/checkToken")
  }

  perfil() {
    return this.httpClient.get(this.url + "/usuario/perfil")
  }

  findAllUsuario() {
    return this.httpClient.get(this.url + "/usuario/get")
  }

  update(dados: any) {
    return this.httpClient.post(this.url + "/usuario/update", dados, { headers: new HttpHeaders().set('Content-Type', 'application/json') })
  }
}
