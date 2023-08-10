import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Page, Pedido, Voucher } from '../../shared/models';
import { LocalStorageService, Logger, PedidoService, VoucherService } from '../../shared/services';

@Component({
  selector: 'app-pedido-publico-view',
  templateUrl: './pedido-view.component.html',
  styleUrls: ['./pedido-view.component.css']
})
export class PedidoViewComponent extends AbstractComponent implements OnInit {

  item: Pedido;

  items: Array<Voucher>;

  isNew = false;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private route: ActivatedRoute, private router: Router,
    private pedidoService: PedidoService, private voucherService: VoucherService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('PedidoViewComponent.ngOnInit();');
    this.route.queryParams.subscribe(
      params => {
        if (params['new']) {
          this.isNew = true;
        }
      }
    );

    this.route.params.subscribe((params: Params) => {
      if (params['id']) {
        const id: string = params['id'];
        this.pedidoService.get(id).subscribe(
          item => {
            this.item = item;
            if (this.item.situacao === 3) {
              this.voucherService.list(item.id, '', false, 0, 1000).subscribe(
                page => {
                  this.items = page.content;
                },
                response => this.onError(response),
                () => this.logger.debug('Complete')
              );
            }
          },
          response => this.onError(response),
          () => this.logger.debug('Complete')
        );
      }
    });
  }

  public onRestore(): void {
    this.store('pedido', this.item);
    this.router.navigate(['/publico']);
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

}
