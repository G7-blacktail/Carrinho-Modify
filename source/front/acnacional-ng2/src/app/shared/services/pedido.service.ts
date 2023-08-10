import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Page, Pedido } from '../models';

@Injectable()
export class PedidoService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/pedido';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<Pedido> {
    this.logger.debug(`PedidoService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get(getUrl);
  }

  public list(text: string, type: string, page: number, size: number = 10): Observable<Page<Pedido>> {
    this.logger.debug(`PedidoService.list(${text}, ${type}, ${page}, ${size});`);
    const listUrl = `${this.url}?q=${text}&type=${type}&p=${page}&s=${size}`;

    return this.httpClient.get(listUrl);
  }

  public save(pedido: Pedido): Observable<Pedido> {
    this.logger.debug(`PedidoService.save(${pedido};`);
    const postUrl = `${this.url}`;

    return this.httpClient.post(postUrl, pedido);
  }

}
