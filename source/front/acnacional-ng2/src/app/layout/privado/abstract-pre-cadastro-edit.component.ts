import { Directive, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';
import { URLUtils } from 'src/app/shared/utils';

import { AbstractComponent } from '../../shared/components';
import { Municipio, PreCadastro, UF, Voucher } from '../../shared/models';
import { LocalStorageService, Logger, MunicipioService, PreCadastroService, UfService, VoucherService } from '../../shared/services';

@Directive()
export abstract class AbstractPreCadastroEditComponent extends AbstractComponent implements OnInit {

  id: string;

  voucher: Voucher;

  form: FormGroup;

  ufList: Array<UF>;

  municipioList: Array<Municipio>;

  dateMask: Array<string | RegExp>;

  returnUrl: string;

  returnUrlParams: { [k: string]: string } = {};

  readonly formErrors = {
    'id': '',
    'tipo': '',
    'nomeResp': '',
    'dataNascimentoResp': '',
    'cpfResp': '',
    'emailResp': '',
    'rgResp': '',
    'orgaoEmissorRgResp': '',
    'ufOrgaoEmissorRgResp': '',
    'pisPasepResp': '',
    'cepResp': '',
    'logradouroResp': '',
    'numeroEnderecoResp': '',
    'complementoEnderecoResp': '',
    'bairroResp': '',
    'municipioResp': '',
    'ufResp': '',
    'telefonePrincipal': '',
    'telefoneAlternativo': '',
    'tituloEleitor': '',
    'zonaTituloEleitor': '',
    'secaoTituloEleitor': '',
    'municipioTituloEleitor': '',
    'ufTituloEleitor': '',
    'inssCeiPf': '',
    'nomeEmpresarial': '',
    'cnpj': '',
    'inssCeiPj': '',
    'municipioEmpresa': '',
    'ufEmpresa': '',
    'nomeRepresentanteLegal': '',
    'cpfRepresentanteLegal': '',
    'dataNascimentoRepresentanteLegal': ''
  };

  readonly validationMessages = {
    'id': {},
    'tipo': {},
    'nomeResp': {
      'required':      'O nome deve ser informado',
    },
    'dataNascimentoResp': {},
    'cpfResp': {},
    'emailResp': {
      'required':      'O e-mail deve ser informado',
    },
    'rgResp': {
      'required':      'O RG deve ser informado',
    },
    'orgaoEmissorRgResp': {},
    'ufOrgaoEmissorRgResp': {},
    'pisPasepResp': {},
    'cepResp': {},
    'logradouroResp': {},
    'numeroEnderecoResp': {},
    'complementoEnderecoResp': {},
    'bairroResp': {},
    'municipioResp': {},
    'ufResp': {},
    'telefonePrincipal':  {
      'required':      'O telefone deve ser informado',
    },
    'telefoneAlternativo': {},
    'tituloEleitor': {},
    'zonaTituloEleitor': {},
    'secaoTituloEleitor': {},
    'municipioTituloEleitor': {},
    'ufTituloEleitor': {},
    'inssCeiPf': {},
    'nomeEmpresarial': {},
    'cnpj': {},
    'inssCeiPj': {},
    'municipioEmpresa': {},
    'ufEmpresa': {},
    'nomeRepresentanteLegal': {},
    'cpfRepresentanteLegal': {},
    'dataNascimentoRepresentanteLegal': {}
  };

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    protected fb: FormBuilder, protected route: ActivatedRoute, protected router: Router,
    protected preCadastroService: PreCadastroService, protected ufService: UfService, protected municipioService: MunicipioService,
    protected voucherService: VoucherService, protected tipo: number) {
    super(logger, localStorageService, modalService);
    this.dateMask = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  }

  public ngOnInit() {
    this.logger.debug('PreCadastroEditComponent.ngOnInit();');

    this.ufService.list().subscribe(
      page => {
        this.ufList = page.content;
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );

    this.route.queryParams.subscribe(
      params => {
        if (params['return']) {
          this.returnUrl = URLUtils.getUrl(params['return']);
          this.returnUrlParams = URLUtils.getParams(params['return']);
        } else {
          this.returnUrl = '/privado/pre-cadastro';
        }

        if (params['idVoucher']) {
          this.voucherService.get(params['idVoucher']).subscribe(
            v => {
              this.voucher = v;
            },
            response => this.onError(response),
            () => this.logger.debug('Complete')
          );
        } else {
          this.voucher = null;
        }
      }
    );

    this.route.params.subscribe((params: Params) => {
      if (params['id']) {
        const id: string = params['id'];
        this.preCadastroService.get(id).subscribe(
          item => {
            this.id = id;
            this.buildForm();
            this.setFormValue(this.form, this.formErrors, item);
          },
          response => this.onError(response),
          () => this.logger.debug('Complete')
        );
      } else {
        this.buildForm();
        this.form.controls['tipo'].patchValue(this.tipo);
      }
    });
  }

  private buildForm(): void {
    this.form = this.fb.group({
      'id': [''],
      'tipo': ['', [ Validators.maxLength(1) ]],
      'nomeResp': ['', [ Validators.required, Validators.maxLength(100) ]],
      'dataNascimentoResp': ['', [ Validators.maxLength(10) ]],
      'cpfResp': ['', [ Validators.maxLength(20) ]],
      'emailResp': ['', [ Validators.required, Validators.maxLength(100) ]],
      'rgResp': ['', [ Validators.maxLength(20) ]],
      'orgaoEmissorRgResp': ['', [ Validators.maxLength(20) ]],
      'ufOrgaoEmissorRgResp': ['', [ Validators.maxLength(2) ]],
      'pisPasepResp': ['', [ Validators.maxLength(20) ]],
      'cepResp': ['', [ Validators.maxLength(10) ]],
      'logradouroResp': ['', [ Validators.maxLength(100) ]],
      'numeroEnderecoResp': ['', [ Validators.maxLength(20) ]],
      'complementoEnderecoResp': ['', [ Validators.maxLength(100) ]],
      'bairroResp': ['', [ Validators.maxLength(100) ]],
      'municipioResp': ['', [ Validators.maxLength(100) ]],
      'ufResp': ['', [ Validators.maxLength(2) ]],
      'telefonePrincipal': ['', [ Validators.required, Validators.maxLength(20) ]],
      'telefoneAlternativo': ['', [ Validators.maxLength(20) ]],
      'tituloEleitor': ['', [ Validators.maxLength(100) ]],
      'zonaTituloEleitor': ['', [ Validators.maxLength(20) ]],
      'secaoTituloEleitor': ['', [ Validators.maxLength(20) ]],
      'municipioTituloEleitor': ['', [ Validators.maxLength(100) ]],
      'ufTituloEleitor': ['', [ Validators.maxLength(2) ]],
      'inssCeiPf': ['', [ Validators.maxLength(100) ]],
      'nomeEmpresarial': ['', [ Validators.maxLength(100) ]],
      'cnpj': ['', [ Validators.maxLength(20) ]],
      'inssCeiPj': ['', [ Validators.maxLength(100) ]],
      'municipioEmpresa': ['', [ Validators.maxLength(100) ]],
      'ufEmpresa': ['', [ Validators.maxLength(2) ]],
      'nomeRepresentanteLegal': ['', [ Validators.maxLength(100) ]],
      'cpfRepresentanteLegal': ['', [ Validators.maxLength(20) ]],
      'dataNascimentoRepresentanteLegal': ['', [ Validators.maxLength(10) ]]
    });

    this.form.valueChanges.subscribe(data => this.onValueChanged(data));

    this.onValueChanged();
  }

  public onValueChanged(data?: any): void {
    if (!this.form) {
      return;
    }

    super.onValueChanged(this.form, this.formErrors, this.validationMessages);
  }

  public onChangeUf(uf: string): void {
    this.logger.debug(`UF selecionada: ${uf}`);
    this.municipioService.list(uf, '', 0, 10000).subscribe(
      page => {
        this.municipioList = page.content;
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onSubmit(): void {
    this.logger.debug('PreCadastroEditComponent.onSubmit();');
    const preCadastro: PreCadastro = this.form.value;
    preCadastro.idVoucher = this.voucher.id;
    this.logger.debug(JSON.stringify(preCadastro));
    let observable: Observable<PreCadastro>;
    if (this.id) {
      observable = this.preCadastroService.update(preCadastro);
    } else {
      observable = this.preCadastroService.save(preCadastro);
    }
    observable.subscribe(
      item => {
        this.router.navigate(['/privado/pedido', this.voucher.pedido.id, 'voucher', this.voucher.id, 'agendamento-done']);
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

}
