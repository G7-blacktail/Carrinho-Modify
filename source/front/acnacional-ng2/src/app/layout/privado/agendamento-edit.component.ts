import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { NgbCalendar, NgbDateParserFormatter, NgbDatepickerI18n, NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { CustomDateParserFormatter, CustomDatepickerI18n } from '../../shared/configuration';

import { AbstractComponent } from '../../shared/components';
import { HorarioPosto, Municipio, Posto, UF, Voucher } from '../../shared/models';
import { LocalStorageService, Logger, HorarioPostoService, MunicipioService,
         PostoService, PreCadastroService, UfService, VoucherService } from '../../shared/services';

@Component({
  selector: 'app-agendamento-publico-edit',
  templateUrl: './agendamento-edit.component.html',
  styleUrls: ['./agendamento-edit.component.css'],
  providers: [
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter},
    {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n}
  ]
})
export class AgendamentoEditComponent extends AbstractComponent implements OnInit {

  item: Voucher;

  uf: string;

  ufList: Array<UF>;

  municipio: string;

  municipioList: Array<Municipio>;

  posto: string;

  postoList: Array<Posto>;

  data: string;

  horario: string;

  horarioList: Array<HorarioPosto>;

  horarioLoading = false;

  dataModel: NgbDateStruct;

  minDate: NgbDateStruct;

  maxDate: NgbDateStruct;

  back: string;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private fb: FormBuilder, private route: ActivatedRoute, private router: Router,
    private ufService: UfService, private municipioService: MunicipioService, private voucherService: VoucherService,
    private horarioPostoService: HorarioPostoService, private postoService: PostoService, private preCadastroService: PreCadastroService,
    private calendar: NgbCalendar, private dateParserFormatter: NgbDateParserFormatter) {
    super(logger, localStorageService, modalService);
    // this.dataModel = calendar.getToday();
    this.minDate = calendar.getToday();
    this.maxDate = calendar.getNext(calendar.getToday(), 'd', 30);
  }

  public ngOnInit() {
    this.logger.debug('AgendamentoEditComponent.ngOnInit();');

    this.route.queryParams.subscribe(
      params => {
        if (params['back']) {
          this.back = params['back'];
          this.store('AgendamentoEdit_back', this.back);
        } else {
          this.back = this.restore('AgendamentoEdit_back');
        }
      }
    );

    this.route.params.subscribe((params: Params) => {
      if (params['idVoucher']) {
        const idPedido: string = params['idPedido'];
        const idVoucher: string = params['idVoucher'];
        this.voucherService.get(idVoucher).subscribe(
          item => {
            this.item = item;
            this.onInitItem();
          },
          response => this.onError(response),
          () => this.logger.debug('Complete')
        );
      }
    });
  }

  private onInitItem() {
    if (this.item.horarioPosto) {
      this.uf = this.item.horarioPosto.posto.uf.id;
      this.municipio = this.item.horarioPosto.posto.municipio.id;
      this.posto = this.item.horarioPosto.posto.id;

      this.data = this.item.horarioPosto.data;
      this.horario = this.item.horarioPosto.id;

      this.dataModel = this.dateParserFormatter.parse(this.data);
      this.onDateChange(this.dataModel);
    } else {
      this.uf = this.item.pedido.posto.uf.id;
      this.municipio = this.item.pedido.posto.municipio.id;
      this.posto = this.item.pedido.posto.id;
    }
    this.onInitUf();
  }

  private onInitUf() {
    this.ufService.list(true).subscribe(
      page => {
        this.ufList = page.content;
        if (!this.uf) {
          this.uf = this.ufList[0].id;
        }
        this.onChangeUf(this.uf);
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onChangeUf(uf: string): void {
    this.logger.debug(`UF selecionada: ${uf}`);
    this.municipioService.list(uf, '', 0, 10000, true).subscribe(
      page => {
        this.municipioList = page.content;
        if ((!this.municipio) && (this.municipioList) && (this.municipioList.length > 0)) {
          this.municipio = this.municipioList[0].id;
        }
        this.onChangeMunicipio(this.municipio);
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onChangeMunicipio(municipio: string): void {
    this.logger.debug(`Municipio selecionado: ${municipio}`);
    this.postoService.list(this.uf, municipio, true, '', 0, 10000).subscribe(
      page => {
        this.postoList = page.content;
        if ((!this.posto) && (this.postoList) && (this.postoList.length > 0)) {
          this.posto = this.postoList[0].id;
        }
        this.onChangePosto(this.posto);
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onChangePosto(posto: string): void {
    this.logger.debug(`Posto selecionado: ${posto}`);
    this.posto = posto;
    if (this.data) {
      this.doChangeData();
    }
  }

  public onDateChange(date: NgbDateStruct) {
    this.logger.debug(`Data selecionada: ${date}`);
    this.data = this.dateParserFormatter.format(date);
    this.doChangeData();
  }

  private doChangeData() {
    this.logger.debug(`Data selecionada: ${this.data}`);
    // this.horario = null;
    if (this.data) {
      this.horarioList = null;
      this.horarioLoading = true;
      this.horarioPostoService.list(this.posto, this.data, true, 0, 1000).subscribe(
        page => {
          this.horarioList = page.content;
          this.horarioLoading = false;
          // Caso ja tenha escolhido uma data
          if ((!this.horario) && (this.item.horarioPosto)) {
            this.horario = this.item.horarioPosto.id;
          }
          if (this.horario) {
            let found = false;
            this.horarioList.forEach((v) => {
              if (v.id === this.horario) {
                found = true;
              }
            });
            if (!found) {
              this.horario = null;
            }
          }
        },
        response => {
          this.horarioLoading = true;
          this.onError(response);
        },
        () => this.logger.debug('Complete')
      );
    }
  }

  public isDisabled(date: NgbDateStruct, current: {month: number}): boolean {
    const d = new Date(date.year, date.month - 1, date.day);
    return d.getDay() === 0 || d.getDay() === 6;
  }

  public onSelectHorario(h: HorarioPosto): void {
    this.horario = h.id;
  }

  public onSubmit(): void {
    const obj: Voucher = {
      id: this.item.id,
      horarioPosto: {id: this.horario}
    };

    this.voucherService.schedule(obj).subscribe(
      item => {
        this.item = item;
        this.toPreCadastro();
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onReset(): void {
    if (this.item.horarioPosto) {
      this.horario = this.item.horarioPosto.id;
    } else {
      this.horario = null;
    }
  }

  public toPreCadastro(): void {
    let url = '';
    if (this.item.produto.grupo.codigo === 'ECPF') {
      url = '/privado/pre-cadastro-pf';
    } else if ((this.item.produto.grupo.codigo === 'ECNPJ') || (this.item.produto.grupo.codigo === 'NFE')) {
      url = '/privado/pre-cadastro-pj';
    }
    if (url !== '') {
      if (this.item.preCadastro) {
        url += '/edit/' + this.item.preCadastro.id;
      } else {
        url += '/new';
      }
      const urlReturn = `/privado/pedido/${this.item.pedido.id}/voucher/${this.item.id}/agendamento-edit?back=${this.back}`;
      this.router.navigate([url], { queryParams: { 'idPedido': this.item.pedido.id, 'idVoucher' : this.item.id, 'return':  urlReturn} });
    }
  }

}
