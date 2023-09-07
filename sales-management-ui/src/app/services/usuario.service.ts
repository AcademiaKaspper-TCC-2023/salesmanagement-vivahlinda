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

  alterarSenha(dados: any): Observable<any> {
    return this.httpClient.post(this.url + "/usuario/alterarSenha", dados, { headers: new HttpHeaders().set('Content-Type', 'application/json') });
  }
}
