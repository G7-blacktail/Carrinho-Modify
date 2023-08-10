import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Voucher } from '../../shared/models';
import { LocalStorageService, Logger, VoucherService } from '../../shared/services';

@Component({
  selector: 'app-agendamento-publico-done',
  templateUrl: './agendamento-done.component.html',
  styleUrls: ['./agendamento-done.component.css']
})
export class AgendamentoDoneComponent extends AbstractComponent implements OnInit {

  item: Voucher;

  back: string;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private fb: FormBuilder, private route: ActivatedRoute, private router: Router,
    private voucherService: VoucherService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('AgendamentoDoneComponent.ngOnInit();');

    this.back = this.restore('AgendamentoEdit_back');

    this.route.params.subscribe((params: Params) => {
      if (params['idVoucher']) {
        const idPedido: string = params['idPedido'];
        const idVoucher: string = params['idVoucher'];
        this.voucherService.get(idVoucher).subscribe(
          item => {
            this.item = item;
          },
          response => this.onError(response),
          () => this.logger.debug('Complete')
        );
      }
    });
  }

}
