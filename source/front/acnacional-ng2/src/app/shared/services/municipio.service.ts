import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Municipio, Page } from '../models';

@Injectable()
export class MunicipioService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/uf';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(parentId: string, id: string): Observable<Municipio> {
    this.logger.debug(`MunicipioService.get(${parentId}, ${id});`);
    const getUrl = `${this.url}/${parentId}/municipio/${id}`;

    return this.httpClient.get(getUrl);
  }

  public list(parentId: string, text: string, page: number, size: number = 10, valid: boolean = false): Observable<Page<Municipio>> {
    this.logger.debug(`MunicipioService.list(${parentId}, ${text}, ${page}, ${size});`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}/${parentId}/municipio?q=${text}&p=${page}&s=${size}&type=${type}`;

    return this.httpClient.get(listUrl);
  }

}
