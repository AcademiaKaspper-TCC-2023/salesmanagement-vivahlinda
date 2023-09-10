import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

   constructor(private router: Router) { }

   public isAuthenticated(): boolean {
     const token = localStorage.getItem('token');

     if (!token) {
       this.router.navigate(['/']);
       console.log("NÃO ESTÁ AUTENTICADO");
       return false;
     } else {
       console.log("ESTÁ AUTENTICADO");
       return true;
     }
   }
}
