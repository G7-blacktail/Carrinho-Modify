import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Posto, Page } from '../../shared/models';
import { LocalStorageService, Logger, PostoService } from '../../shared/services';

@Component({
  selector: 'app-posto-list',
  templateUrl: './posto-list.component.html',
  styleUrls: ['./posto-list.component.css']
})
export class PostoListComponent extends AbstractComponent implements OnInit {

  text: string;

  pageNumber: number;

  pageSize: number;

  totalElements: number;

  page: Page<Posto>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private postoService: PostoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('PostoListComponent.ngOnInit();');
    this.text = '';
    this.pageNumber = 1;
    this.pageSize = 10;

    const queryData = this.restoreQueryData('PostoListComponent_QueryData');
    if (queryData) {
      this.text = queryData.text;
      this.pageNumber = queryData.pageNumber;
      this.pageSize = queryData.pageSize;
    }

    this.doList();
  }

  public list(): void {
    this.logger.debug('PostoListComponent.list();');
    this.pageNumber = 1;
    this.doList();
  }

  public clear(): void {
    this.logger.debug('PostoListComponent.clear();');
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
    this.storeQueryData('PostoListComponent_QueryData', this.text, this.pageNumber, this.pageSize);
    const observable = this.postoService.list('', '', false, this.text, this.pageNumber - 1, this.pageSize);
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
