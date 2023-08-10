import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Horario, Page } from '../models';

@Injectable()
export class HorarioService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/posto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public list(parentId: string, page: number, size: number = 10): Observable<Page<Horario>> {
    this.logger.debug(`HorarioService.list(${parentId}, ${page}, ${size});`);
    const listUrl = `${this.url}/${parentId}/horario?&p=${page}&s=${size}`;

    return this.httpClient.get(listUrl);
  }

}
