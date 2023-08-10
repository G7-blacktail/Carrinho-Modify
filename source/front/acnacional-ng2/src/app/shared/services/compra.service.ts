import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Convenio, Pedido, PedidoProduto } from '../models';

@Injectable()
export class CompraService {

  private url: string;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/compra';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public begin(): Observable<Pedido> {
    this.logger.debug(`CompraService.begin();`);
    const postUrl = `${this.url}/begin`;

    return this.httpClient.post(postUrl, null);
  }

  public get(id: string): Observable<Pedido> {
    this.logger.debug(`CompraService.get();`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get(getUrl);
  }

  public add(parentId: string, item: PedidoProduto): Observable<Pedido> {
    this.logger.debug(`CompraService.add(${parentId}, ${item};`);
    const postUrl = `${this.url}/${parentId}/add`;

    return this.httpClient.post(postUrl, item);
  }

  public addSimple(parentId: string, item: PedidoProduto): Observable<PedidoProduto> {
    this.logger.debug(`CompraService.add(${parentId}, ${item};`);
    const postUrl = `${this.url}/${parentId}/add-simple`;

    return this.httpClient.post(postUrl, item);
  }

  public addConvenio(id: string, item: Convenio): Observable<Pedido> {
    this.logger.debug(`CompraService.addConvenio(${id}, ${item};`);
    const postUrl = `${this.url}/${id}/add-convenio`;

    return this.httpClient.post(postUrl, item);
  }

  public update(parentId: string, item: PedidoProduto): Observable<Pedido> {
    this.logger.debug(`CompraService.update(${parentId}, ${item};`);
    const postUrl = `${this.url}/${parentId}/update`;

    return this.httpClient.put(postUrl, item);
  }

  public updateSimple(parentId: string, item: PedidoProduto): Observable<PedidoProduto> {
    this.logger.debug(`CompraService.update(${parentId}, ${item};`);
    const postUrl = `${this.url}/${parentId}/update-simple`;

    return this.httpClient.put(postUrl, item);
  }

  public remove(parentId: string, id: string): Observable<Pedido> {
    this.logger.debug(`CompraService.remove(${parentId}, ${id};`);
    const deleteUrl = `${this.url}/${parentId}/remove/${id}`;

    return this.httpClient.delete(deleteUrl);
  }

  public removeConvenio(id: string): Observable<Pedido> {
    this.logger.debug(`CompraService.removeConvenio(${id}};`);
    const deleteUrl = `${this.url}/${id}/remove-convenio`;

    return this.httpClient.delete(deleteUrl);
  }

  public end(item: Pedido): Observable<Pedido> {
    this.logger.debug(`CompraService.end(${item});`);
    const postUrl = `${this.url}/end`;

    return this.httpClient.post(postUrl, item);
  }

}
