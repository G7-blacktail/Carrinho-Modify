import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { PrecadastroDialogComponent } from './precadastro-dialog.component';
import { Page, Pedido, Voucher } from '../../shared/models';
import { LocalStorageService, Logger, PedidoService, VoucherService } from '../../shared/services';

@Component({
  selector: 'app-pedido-view',
  templateUrl: './pedido-view.component.html',
  styleUrls: ['./pedido-view.component.css']
})
export class PedidoViewComponent extends AbstractComponent implements OnInit {

  item: Pedido;

  voucherList: Array<Voucher>;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private route: ActivatedRoute, private router: Router, private pedidoService: PedidoService,
    private voucherService: VoucherService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('PedidoViewComponent.ngOnInit();');
    this.route.params.subscribe((params: Params) => {
      if (params['id']) {
        const id: string = params['id'];
        this.show(id);
      }
    });
  }

  public onConfirmVoucher(obj: Voucher): void {
    this.logger.debug('AgendaViewComponent.onConfirmVoucher();');
    this.voucherService.done(obj).subscribe(
      value => {
        this.show(this.item.id);
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  private show(id: string): void {
    this.pedidoService.get(id).subscribe(
      item => {
        this.item = item;
        this.voucherList = item.voucherList;
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public isAgendavel(v: Voucher): boolean {
    if (v.produto.grupo.codigo === 'ECPF') {
      return true;
    }
    if ((v.produto.grupo.codigo === 'ECNPJ') || (v.produto.grupo.codigo === 'NFE')) {
      return true;
    }
    return false;
  }

  public onClickPreCadastro(v: Voucher): void {
    const ref = this.modalService.open(PrecadastroDialogComponent);
    const instance: PrecadastroDialogComponent = ref.componentInstance as PrecadastroDialogComponent;
    instance.configure(v);
  }

}
