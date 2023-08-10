import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Page, Agenda } from '../models';

@Injectable()
export class AgendaService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/posto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(idPosto: string, mes: number, ano: number): Observable<Agenda> {
    this.logger.debug(`AgendaService.get(${idPosto}, ${idPosto}, ${idPosto}, ${idPosto});`);
    const getUrl = `${this.url}/${idPosto}/agenda?mes=${mes}&ano=${ano}`;

    return this.httpClient.get(getUrl);
  }

}
