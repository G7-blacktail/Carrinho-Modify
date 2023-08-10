import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Page, PreCadastro } from '../models';

@Injectable()
export class PreCadastroService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/pre-cadastro';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<PreCadastro> {
    this.logger.debug(`PreCadastroService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get<PreCadastro>(getUrl);
  }

  public list(idCliente: string, tipo: number, page: number, size: number = 10): Observable<Page<PreCadastro>> {
    this.logger.debug(`PreCadastroService.list(${idCliente}, ${page}, ${size});`);
    let listUrl = `${this.url}?p=${page}&s=${size}&idCliente=${idCliente}`;
    if (tipo) {
      listUrl += `&tipo=${tipo}`;
    }

    return this.httpClient.get(listUrl);
  }

  public save(preCadastro: PreCadastro): Observable<PreCadastro> {
    this.logger.debug(`PreCadastroService.save(${preCadastro};`);
    const postUrl = `${this.url}`;

    return this.httpClient.post<PreCadastro>(postUrl, preCadastro);
  }

  public update(preCadastro: PreCadastro): Observable<PreCadastro> {
    this.logger.debug(`PreCadastroService.update(${preCadastro};`);
    const postUrl = `${this.url}/${preCadastro.id}`;

    return this.httpClient.put<PreCadastro>(postUrl, preCadastro);
  }

}
