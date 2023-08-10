import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Page, Voucher } from '../../shared/models';
import { LocalStorageService, Logger, SessionService, VoucherService } from '../../shared/services';

@Component({
  selector: 'app-voucher-publico-list',
  templateUrl: './voucher-list.component.html',
  styleUrls: ['./voucher-list.component.css']
})
export class VoucherListComponent extends AbstractComponent implements OnInit {

  text: string;

  pageNumber: number;

  pageSize: number;

  totalElements: number;

  page: Page<Voucher>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private voucherService: VoucherService, private sessionService: SessionService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    if (this.sessionService.isLoggedIn()) {
      this.doInitLoggedIn();
    } else {

    }
  }

  public isLoggedIn() {
    return this.sessionService.isLoggedIn();
  }

  private doInitLoggedIn() {
    this.logger.debug('VoucherListComponent.ngOnInit();');
    this.text = '';
    this.pageNumber = 1;
    this.pageSize = 100;

    const queryData = this.restoreQueryData('VoucherListComponent_QueryData');
    if (queryData) {
      try {
        const json = JSON.parse(queryData.text);
        this.text = json.text;
      } catch (e) {
        this.text = queryData.text;
      }
      this.pageNumber = queryData.pageNumber;
      this.pageSize = queryData.pageSize;
    }
    this.doList();
  }

  public login(): void {
    this.sessionService.login();
  }

  public list(): void {
    this.logger.debug('VoucherListComponent.list();');
    this.pageNumber = 1;
    this.doList();
  }

  public clear(): void {
    this.logger.debug('VoucherListComponent.clear();');
    if (this.text) {
      this.text = '';
      this.pageNumber = 1;
      this.doList();
    }
  }

  public onPageChange(event: any): void {
    this.logger.debug('New Page: ' + event);
    this.doList();
  }

  private doList(): void {
    const data = JSON.stringify({
      text: this.text
    });
    this.storeQueryData('VoucherListComponent_QueryData', data, this.pageNumber, this.pageSize);
    const observable = this.voucherService.list(null, this.text, true, this.pageNumber - 1, this.pageSize);
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

  public isAgendavel(v: Voucher): boolean {
    if (v.produto.grupo.codigo === 'ECPF') {
      return true;
    }
    if ((v.produto.grupo.codigo === 'ECNPJ') || (v.produto.grupo.codigo === 'NFE')) {
      return true;
    }
    return false;
  }

}
