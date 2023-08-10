import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { TipoProduto, Page } from '../models';

@Injectable()
export class TipoProdutoService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/tipo-produto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<TipoProduto> {
    this.logger.debug(`TipoProdutoService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get<TipoProduto>(getUrl);
  }

  public list(valid: boolean, text: string, page: number, size: number = 10): Observable<Page<TipoProduto>> {
    this.logger.debug(`TipoProdutoService.list();`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}?q=${text}&p=${page}&s=${size}&type=${type}`;

    return this.httpClient.get<Page<TipoProduto>>(listUrl);
  }

}
