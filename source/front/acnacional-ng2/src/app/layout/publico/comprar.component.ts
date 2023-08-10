import { Component, ComponentRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable, forkJoin } from 'rxjs';

import { AbstractComponent } from '../../shared/components';
import { ConvenioDialogComponent } from './convenio-dialog.component';
import { Page, Convenio, GrupoProduto, Pedido, PedidoProduto, Produto, SubGrupoProduto, TipoProduto } from '../../shared/models';
import { LocalStorageService, Logger, CompraService,  GrupoProdutoService, ProdutoService,
         SubGrupoProdutoService, TipoProdutoService  } from '../../shared/services';

@Component({
  selector: 'app-comprar',
  templateUrl: './comprar.component.html',
  styleUrls: ['./comprar.component.css']
})
export class ComprarComponent extends AbstractComponent implements OnInit {

  item: Pedido;

  editId: string;

  current: Produto;

  quantidade: number;

  quantidadeList: Array<number>;

  total: string;

  tipo: string;

  tipoList: Array<TipoProduto>;

  tipoLoading = false;

  grupo: string;

  grupoList: Array<GrupoProduto>;

  grupoLoading = false;

  produto: string;

  produtoList: Array<Produto>;

  produtoLoading = false;

  exibeValidade = false;

  test = false;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
              private route: ActivatedRoute, private router: Router, private compraService: CompraService,
              private produtoService: ProdutoService, private tipoProdutoService: TipoProdutoService,
              private grupoProdutoService: GrupoProdutoService, private subGrupoProdutoService: SubGrupoProdutoService) {
    super(logger, localStorageService, modalService);

    this.quantidadeList = new Array();
    for (let i = 1; i <= 20; i++) {
      this.quantidadeList.push(i);
    }
  }

  public ngOnInit() {
    this.logger.debug('ComprarComponent.ngOnInit();');
    this.route.queryParams.subscribe(
      params => {
        if (params['produto']) {
          // this.doInitByProduto(params['produto']);
          this.doInitByProduto(params['produto']);
        } else if (params['grupo']) {
          this.doInitByGrupo(params['tipo'], params['grupo']);
        } else {
          this.doInit();
        }
      }
    );
  }

  private doInitByProduto(produto: string): void {
    this.logger.debug('ComprarComponent.doInitByProdutoV2();');
    this.produtoService.get(produto).subscribe(
      item => {
        if (item) {
          this.current = item;
          this.produto = item.id;
          this.doGetItem(() => {
            this.onSelectProduto(this.current);
          });
        } else {
          this.doInit();
        }
      },
      response => {
        this.doInit();
      },
      () => this.logger.debug('Complete')
    );
  }

  private doInitByGrupo(tipo: string, grupo: string): void {
    this.logger.debug('ComprarComponent.doInitByGrupo();');
    this.tipoLoading = true;
    this.grupoLoading = true;
    this.produtoLoading = true;
    this.grupoProdutoService.get(grupo).subscribe(
      item => {
        if (item) {
          this.grupo = item.id;
          this.tipo = item.tipo.id;

          const obs1 = this.tipoProdutoService.list(true, '', 0, 1000);
          const obs2 = this.grupoProdutoService.list(this.tipo, true, '', 0, 1000);
          const obs3 = this.subGrupoProdutoService.list(this.tipo, this.grupo, true, '', 0, 1000);

          forkJoin([obs1, obs2, obs3]).subscribe(
            (arr: Array<Page<any>>) => {
              console.log(arr);
              this.tipoList = arr[0].content;
              this.grupoList = arr[1].content;

              this.tipoLoading = false;
              this.grupoLoading = false;

              this.produtoService.list(this.tipo, this.grupo, '', true, '', 0, 1000).subscribe(
                page => {
                  this.produtoList = page.content;
                  this.produtoLoading = false;
                  if (this.produtoList.length > 0) {
                    this.produto = this.produtoList[0].id;
                    // this.onSelectProduto(this.produtoList[0]);
                  }
                  this.doGetItem();
                },
                response => {
                  this.doInit();
                },
                () => this.logger.debug('Complete')
              );
            },
            response => {
              this.doInit();
            },
            () => this.logger.debug('Complete')
          );
        } else {
          this.doInit();
        }
      },
      response => {
        this.doInit();
      },
      () => this.logger.debug('Complete')
    );
  }

  private doInit(): void {
    this.tipoLoading = true;
    this.grupoLoading = true;
    this.produtoLoading = true;
    this.tipoProdutoService.list(true, '', 0, 1000).subscribe(
      page => {
        this.tipoList = page.content;
        if (this.tipoList.length > 0) {
          this.tipo = this.tipoList[0].id;
          this.tipoLoading = false;
          this.onChangeTipo();
        }
      },
      response => {
        this.tipoLoading = false;
        this.onError(response);
      },
      () => this.logger.debug('Complete')
    );
    this.doGetItem();
  }

  private doGetItem(cb: Function = null) {
    this.item = this.restore('pedido');
    if (!this.item) {
      this.logger.debug('Criando um novo pedido');
      this.compraService.begin().subscribe(
        item => {
          this.logger.debug('Pedido criado');
          this.item = item;
          this.store('pedido', item);
          if (cb) {
            cb();
          }
        },
        response => this.onError(response),
        () => this.logger.debug('Complete')
      );
    } else {
      const observable = this.compraService.get(this.item.id);
      observable.subscribe(
        item => {
          this.item = item;
          if (cb) {
            cb();
          }
        },
        response => {
          this.compraService.begin().subscribe(
            item => {
              this.logger.debug('Pedido criado');
              this.item = item;
              this.store('pedido', item);
              if (cb) {
                cb();
              }
            },
            response2 => this.onError(response2),
            () => this.logger.debug('Complete')
          );
        },
        () => this.logger.debug('Complete')
      );
      this.logger.debug(`Pedido: ${this.item.id}`);
    }
  }

  public onChangeTipo(): void {
    this.logger.debug('ComprarComponent.onChangeTipo();');
    this.grupoLoading = true;
    this.grupoList = null;
    this.produtoList = null;
    this.current = null;
    this.produto = null;
    this.grupoProdutoService.list(this.tipo, true, '', 0, 1000).subscribe(
      page => {
        this.grupoList = page.content;
        this.grupoLoading = false;
        if (this.grupoList.length > 0) {
          this.grupo = this.grupoList[0].id;
          this.onChangeGrupo(this.grupoList[0].codigo);
        }
      },
      response => {
        this.grupoLoading = false;
        this.onError(response);
      },
      () => this.logger.debug('Complete')
    );
  }

  public onChangeGrupo(codigo: string): void {
    this.logger.debug('ComprarComponent.onChangeGrupo();');
    this.produtoLoading = true;
    this.produtoList = null;
    this.current = null;
    this.produto = null;
    this.produtoService.list(this.tipo, this.grupo, '', true, '', 0, 1000).subscribe(
      page => {
        this.produtoList = page.content;
        this.produtoLoading = false;
        if (this.produtoList.length > 0) {
          this.produto = this.produtoList[0].id;
          //this.onSelectProduto(this.produtoList[0]);
        }
      },
      response => {
        this.produtoLoading = false;
        this.onError(response);
      },
      () => this.logger.debug('Complete')
    );
  }

  public onSelectProduto(p: Produto): void {
    this.logger.debug('ComprarComponent.onSelectProduto();');
    this.total = p.valor;
    this.quantidade = 1;
    this.current = p;
    this.onAdd((item: PedidoProduto) => {
      this.router.navigate(['/publico/finalizar', this.item.id], {queryParams: {"pedido_produto_id": item.id}});
    });
  }

  public onAdd(cb: Function = null): void {
    this.logger.debug('ComprarComponent.onAdd();');
    const pp: PedidoProduto = {
      pedido: {id: this.item.id},
      produto: {id: this.current.id},
      quantidade: this.quantidade
    };

    let observable: Observable<PedidoProduto> =  null;

    if (this.editId) {
      pp.id = this.editId;
      observable = this.compraService.updateSimple(this.item.id, pp);
    } else {
      observable = this.compraService.addSimple(this.item.id, pp);
    }

    observable.subscribe(
      item => {
        if (cb) {
          cb(item);
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

}
