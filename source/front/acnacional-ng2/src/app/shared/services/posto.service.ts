import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Page, Posto } from '../models';

@Injectable()
export class PostoService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/posto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<Posto> {
    this.logger.debug(`PostoService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get(getUrl);
  }

  public list(idUf: string, idMunicipio: string, valid: boolean, text: string, page: number, size: number = 10): Observable<Page<Posto>> {
    this.logger.debug(`PostoService.list(${text}, ${page}, ${size});`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}?q=${text}&p=${page}&s=${size}&idUf=${idUf}&idMunicipio=${idMunicipio}&type=${type}`;

    return this.httpClient.get(listUrl);
  }

  public save(posto: Posto): Observable<Posto> {
    this.logger.debug(`PostoService.save(${posto};`);
    const postUrl = `${this.url}`;

    return this.httpClient.post(postUrl, posto);
  }

  public update(posto: Posto): Observable<Posto> {
    this.logger.debug(`PostoService.update(${posto};`);
    const postUrl = `${this.url}/${posto.id}`;

    return this.httpClient.put(postUrl, posto);
  }

}
