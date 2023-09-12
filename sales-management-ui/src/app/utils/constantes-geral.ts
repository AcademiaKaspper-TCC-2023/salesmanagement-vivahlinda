export class ConstantesGeral {
  public static erroGenerico: string =
    'Ops! Algo deu errado :( . Por favor, tente novamente mais tarde';

  public static naoAutorizado: string =
    'Você não tem autorização para acessar esta página.';

  public static produtoJaexisteError: string = 'O produto já existe';

  public static produtoAdicionadoSucesso: string =
    'O produto foi adicionado com sucesso!';

  public static erroTabela: string = "Ocorreu um erro ao buscar os dados";

  public static nomeRegex: string = '[a-zA-Z0-9À-ú ]*';

  public static emailRegex: string =
    '[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}';

  // public static numeroContatoRegex: string = '^[e0-9]{11,11}$';
  public static numeroContatoRegex: string = '^[0-9]{11}$';

  public static precoRegex: string = '^[0-9]+$';

  public static error: string = 'error';

  public static success: string = 'success';

  public static perfil: string = 'Perfil do usuário:';
  static erroPerfil: string = 'Erro ao buscar o perfil do usuário: ';

  public static cpfRegex: string = '^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$';
}
