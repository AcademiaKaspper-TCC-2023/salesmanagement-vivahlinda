import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  private url = `${environment.apiUrl}/produto`;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private httpClient: HttpClient) { }

  deleteProduto(id: number): Observable<any> {
    return this.httpClient.post(`${this.url}/delete/${id}`, null);
  }

  addNewProduto(data: any): Observable<any> {
    return this.httpClient.post(`${this.url}/addNewProdruto`, data);
  }

  updateProduto(data: any): Observable<any> {
    return this.httpClient.post(`${this.url}/updateProdruto`, data);
  }

  getAllProduto(): Observable<any> {
    return this.httpClient.get(`${this.url}/getAllProduto`);
  }

  updateStatus(dados: any) {
    return this.httpClient.post(`${this.url}/updateStatus`, dados, { headers: this.headers });
  }

}
