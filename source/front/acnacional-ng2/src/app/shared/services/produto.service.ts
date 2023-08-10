import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Page, Produto } from '../models';

@Injectable()
export class ProdutoService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/produto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<Produto> {
    this.logger.debug(`ProdutoService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get(getUrl);
  }

  public list(tipo: string, grupo: string, subGrupo: string, valid: boolean, text: string, page: number, size: number = 10): Observable<Page<Produto>> {
    this.logger.debug(`ProdutoService.list(${text}, ${page}, ${size});`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}?tipo_id=${tipo}&grupo_id=${grupo}&subgrupo_id=${subGrupo}&type=${type}&q=${text}&p=${page}&s=${size}`;

    return this.httpClient.get(listUrl);
  }

  public save(produto: Produto): Observable<Produto> {
    this.logger.debug(`ProdutoService.save(${produto};`);
    const postUrl = `${this.url}`;

    return this.httpClient.post(postUrl, produto);
  }

  public update(produto: Produto): Observable<Produto> {
    this.logger.debug(`ProdutoService.update(${produto};`);
    const postUrl = `${this.url}/${produto.id}`;

    return this.httpClient.put(postUrl, produto);
  }

}
