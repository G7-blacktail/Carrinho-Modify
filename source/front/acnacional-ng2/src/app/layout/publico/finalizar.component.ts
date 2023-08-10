import { Component, ComponentRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal, NgbModalOptions } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';

import { AbstractComponent, DialogComponent } from '../../shared/components';
import { Page, Pedido, PedidoProduto, Posto, Produto } from '../../shared/models';
import { LocalStorageService, Logger, CompraService, PostoService, SessionService } from '../../shared/services';

@Component({
  selector: 'app-finalizar',
  templateUrl: './finalizar.component.html',
  styleUrls: ['./finalizar.component.css']
})
export class FinalizarComponent extends AbstractComponent implements OnInit {

  item: Pedido;

  posto: string;

  postoList: Array<Posto>;

  currentPedidoProduto: PedidoProduto;

  cep : string = '';

  form!: FormGroup;

  valorFrete: number = 0.00; // Valor do frete inicialmente como 0.

  private fb: FormBuilder = new FormBuilder();

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private route: ActivatedRoute, private router: Router,
    private compraService: CompraService, private postoService: PostoService, private sessionService: SessionService) {
    super(logger, localStorageService, modalService);
  }

  formatarCEP() {
    // Obter o valor do campo CEP do formulário
    let cepSemMascara = this.form.get('cep')?.value.replace(/\D/g, '');

    // Limitar a quantidade de caracteres numéricos a 8
    cepSemMascara = cepSemMascara.substring(0, 8);

    // Aplicar a máscara de CEP: "00000-000"
    if (cepSemMascara.length > 5) {
      cepSemMascara = cepSemMascara.substring(0, 5) + '-' + cepSemMascara.substring(5, 8);
    }

    // Atualizar o valor do campo com o CEP formatado
    this.form.get('cep')?.setValue(cepSemMascara, { emitEvent: false });
  }

  public calcularFrete(): void {
    this.cep = this.form.get('cep').value;
    // Faça a lógica de verificação do CEP e cálculo do valor do frete.
    // Por exemplo, você pode chamar um serviço que realiza a consulta do CEP em uma API de correios ou outra fonte de dados.
    const valorFrete: number = this.calcularValorFrete(this.cep); // Substitua essa linha pela lógica real de cálculo do frete.
    this.valorFrete = Number(valorFrete); // Utilize 'toFixed' para formatar o valor com 2 casas decimais.

    // Após calcular o valor do frete, recalcule o valor total do pedido
    this.calcularValorTotal();
  }


  private calcularValorFrete(cep: string): number {
    // Exemplo de função fictícia para calcular o valor do frete.
    // Aqui você pode implementar a lógica de consulta do CEP em uma API de correios ou outra fonte de dados.
    // Por enquanto, vamos apenas retornar um valor fixo como exemplo.
    return 10;
  }

  private calcularValorTotal(): void {
    let totalProdutos = 0;

    if (this.item.produtoList) {
      for (const produto of this.item.produtoList) {
        totalProdutos += produto.valor;
      }
    }

    this.item.valor = totalProdutos;
    this.item.valor += this.valorFrete;
  }


  public ngOnInit() {
    this.logger.debug('Comprar.ngOnInit();');

    this.postoService.list('', '', true, '', 0, 100).subscribe(
      page => {
        this.postoList = page.content.filter(p => p.codigo === 'ITECLIDERSIS' || p.codigo === 'ONVIDEO');
        this.posto = this.postoList[0].id;
      },
      response => {
        this.onError(response);
      },
      () => this.logger.debug('Complete')
    );



    this.currentPedidoProduto = null;

    this.route.params.subscribe((params: Params) => {
      if (params['id']) {
        const id: string = params['id'];
        this.logger.debug('ID:' + id);
        this.compraService.get(id).subscribe(
          item => {
            this.item = item;
            if ((!item.produtoList) || (item.produtoList.length === 0)) {
              this.router.navigate(['/publico']);
            } else {
              this.route.queryParams.subscribe((queryParams: Params) => {
                if (queryParams['pedido_produto_id']) {
                  const pedidoProdutoId = queryParams['pedido_produto_id'];
                  let cpp = this.item.produtoList.find(pp => pp.id == pedidoProdutoId);
                  if (cpp) {
                    this.currentPedidoProduto = cpp;
                  } else {
                    this.currentPedidoProduto = null;
                  }
                }
              });
            }
          },
          response => this.onError(response),
          () => this.logger.debug('Complete')
        );
      }
    });

      // Criar o formulário com a validação do campo CEP
      this.form = this.fb.group({
        // ... Outros campos do formulário, se houver
        cep: ['', [Validators.required, Validators.pattern(/^\d{5}-\d{3}$/)]],
      });
      this.calcularValorTotal();

  }

  public isLoggedIn(): boolean {
    return this.sessionService.isLoggedIn();
  }

  public login(): void {
    this.sessionService.login();
  }

  public onIncItem(p: PedidoProduto): void {
    this.logger.debug('FinalizarComponent.onIncItem();');
    p.quantidade++;
    this.onAdd(p);
  }

  public onDecItem(p: PedidoProduto): void {
    this.logger.debug('FinalizarComponent.onDecItem();');
    if (p.quantidade > 1) {
      p.quantidade--;
      this.onAdd(p);
    } else {
      this.onRemove(p.id);
    }
  }

  public onAdd(p: PedidoProduto): void {
    this.logger.debug('FinalizarComponent.onAdd();');
    this.compraService.update(this.item.id, p).subscribe(
      item => {
        this.item = item;
        if (this.currentPedidoProduto) {
          let cpp = this.item.produtoList.find(pp => pp.id == this.currentPedidoProduto.id);
          if (cpp) {
            this.currentPedidoProduto = cpp;
          }
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onRemove(id: string) {
    this.logger.debug('ComprarComponent.onRemove();');
    this.compraService.remove(this.item.id, id).subscribe(
      item => {
        this.item = item;
        if ((!item.produtoList) || (item.produtoList.length === 0)) {
          this.router.navigate(['/publico']);
        } else {
          if (this.currentPedidoProduto.id == id) {
            this.currentPedidoProduto = null;
          }
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onSubmit(): void {
    this.logger.debug('ProdutoEditComponent.onSubmit();');

    if ((!this.item.produtoList) || (this.item.produtoList.length === 0)) {
      this.onError('Nenhum produto foi selecionado');
      return;
    }

    if (this.form.invalid) {
      // Formulário inválido, exiba uma mensagem de erro ou faça o tratamento apropriado
      return;
    }

    const ngbModalOptions: NgbModalOptions = {
      backdrop: 'static',
      keyboard: false
    };

    const ref = this.modalService.open(DialogComponent, ngbModalOptions);
    const instance: DialogComponent = ref.componentInstance as DialogComponent;
    instance.configure('Aguarde', ['Estamos processando seu pedido'], 'primary', false);

    const obj: Pedido = {
      id: this.item.id,
      posto: {
        id: this.posto
      },
    };

    this.compraService.end(obj).subscribe(
      item => {
        ref.close();
        this.store('pedido', null); // Remove o pedido
        this.router.navigate(['/privado/pedido/view', item.id], { queryParams: { 'new': true } });
      },
      response => {
        ref.close();
        this.onError(response);
      },
      () => {
        this.logger.debug('Complete');
        // Após finalizar o pedido, chame a função para recalcular o valor total do pedido com o valor do frete atualizado.
        this.calcularValorTotal();
      }
    );
  }



}
