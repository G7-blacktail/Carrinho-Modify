import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { GrupoProduto, Page, SubGrupoProduto, TipoProduto } from '../../shared/models';
import { LocalStorageService, Logger, GrupoProdutoService, SubGrupoProdutoService, TipoProdutoService } from '../../shared/services';

@Component({
  selector: 'app-subgrupo-produto-list',
  templateUrl: './subgrupo-produto-list.component.html',
  styleUrls: ['./subgrupo-produto-list.component.css']
})
export class SubGrupoProdutoListComponent extends AbstractComponent implements OnInit {

  text: string;

  pageNumber: number;

  pageSize: number;

  totalElements: number;

  page: Page<SubGrupoProduto>;

  tipo = '';

  tipoList: Array<TipoProduto>;

  grupo = '';

  grupoList: Array<GrupoProduto>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private tipoProdutoService: TipoProdutoService, private grupoProdutoService: GrupoProdutoService,
    private subGrupoProdutoService: SubGrupoProdutoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('SubGrupoProdutoListComponent.ngOnInit();');
    this.text = '';
    this.pageNumber = 1;
    this.pageSize = 10;

    const queryData = this.restoreQueryData('SubGrupoProdutoListComponent_QueryData');
    if (queryData) {
      try {
        const json = JSON.parse(queryData.text);
        this.text = json.text;
        this.tipo = json.tipo;
        this.grupo = json.grupo;
      } catch (e) {
        this.text = queryData.text;
      }
      this.pageNumber = queryData.pageNumber;
      this.pageSize = queryData.pageSize;
    }

    this.tipoProdutoService.list(false, '', 0, 1000).subscribe(
      pageT => {
        this.tipoList = pageT.content;
        if (this.tipoList.length > 0) {
          this.tipo = this.tipoList[0].id;
        }
        if (this.tipo) {
          this.grupoProdutoService.list(this.tipo, false, '', 0, 1000).subscribe(
            pageG => {
              this.grupoList = pageG.content;
              if (this.grupoList.length > 0) {
                this.grupo = this.grupoList[0].id;
              }
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
  }

  public list(): void {
    this.logger.debug('SubGrupoProdutoListComponent.list();');
    this.pageNumber = 1;
    this.doList();
  }

  public clear(): void {
    this.logger.debug('SubGrupoProdutoListComponent.clear();');
    if (this.text) {
      this.text = '';
      this.pageNumber = 1;
      this.doList();
    }
  }

  public onChangeTipo(): void {
    this.logger.debug('SubGrupoProdutoListComponent.onChangeTipo();');
    this.grupoList = null;
    if (this.tipo) {
      this.grupoProdutoService.list(this.tipo, false, '', 0, 1000).subscribe(
        page => {
          this.grupoList = page.content;
          if (this.grupoList.length > 0) {
            this.grupo = this.grupoList[0].id;
            this.doList();
          }
        },
        response => this.onError(response),
        () => this.logger.debug('Complete')
      );
    }
  }

  public onChangeGrupo(): void {
    this.logger.debug('SubGrupoProdutoListComponent.onChangeGrupo();');
    this.doList();
  }

  public onPageChange(event: any): void {
    this.logger.debug('New Page: ' + event);
    this.doList();
  }

  private doList(): void {
    const data = JSON.stringify({
      tipo: this.tipo,
      grupo: this.grupo,
      text: this.text
    });
    this.storeQueryData('SubGrupoProdutoListComponent_QueryData', data, this.pageNumber, this.pageSize);
    const observable = this.subGrupoProdutoService.list(this.tipo, this.grupo, false, this.text, this.pageNumber - 1, this.pageSize);
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
