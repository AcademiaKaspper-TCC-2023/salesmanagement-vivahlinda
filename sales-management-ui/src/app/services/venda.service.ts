import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VendaService {

  private url = `${environment.apiUrl}/venda`;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  getVendasMensais(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/vendasMensais`);
  }

  getPdf(dados: any): Observable<Blob> {
    return this.http.post(`${this.url}/getPdf`, dados, { responseType: 'blob' });
  }

  gerarRelatorio(dados: any) {
    return this.http.post(`${this.url}/gerarRelatorio`, dados, { headers: this.headers });
  }

  getVenda(): Observable<any> {
    return this.http.get(`${this.url}/getVenda`);
  }

  deleteById(id: any) {
    return this.http.post(`${this.url}/delete/` + id, {
      headers: this.headers,
    });
  }
}
