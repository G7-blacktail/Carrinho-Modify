import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Usuario, Page } from '../models';

@Injectable()
export class UsuarioService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/usuario';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(id: string): Observable<Usuario> {
    this.logger.debug(`UsuarioService.get(${id});`);
    const getUrl = `${this.url}/${id}`;

    return this.httpClient.get(getUrl);
  }

  public list(text: string, page: number, size: number = 10): Observable<Page<Usuario>> {
    this.logger.debug(`UsuarioService.list(${text}, ${page}, ${size});`);
    const listUrl = `${this.url}?q=${text}&p=${page}&s=${size}`;

    return this.httpClient.get(listUrl);
  }

}
