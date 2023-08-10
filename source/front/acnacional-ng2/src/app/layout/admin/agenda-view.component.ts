import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbCalendar, NgbDateStruct, NgbModal, NgbDate } from '@ng-bootstrap/ng-bootstrap';

import { AbstractComponent } from '../../shared/components';
import { Page, Agenda, Voucher } from '../../shared/models';
import { LocalStorageService, Logger, AgendaService, HorarioPostoService, VoucherService } from '../../shared/services';

import { AgendaDialogComponent } from './agenda-dialog.component';

@Component({
  selector: 'app-agenda-view',
  templateUrl: './agenda-view.component.html',
  styleUrls: ['./agenda-view.component.css']
})
export class AgendaViewComponent extends AbstractComponent implements OnInit {

  id: string;

  item: Agenda;

  current: NgbDateStruct;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private route: ActivatedRoute, private router: Router, private calendar: NgbCalendar,
    private agendaService: AgendaService, private horarioPostoService: HorarioPostoService,
    private voucherService: VoucherService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('AgendaViewComponent.ngOnInit();');
    this.current = this.calendar.getToday();

    this.route.params.subscribe((params: Params) => {
      if (params['id']) {
        this.id = params['id'];
        this.show();
      }
    });
  }

  public onSelectData(data: string): void {
    this.logger.debug('AgendaViewComponent.onSelectData();');
    const ref = this.modalService.open(AgendaDialogComponent);
    const instance: AgendaDialogComponent = ref.componentInstance as AgendaDialogComponent;

    instance.configure(this.item.posto, data).subscribe(
      status => {
        this.show();
        ref.close();
        if (!status) {
          this.onError('Erro ao adicionar o evento');
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onDelete(id: string): void {
    this.logger.debug('AgendaViewComponent.onDelete();');
    this.horarioPostoService.delete(this.item.posto.id, id).subscribe(
      value => {
        this.show();
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onConfirm(obj: Voucher): void {
    this.logger.debug('AgendaViewComponent.onConfirm();');
    this.voucherService.done(obj).subscribe(
      value => {
        this.show();
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public toPrevious(): void {
    const date = NgbDate.from(this.current);
    this.current = this.calendar.getPrev(date, 'm', 1);
    this.show();
  }

  public toNext(): void {
    const date = NgbDate.from(this.current);
    this.current = this.calendar.getNext(date, 'm', 1);
    this.show();
  }

  private show(): void {
    const month = this.current.month;
    const year = this.current.year;
    this.agendaService.get(this.id, month, year).subscribe(
      item => {
        this.item = item;
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

}
