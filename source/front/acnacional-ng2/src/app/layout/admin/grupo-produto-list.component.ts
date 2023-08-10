import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { GrupoProduto, Page, Produto, TipoProduto } from '../../shared/models';
import { LocalStorageService, Logger, GrupoProdutoService, TipoProdutoService } from '../../shared/services';

@Component({
  selector: 'app-grupo-produto-list',
  templateUrl: './grupo-produto-list.component.html',
  styleUrls: ['./grupo-produto-list.component.css']
})
export class GrupoProdutoListComponent extends AbstractComponent implements OnInit {

  text: string;

  pageNumber: number;

  pageSize: number;

  totalElements: number;

  page: Page<Produto>;

  tipo = '';

  tipoList: Array<TipoProduto>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private tipoProdutoService: TipoProdutoService, private grupoProdutoService: GrupoProdutoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('GrupoGrupoProdutoListComponent.ngOnInit();');
    this.text = '';
    this.pageNumber = 1;
    this.pageSize = 10;

    const queryData = this.restoreQueryData('GrupoGrupoProdutoListComponent_QueryData');
    if (queryData) {
      try {
        const json = JSON.parse(queryData.text);
        this.text = json.text;
        this.tipo = json.tipo;
      } catch (e) {
        this.text = queryData.text;
      }
      this.pageNumber = queryData.pageNumber;
      this.pageSize = queryData.pageSize;
    }

    this.tipoProdutoService.list(false, '', 0, 1000).subscribe(
      page => {
        this.tipoList = page.content;
        if (this.tipoList.length > 0) {
          this.tipo = this.tipoList[0].id;
        }
        this.doList();
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public list(): void {
    this.logger.debug('GrupoProdutoListComponent.list();');
    this.pageNumber = 1;
    this.doList();
  }

  public clear(): void {
    this.logger.debug('GrupoProdutoListComponent.clear();');
    if (this.text) {
      this.text = '';
      this.pageNumber = 1;
      this.doList();
    }
  }

  public onChangeTipo(): void {
    this.logger.debug('GrupoProdutoListComponent.onChangeTipo();');
    this.doList();
  }

  public onPageChange(event: any): void {
    this.logger.debug('New Page: ' + event);
    this.doList();
  }

  private doList(): void {
    const data = JSON.stringify({
      tipo: this.tipo,
      text: this.text
    });
    this.storeQueryData('GrupoProdutoListComponent_QueryData', data, this.pageNumber, this.pageSize);
    const observable = this.grupoProdutoService.list(this.tipo, false, this.text, this.pageNumber - 1, this.pageSize);
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
