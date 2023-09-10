import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Categoria } from '../models/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {


  private url = `${environment.apiUrl}/categoria`;

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private httpClient: HttpClient) { }

  getAllCategoria(): Observable<any> {
    return this.httpClient.get(`${this.url}/getAllCategoria`);
  }

  addCategoria(categoria: any): Observable<any> {
    return this.httpClient.post(`${this.url}/addCategoria`, categoria, { headers: this.headers });
  }

  updateCategoria(categoria: any): Observable<any> {
    return this.httpClient.post(`${this.url}/updateCategoria`, categoria, { headers: this.headers });
  }
}
