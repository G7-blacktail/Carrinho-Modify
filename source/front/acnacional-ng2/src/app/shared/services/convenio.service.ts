import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Convenio, Page } from '../models';

@Injectable()
export class ConvenioService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/convenio';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<Convenio> {
    this.logger.debug(`ConvenioService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get(getUrl);
  }

  public list(text: string, page: number, size: number = 10): Observable<Page<Convenio>> {
    this.logger.debug(`ConvenioService.list(${text}, ${page}, ${size});`);
    const listUrl = `${this.url}?q=${text}&p=${page}&s=${size}`;

    return this.httpClient.get(listUrl);
  }

  public save(convenio: Convenio): Observable<Convenio> {
    this.logger.debug(`ConvenioService.save(${convenio});`);
    const postUrl = `${this.url}`;

    return this.httpClient.post(postUrl, convenio);
  }

  public update(convenio: Convenio): Observable<Convenio> {
    this.logger.debug(`ConvenioService.done(${convenio});`);
    const postUrl = `${this.url}/${convenio.id}`;

    return this.httpClient.put(postUrl, convenio);
  }

}
