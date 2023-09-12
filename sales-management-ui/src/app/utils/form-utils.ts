import { FormGroup } from '@angular/forms';

export const fieldLabels: { [key: string]: string } = {
  nome: 'Nome',
  senha: 'Senha',
  email: 'E-mail',
  preco: 'Preço',
  numeroContato: 'Numero de Contato',
  pagamento: 'Método de Pagamento',
  quantidade: 'Quantidade',
  total: 'Total',
  nomeCliente: 'Nome',
  cpfCliente : 'CPF',
  emailCliente  : 'E-mail',
  noContatoCliente: 'Contato',
};

export function exibirErro(formControlName: string, form: FormGroup): boolean {
  const control = form.get(formControlName);
  return (control?.touched && control?.invalid) || false;
}

export function getErrorMessage(formControlName: string, form: FormGroup, fieldLabels: { [key: string]: string }): string {
  const control = form.get(formControlName);
  const friendlyName = fieldLabels[formControlName] || formControlName;
  if (control?.touched && control?.invalid) {
      if (control.errors?.['required']) {
          return `O campo ${friendlyName} é requerido`;
      }
      if (control.errors?.['pattern']) {
          return `O campo ${friendlyName} está inválido`;
      }
  }
  return '';
}
