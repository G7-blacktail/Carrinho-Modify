import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { EnvironmentService } from './environment.service';
import { Logger } from './logger';

import { Page, Voucher } from '../models';

@Injectable()
export class VoucherService {

  private url;

  constructor(private environmentService: EnvironmentService, private httpClient: HttpClient, private logger: Logger) {
    this.url = this.environmentService.getResourcesUrl() + '/voucher';
    this.logger.debug(`URL:  ${this.url}`);
  }

  public get(idVoucher: string): Observable<Voucher> {
    this.logger.debug(`VoucherService.get(${idVoucher});`);
    const getUrl = `${this.url}/${idVoucher}`;

    return this.httpClient.get(getUrl);
  }

  public schedule(voucher: Voucher): Observable<Voucher> {
    this.logger.debug(`VoucherService.schedule(${voucher});`);
    const postUrl = `${this.url}/schedule`;

    return this.httpClient.post(postUrl, voucher);
  }

  public done(voucher: Voucher): Observable<Voucher> {
    this.logger.debug(`VoucherService.schedule(${voucher});`);
    const postUrl = `${this.url}/done`;

    return this.httpClient.post(postUrl, voucher);
  }

  public list(idPedido: string, text: string, valid: boolean, page: number, size: number = 10): Observable<Page<Voucher>> {
    this.logger.debug(`VoucherService.list(${idPedido}, ${text}, ${page}, ${size});`);
    const id = idPedido ? idPedido : '';
    const type = valid ? 'active' : 'all';
    const listUrl = `${this.url}/?q=${text}&p=${page}&s=${size}&idPedido=${id}&type=${type}`;

    return this.httpClient.get(listUrl);
  }

}
