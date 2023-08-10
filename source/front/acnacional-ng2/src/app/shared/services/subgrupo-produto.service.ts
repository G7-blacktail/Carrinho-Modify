import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { SubGrupoProduto, Page } from '../models';

@Injectable()
export class SubGrupoProdutoService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/subgrupo-produto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<SubGrupoProduto> {
    this.logger.debug(`SubGrupoProdutoService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get<SubGrupoProduto>(getUrl);
  }

  public list(tipo: string, grupo: string, valid: boolean, text: string, page: number, size: number = 10): Observable<Page<SubGrupoProduto>> {
    this.logger.debug(`SubGrupoProdutoService.list();`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}/?q=${text}&p=${page}&s=${size}&type=${type}&tipo_id=${tipo}&grupo_id=${grupo}`;

    return this.httpClient.get<Page<SubGrupoProduto>>(listUrl);
  }

  public save(subGrupo: SubGrupoProduto): Observable<SubGrupoProduto> {
    this.logger.debug(`GrupoProdutoService.save(${subGrupo};`);
    const postUrl = `${this.url}`;

    return this.httpClient.post<SubGrupoProduto>(postUrl, subGrupo);
  }

  public update(subGrupo: SubGrupoProduto): Observable<SubGrupoProduto> {
    this.logger.debug(`GrupoProdutoService.update(${subGrupo};`);
    const postUrl = `${this.url}/${subGrupo.id}`;

    return this.httpClient.put<SubGrupoProduto>(postUrl, subGrupo);
  }

}
