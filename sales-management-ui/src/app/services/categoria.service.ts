import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../environments/environment';

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

  getFilterCategoria() {
    return this.httpClient.get(`${this.url}/getAllCategoria?filterValue=true`);
  }
}
