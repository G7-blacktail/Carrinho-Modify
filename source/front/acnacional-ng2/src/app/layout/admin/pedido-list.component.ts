import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Pedido, Page } from '../../shared/models';
import { LocalStorageService, Logger, PedidoService } from '../../shared/services';

@Component({
  selector: 'app-pedido-list',
  templateUrl: './pedido-list.component.html',
  styleUrls: ['./pedido-list.component.css']
})
export class PedidoListComponent extends AbstractComponent implements OnInit {

  text: string;

  pageNumber: number;

  pageSize: number;

  totalElements: number;

  page: Page<Pedido>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private pedidoService: PedidoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('PedidoListComponent.ngOnInit();');
    this.text = '';
    this.pageNumber = 1;
    this.pageSize = 10;

    const queryData = this.restoreQueryData('PedidoListComponent_QueryData');
    if (queryData) {
      this.text = queryData.text;
      this.pageNumber = queryData.pageNumber;
      this.pageSize = queryData.pageSize;
    }

    this.doList();
  }

  public list(): void {
    this.logger.debug('PedidoListComponent.list();');
    this.pageNumber = 1;
    this.doList();
  }

  public clear(): void {
    this.logger.debug('PedidoListComponent.clear();');
    if (this.text) {
      this.text = '';
      this.pageNumber = 1;
      this.doList();
    }
  }

  public onPageChange(num: number): void {
    this.logger.debug('New Page: ' + num);
    this.pageNumber = num;
    this.doList();
  }

  private doList(): void {
    this.storeQueryData('PedidoListComponent_QueryData', this.text, this.pageNumber, this.pageSize);
    const observable = this.pedidoService.list(this.text, 'all', this.pageNumber - 1, this.pageSize);
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
