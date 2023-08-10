import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { HorarioPosto, Page } from '../models';

@Injectable()
export class HorarioPostoService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/posto';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(parentId: string, id: string): Observable<HorarioPosto> {
    this.logger.debug(`HorarioPostoService.get(${parentId}, ${id});`);
    const getUrl = `${this.url}/${parentId}/${id}`;

    return this.httpClient.get(getUrl);
  }


  public list(parentId: string, data: string, valid: boolean, page: number, size: number = 10): Observable<Page<HorarioPosto>> {
    this.logger.debug(`HorarioPostoService.list(${parentId}, ${page}, ${size});`);
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}/${parentId}/horario?&p=${page}&s=${size}&data=${data}&type=${type}`;

    return this.httpClient.get(listUrl);
  }

  public save(parentId: string, horarioPosto: HorarioPosto): Observable<HorarioPosto> {
    this.logger.debug(`HorarioPostoService.save(${parentId}, ${horarioPosto};`);
    const postUrl = `${this.url}/${parentId}/horario`;

    return this.httpClient.post(postUrl, horarioPosto);
  }

  public delete(parentId: string, id: string): Observable<any> {
    this.logger.debug(`HorarioPostoService.delete(${parentId}, ${id});`);
    const deleteUrl = `${this.url}/${parentId}/horario/${id}`;

    return this.httpClient.delete(deleteUrl);
  }


}
