import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { UF, Page } from '../models';

@Injectable()
export class UfService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/uf';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<UF> {
    this.logger.debug(`UfService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get(getUrl);
  }

  public list(valid: boolean = false): Observable<Page<UF>> {
    this.logger.debug(`UfService.list();`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}?p=0&s=100&type=${type}`;

    return this.httpClient.get(listUrl);
  }

}
