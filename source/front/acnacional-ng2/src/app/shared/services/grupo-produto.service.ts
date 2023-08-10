import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { GrupoProduto, Page } from '../models';

@Injectable()
export class GrupoProdutoService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/grupo-produto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<GrupoProduto> {
    this.logger.debug(`GrupoProdutoService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get<GrupoProduto>(getUrl);
  }

  public list(tipo: string, valid: boolean, text: string, page: number, size: number = 10): Observable<Page<GrupoProduto>> {
    this.logger.debug(`GrupoProdutoService.list();`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}/?q=${text}&p=${page}&s=${size}&type=${type}&tipo_id=${tipo}`;

    return this.httpClient.get(listUrl);
  }

  public save(grupo: GrupoProduto): Observable<GrupoProduto> {
    this.logger.debug(`GrupoProdutoService.save(${grupo};`);
    const postUrl = `${this.url}`;

    return this.httpClient.post<GrupoProduto>(postUrl, grupo);
  }

  public update(grupo: GrupoProduto): Observable<GrupoProduto> {
    this.logger.debug(`GrupoProdutoService.update(${grupo};`);
    const postUrl = `${this.url}/${grupo.id}`;

    return this.httpClient.put<GrupoProduto>(postUrl, grupo);
  }

}
