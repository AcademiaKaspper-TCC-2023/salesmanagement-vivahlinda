import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { saveAs } from 'file-saver';
import { CategoriaService } from 'src/app/services/categoria.service';
import { ProdutoService } from 'src/app/services/produto.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { VendaService } from 'src/app/services/venda.service';
import { ConstantesGeral } from 'src/app/utils/constantes-geral';
import { exibirErro, fieldLabels, getErrorMessage } from 'src/app/utils/form-utils';

@Component({
  selector: 'app-ordem-venda',
  templateUrl: './ordem-venda.component.html',
  styleUrls: ['./ordem-venda.component.css']
})
export class OrdemVendaComponent implements OnInit {

  isLoading = false;

  exibirErro = exibirErro;
  getErrorMessage = getErrorMessage;
  fieldLabels = fieldLabels;

  displayedColumns: string[] = [
    'nome',
    'categoria',
    'preco',
    'quantidade',
    'total',
    'editar',
  ];

  dataSource: any = [];

  gerenciarOrdemForm: any = FormGroup;

  categorias: any = [];

  produtos: any = [];

  preco: any;

  totalCompra: number = 0;

  respostaMensagem: any;

  metodosPagamento: string[] = [
    'Dinheiro',
    'Pix',
    'Cartão de Crédito',
    'Cartão de Débito',
    'Boleto Bancário',
  ];


  constructor(
    private formBuilder: FormBuilder,
    private categoriaService: CategoriaService,
    private produtoService: ProdutoService,
    private snackbarService: SnackbarService,
    private vendaService: VendaService,
  ) { }

  ngOnInit(): void {
    this.isLoading = true;
    this.getCategorias();
    this.gerenciarOrdemForm = this.formBuilder.group({
      nomeCliente: [
        null,
        [Validators.required, Validators.pattern(ConstantesGeral.nomeRegex)],
      ],
      emailCliente: [
        null,
        [Validators.required, Validators.pattern(ConstantesGeral.emailRegex)],
      ],
      noContatoCliente: [
        null,
        [
          Validators.required,
          Validators.pattern(ConstantesGeral.numeroContatoRegex),
        ],
      ],
      cpfCliente: [
        null,
        [Validators.required, Validators.pattern(ConstantesGeral.cpfRegex)],
      ],
      formaPagamento: [null, Validators.required],
      produto: [null, Validators.required],
      categoria: [null, Validators.required],
      quantidade: [null, Validators.required],
      preco: [null, Validators.required],
      total: [null, Validators.required],
    });
  }

  getCategorias() {
    this.categoriaService.getFilterCategoria().subscribe(
      (resp: any) => {
        this.isLoading = false;
        this.categorias = resp;
      },
      (error: any) => {
        console.log(error);
        this.isLoading = false;
        if (error.error?.message) {
          this.respostaMensagem = error.error?.message;
        } else {
          this.respostaMensagem = ConstantesGeral.erroGenerico;
        }
        this.snackbarService.openSnackBar(
          this.respostaMensagem,
          ConstantesGeral.error
        );
      }
    );
  }

  getProdutobyCategoria(valor: any) {
    this.produtoService.getByCategoria(valor.id).subscribe(
      (resp: any) => {
        this.produtos = resp;
        this.gerenciarOrdemForm.controls['preco'].setValue('');
        this.gerenciarOrdemForm.controls['quantidade'].setValue('');
        this.gerenciarOrdemForm.controls['total'].setValue(0);
      },
      (error: any) => {
        console.log('Erro ao buscar os produtos', error);
        if (error.error?.message) {
          this.respostaMensagem = error.error?.message;
        } else {
          this.respostaMensagem = ConstantesGeral.erroGenerico;
        }
        this.snackbarService.openSnackBar(
          this.respostaMensagem,
          ConstantesGeral.error
        );
      }
    );
  }

  getProdutoDetalhes(valor: any) {
    this.produtoService.getProdutoById(valor.id).subscribe(
      (resp: any) => {
        this.preco = resp.preco;
        this.gerenciarOrdemForm.controls['preco'].setValue(resp.preco);
        this.gerenciarOrdemForm.controls['quantidade'].setValue('1');
        this.gerenciarOrdemForm.controls['total'].setValue(this.preco * 1);
      },
      (error: any) => {
        if (error.error?.message) {
          this.respostaMensagem = error.error?.message;
        } else {
          this.respostaMensagem = ConstantesGeral.erroGenerico;
        }
        this.snackbarService.openSnackBar(
          this.respostaMensagem,
          ConstantesGeral.error
        );
      }
    );
  }

  setQuantidade(valor: any) {
    var qtd = this.gerenciarOrdemForm.controls['quantidade'].value;
    if (qtd > 0) {
      this.gerenciarOrdemForm.controls['total'].setValue(
        this.gerenciarOrdemForm.controls['quantidade'].value *
        this.gerenciarOrdemForm.controls['preco'].value
      );
    } else if (qtd != '') {
      this.gerenciarOrdemForm.controls['quantidade'].setValue(1);
      this.gerenciarOrdemForm.controls['total'].setValue(
        this.gerenciarOrdemForm.controls['quantidade'].value *
        this.gerenciarOrdemForm.controls['preco'].value
      );
    }
  }

  validarAddProduto() {
    if (
      this.gerenciarOrdemForm.controls['total'].value === 0 ||
      this.gerenciarOrdemForm.controls['total'].value === null ||
      this.gerenciarOrdemForm.controls['quantidade'].value <= 0
    ) {
      return true;
    } else {
      return false;
    }
  }

  validarSubmit() {
    if (
      this.totalCompra === 0 ||
      this.gerenciarOrdemForm.controls['nomeCliente'].value === null ||
      this.gerenciarOrdemForm.controls['emailCliente'].value === null ||
      this.gerenciarOrdemForm.controls['noContatoCliente'].value === null ||
      this.gerenciarOrdemForm.controls['cpfCliente'].value === null ||
      this.gerenciarOrdemForm.controls['formaPagamento'].value === null
    ) {
      return true;
    } else {
      return false;
    }
  }

  Adicionar() {
    var formData = this.gerenciarOrdemForm.value;
    var nomeProduto = this.dataSource.find(
      (e: { id: number }) => e.id === formData.produto.id
    );

    if (nomeProduto === undefined) {
      this.totalCompra = this.totalCompra + formData.total;
      this.dataSource.push({
        id: formData.produto.id,
        nome: formData.produto.nome,
        categoria: formData.categoria.nome,
        quantidade: formData.quantidade,
        preco: formData.preco,
        total: formData.total,
      });

      this.dataSource = [...this.dataSource];
      this.snackbarService.openSnackBar(
        ConstantesGeral.produtoAdicionadoSucesso,
        ConstantesGeral.success
      );
    } else {
      this.snackbarService.openSnackBar(
        ConstantesGeral.produtoJaexisteError,
        ConstantesGeral.error
      );
    }
  }

  handleDeleteAction(valor: any, element: any) {
    this.totalCompra = this.totalCompra - element.total;
    this.dataSource.splice(valor, 1);
    this.dataSource = [...this.dataSource];
  }

  submitAction() {
    var formData = this.gerenciarOrdemForm.value;
    var dados = {
      nomeCliente: formData.nomeCliente,
      emailCliente: formData.emailCliente,
      noContatoCliente: formData.noContatoCliente,
      formaPagamento: formData.formaPagamento,
      cpfCliente: formData.cpfCliente,
      totalCompra: this.totalCompra.toString(),
      detalheProdutosVendidos: JSON.stringify(this.dataSource),
    };

    this.isLoading = true;

    this.vendaService.gerarRelatorio(dados).subscribe(
      (resp: any) => {
        this.downloadArquivo(resp?.uuid);
        this.gerenciarOrdemForm.reset();
        this.dataSource = [];
        this.totalCompra = 0;
      },
      (error: any) => {
        this.isLoading = false;
        console.log(error);
        if (error.error?.message) {
          this.respostaMensagem = error.error?.message;
        } else {
          this.respostaMensagem = ConstantesGeral.erroGenerico;
        }
        this.snackbarService.openSnackBar(
          this.respostaMensagem,
          ConstantesGeral.error
        );
      }
    );
  }

  downloadArquivo(nomeArquivo: string) {
    var dados = {
      uuid: nomeArquivo,
    };

    this.vendaService.getPdf(dados).subscribe((response: any) => {
      saveAs(response, nomeArquivo + '.pdf');
      this.isLoading = false;
    });
  }


}
