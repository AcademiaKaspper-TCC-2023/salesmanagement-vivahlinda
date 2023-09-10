import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { SnackbarService } from './snackbar.service';
import { ConstantesGeral } from '../utils/constantes-geral';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {

  constructor(
    public authService: AuthService,
    public router: Router,
    private snackbarService: SnackbarService,
  ) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    let expectedRoleArray = route.data['expectedRole'];

    const token: any = localStorage.getItem('token');

    var tokenPayload: any;

    try {
      // Divide o token em cabeçalho e carga útil, e decodifica a carga útil
      const [headerBase64, payloadBase64] = token.split('.');
      const payload = JSON.parse(atob(payloadBase64));
      tokenPayload = payload;
    } catch (error) {
      // Se a decodificação falhar, limpa o armazenamento local e redireciona para a página inicial
      localStorage.clear();
      this.router.navigate(['/']);
    }

    // Percorre as "roles" esperadas e verifica se a role do usuário corresponde a alguma delas
    let expectedRole = '';
    for (let i = 0; i < expectedRoleArray.length; i++) {
      if (expectedRoleArray[i] == tokenPayload.role) {
        expectedRole = tokenPayload.role;
      }
    }

    if (tokenPayload.role === 'usuario' || tokenPayload.role === 'admin') {
      if (this.authService.isAuthenticated() && tokenPayload.role === expectedRole) {
        return true;
      }

      this.snackbarService.openSnackBar(ConstantesGeral.naoAutorizado, ConstantesGeral.error);
      this.router.navigate(['/dashboard']);
      return false;
    }

    else {
      this.router.navigate(['/']);
      localStorage.clear();
      return false;
    }
  }
}
