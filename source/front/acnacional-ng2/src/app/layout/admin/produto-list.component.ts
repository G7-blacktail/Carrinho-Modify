import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { GrupoProduto, Page, Produto, SubGrupoProduto, TipoProduto } from '../../shared/models';
import { LocalStorageService, Logger, GrupoProdutoService, ProdutoService, SubGrupoProdutoService, TipoProdutoService } from '../../shared/services';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrls: ['./produto-list.component.css']
})
export class ProdutoListComponent extends AbstractComponent implements OnInit {

  text: string;

  pageNumber: number;

  pageSize: number;

  totalElements: number;

  page: Page<Produto>;

  tipo = '';

  tipoList: Array<TipoProduto>;

  grupo = '';

  grupoList: Array<GrupoProduto>;

  subGrupo = '';

  subGrupoList: Array<SubGrupoProduto>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private produtoService: ProdutoService, private tipoProdutoService: TipoProdutoService,
    private grupoProdutoService: GrupoProdutoService, private subGrupoProdutoService: SubGrupoProdutoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('ProdutoListComponent.ngOnInit();');
    this.text = '';
    this.pageNumber = 1;
    this.pageSize = 10;

    const queryData = this.restoreQueryData('ProdutoListComponent_QueryData');
    if (queryData) {
      try {
        const json = JSON.parse(queryData.text);
        this.text = json.text;
        this.tipo = json.tipo;
        this.grupo = json.grupo;
        this.subGrupo = json.subGrupo;
      } catch (e) {
        this.text = queryData.text;
      }
      this.pageNumber = queryData.pageNumber;
      this.pageSize = queryData.pageSize;
    }

    this.tipoProdutoService.list(false, '', 0, 1000).subscribe(
      pageT => {
        this.tipoList = pageT.content;
        if (this.tipo) {
          this.grupoProdutoService.list(this.tipo, false, '', 0, 1000).subscribe(
            pageG => {
              this.grupoList = pageG.content;
              if (this.grupo) {
                this.subGrupoProdutoService.list(this.tipo, this.grupo, false, '', 0, 1000).subscribe(
                  pageS => {
                    this.subGrupoList = pageS.content;
                    this.doList();
                  },
                  response => this.onError(response),
                  () => this.logger.debug('Complete')
                );
              } else {
                this.doList();
              }
            },
            response => this.onError(response),
            () => this.logger.debug('Complete')
          );
        } else {
          this.doList();
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public list(): void {
    this.logger.debug('ProdutoListComponent.list();');
    this.pageNumber = 1;
    this.doList();
  }

  public clear(): void {
    this.logger.debug('ProdutoListComponent.clear();');
    if (this.text) {
      this.text = '';
      this.pageNumber = 1;
      this.doList();
    }
  }

  public onChangeTipo(reset: boolean = true): void {
    this.logger.debug('ProdutoListComponent.onChangeTipo();');
    if (reset) {
      this.grupo = '';
      this.subGrupo = '';
    }
    this.grupoList = null;
    this.subGrupoList = null;
    if (this.tipo) {
      this.grupoProdutoService.list(this.tipo, false, '', 0, 1000).subscribe(
        page => {
          this.grupoList = page.content;
        },
        response => this.onError(response),
        () => this.logger.debug('Complete')
      );
    }
  }

  public onChangeGrupo(reset: boolean = true): void {
    this.logger.debug('ProdutoListComponent.onChangeGrupo();');
    if (reset) {
      this.subGrupo = '';
    }
    this.subGrupoList = null;
    if (this.grupo) {
      this.subGrupoProdutoService.list(this.tipo, this.grupo, false, '', 0, 1000).subscribe(
        page => {
          this.subGrupoList = page.content;
        },
        response => this.onError(response),
        () => this.logger.debug('Complete')
      );
    }
  }

  public onPageChange(event: any): void {
    this.logger.debug('New Page: ' + event);
    this.doList();
  }

  private doList(): void {
    const data = JSON.stringify({
      tipo: this.tipo,
      grupo: this.grupo,
      subGrupo: this.subGrupo,
      text: this.text
    });
    this.storeQueryData('ProdutoListComponent_QueryData', data, this.pageNumber, this.pageSize);
    const observable = this.produtoService.list(this.tipo, this.grupo, this.subGrupo, false, this.text, this.pageNumber - 1, this.pageSize);
    observable.subscribe(
      page => {
        this.page = page;
        this.pageNumber = page.number + 1;
        this.pageSize = page.size;
        this.totalElements = page.totalElements;
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

}
