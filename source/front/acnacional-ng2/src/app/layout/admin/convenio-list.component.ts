import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Page, Convenio } from '../../shared/models';
import { LocalStorageService, Logger, ConvenioService } from '../../shared/services';

@Component({
  selector: 'app-convenio-list',
  templateUrl: './convenio-list.component.html',
  styleUrls: ['./convenio-list.component.css']
})
export class ConvenioListComponent extends AbstractComponent implements OnInit {

  text: string;

  pageNumber: number;

  pageSize: number;

  totalElements: number;

  page: Page<Convenio>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private convenioService: ConvenioService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('ConvenioListComponent.ngOnInit();');
    this.text = '';
    this.pageNumber = 1;
    this.pageSize = 10;

    const queryData = this.restoreQueryData('ConvenioListComponent_QueryData');
    if (queryData) {
      this.text = queryData.text;
      this.pageNumber = queryData.pageNumber;
      this.pageSize = queryData.pageSize;
    }

    this.doList();
  }

  public list(): void {
    this.logger.debug('ConvenioListComponent.list();');
    this.pageNumber = 1;
    this.doList();
  }

  public clear(): void {
    this.logger.debug('ConvenioListComponent.clear();');
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
    this.storeQueryData('ConvenioListComponent_QueryData', this.text, this.pageNumber, this.pageSize);
    const observable = this.convenioService.list(this.text, this.pageNumber - 1, this.pageSize);
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
