import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { AbstractComponent } from '../../shared/components';
import { Municipio, Posto, Page, UF } from '../../shared/models';
import { LocalStorageService, Logger, MunicipioService, PostoService, UfService } from '../../shared/services';

@Component({
  selector: 'app-posto-edit',
  templateUrl: './posto-edit.component.html',
  styleUrls: ['./posto-edit.component.css']
})
export class PostoEditComponent extends AbstractComponent implements OnInit {

  id: string;

  form: FormGroup;

  ufList: Array<UF>;

  municipioList: Array<Municipio>;

  readonly formErrors = {
    'codigo': '',
    'nome': '',
    'responsavel': '',
    'email': '',
    'endereco': '',
    'complemento': '',
    'numero': '',
    'bairro': '',
    'cep': '',
    'uf.id': '',
    'municipio.id': '',
    'telefone': '',
    'mapa': '',
    'ativo': ''
  };

  readonly validationMessages = {
    'codigo': {
      'required':      'O código deve ser informado',
      'minlength':     'O código deve ter pelo menos 3 caracteres',
      'maxlength':     'O código não pode ter mais de 20 caracteres'
    },
    'nome': {
      'required':      'O nome deve ser informado',
      'minlength':     'O nome deve ter pelo menos 3 caracteres',
      'maxlength':     'O nome não pode ter mais de 100 caracteres'
    },
    'responsavel': {
      'required':      'O responsável deve ser informado',
      'minlength':     'O responsável deve ter pelo menos 3 caracteres',
      'maxlength':     'O responsável não pode ter mais de 200 caracteres'
    },
    'email': {
      'required':      'O e-mail deve ser informado',
      'minlength':     'O e-mail deve ter pelo menos 3 caracteres',
      'maxlength':     'O e-mail não pode ter mais de 200 caracteres'
    },
    'endereco': {
      'required':      'O endereço deve ser informado',
      'minlength':     'O endereço deve ter pelo menos 3 caracteres',
      'maxlength':     'O endereço não pode ter mais de 200 caracteres'
    },
    'complemento': {
      'maxlength':     'O complemento não pode ter mais de 200 caracteres'
    },
    'numero': {
      'maxlength':     'O numero não pode ter mais de 100 caracteres'
    },
    'bairro': {
      'maxlength':     'O bairro não pode ter mais de 100 caracteres'
    },
    'cep': {
      'minlength':     'O CEP deve ter pelo menos 8 caracteres',
      'maxlength':     'O CEP não pode ter mais de 8 caracteres'
    },
    'uf.id': {
      'required':      'A UF deve ser selecionada',
      'minlength':     'A UF deve ter pelo menos 1 caracteres',
      'maxlength':     'A UF não pode ter mais de 100 caracteres'
    },
    'municipio.id': {
      'required':      'O município deve ser selecionado',
      'minlength':     'O município deve ter pelo menos 1 caracteres',
      'maxlength':     'O município não pode ter mais de 100 caracteres'
    },
    'telefone': {
      'required':      'O telefone deve ser informado',
      'minlength':     'O telefone deve ter pelo menos 8 caracteres',
      'maxlength':     'O telefone não pode ter mais de 100 caracteres'
    },
    'mapa': {
      'required':      'O mapa deve ser informado',
      'maxlength':     'O mapa não pode ter mais de 1000 caracteres'
    }
  };

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private fb: FormBuilder, private route: ActivatedRoute, private router: Router,
    private postoService: PostoService, private ufService: UfService, private municipioService: MunicipioService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('PostoEditComponent.ngOnInit();');

    this.ufService.list().subscribe(
      page => {
        this.ufList = page.content;
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );

    this.route.params.subscribe((params: Params) => {
      if (params['id']) {
        const id: string = params['id'];
        this.postoService.get(id).subscribe(
          item => {
            this.id = id;
            this.onChangeUf(item.uf.id);
            this.buildForm();
            this.setFormValue(this.form, this.formErrors, item);
          },
          response => this.onError(response),
          () => this.logger.debug('Complete')
        );
      } else {
        this.buildForm();
      }
    });
  }

  private buildForm(): void {
    this.form = this.fb.group({
      'id': [''],
      'ativo': [true],
      'codigo': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20)
        ]
      ],
      'nome': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ],
      'responsavel': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(200)
        ]
      ],
      'email': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(200)
        ]
      ],
      'endereco': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(200)
        ]
      ],
      'complemento': ['', [
          Validators.maxLength(100)
        ]
      ],
      'numero': ['', [
          Validators.maxLength(100)
        ]
      ],
      'bairro': ['', [
          Validators.maxLength(100)
        ]
      ],
      'cep': ['', [
          Validators.minLength(8),
          Validators.maxLength(8)
        ]
      ],
      'uf': this.fb.group({
        'id': ['', [
            Validators.required,
            Validators.minLength(1),
            Validators.maxLength(100)
          ]
        ]
      }),
      'municipio': this.fb.group({
        'id': ['', [
            Validators.required,
            Validators.minLength(1),
            Validators.maxLength(100)
          ]
        ]
      }),
      'telefone': ['', [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(100)
        ]
      ],
      'mapa': ['', [
          Validators.maxLength(1000)
        ]
      ]
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
    this.logger.debug('PostoEditComponent.onSubmit();');
    const posto: Posto = this.form.value;
    this.logger.debug(JSON.stringify(posto));
    let observable: Observable<Posto>;
    if (this.id) {
      observable = this.postoService.update(posto);
    } else {
      observable = this.postoService.save(posto);
    }
    observable.subscribe(
      item => {
        this.onSuccess(item);
        this.id = item.id;
        this.setFormValue(this.form, this.formErrors, item);
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

}
