import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { AbstractComponent } from '../../shared/components';
import { GrupoProduto, Page, TipoProduto } from '../../shared/models';
import { LocalStorageService, Logger, GrupoProdutoService, TipoProdutoService } from '../../shared/services';

@Component({
  selector: 'app-grupo-produto-edit',
  templateUrl: './grupo-produto-edit.component.html',
  styleUrls: ['./grupo-produto-edit.component.css']
})
export class GrupoProdutoEditComponent extends AbstractComponent implements OnInit {

  id: string;

  form: FormGroup;

  tipoList: Array<TipoProduto>;

  readonly formErrors = {
    'codigo': '',
    'ordem': '',
    'nome': '',
    'descricao': '',
    'tipo.id': '',
    'imagem': '',
    'ativo': ''
  };

  readonly validationMessages = {
    'codigo': {
      'required':      'O código deve ser informado',
      'minlength':     'O código deve ter pelo menos 2 caracteres',
      'maxlength':     'O código não pode ter mais de 20 caracteres'
    },
    'ordem': {
      'required':      'A ordenação deve ser informada',
      'minlength':     'A ordenação deve ter pelo menos 1 caracter',
      'maxlength':     'A ordenação não pode ter mais de 3 caracteres'
    },
    'nome': {
      'required':      'O nome deve ser informado',
      'minlength':     'O nome deve ter pelo menos 3 caracteres',
      'maxlength':     'O nome não pode ter mais de 100 caracteres'
    },
    'descricao': {
      'maxlength':     'A descrição não pode ter mais de 4000 caracteres'
    },
    'tipo.id': {
      'required':      'O tipo deve ser selecionada',
      'minlength':     'O tipo deve ter pelo menos 1 caracteres',
      'maxlength':     'O tipo não pode ter mais de 100 caracteres'
    },
    'imagem': {
      'maxlength':     'A imagem não pode ter mais de 4000 caracteres'
    }
  };

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private fb: FormBuilder, private route: ActivatedRoute, private router: Router,
    private tipoProdutoService: TipoProdutoService, private grupoProdutoService: GrupoProdutoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('GrupoProdutoEditComponent.ngOnInit();');

    this.tipoProdutoService.list(false, '', 0, 1000).subscribe(
      page => {
        this.tipoList = page.content;
        this.route.params.subscribe(
          (params: Params) => {
            if (params['id']) {
              const id: string = params['id'];
              this.grupoProdutoService.get(id).subscribe(
                item => {
                  this.id = id;
                  this.buildForm();
                  this.setFormValue(this.form, this.formErrors, item);
                  this.onChangeTipo();
                },
                response => this.onError(response),
                () => this.logger.debug('Complete')
              );
            } else {
              this.buildForm();
              if (this.tipoList.length > 0) {
                (this.form.controls['tipo'] as FormGroup).controls['id'].patchValue(this.tipoList[0].id);
                this.onChangeTipo();
              }
            }
          }
        );
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  private buildForm(): void {
    this.form = this.fb.group({
      'id': [''],
      'ativo': [true],
      'codigo': ['', [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20)
        ]
      ],
      'ordem': ['', [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(3)
        ]
      ],
      'nome': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ],
      'descricao': ['', [
          Validators.maxLength(4000)
        ]
      ],
      'tipo': this.fb.group({
        'id': ['', [
            Validators.required,
            Validators.minLength(1),
            Validators.maxLength(100)
          ]
        ]
      }),
      'imagem': ['', [
          Validators.maxLength(4000)
        ]
      ]
    });

    this.form.valueChanges.subscribe(data => this.onValueChanged(data));

    this.onValueChanged();
  }

  public onValueChanged(data?: any) {
    if (!this.form) {
      return;
    }

    super.onValueChanged(this.form, this.formErrors, this.validationMessages);
  }

  public onChangeTipo(): void {
    this.logger.debug('GrupoProdutoEditComponent.onChangeTipo();');
    //
  }

  public onSubmit(): void {
    this.logger.debug('ProdutoEditComponent.onSubmit();');
    const grupo: GrupoProduto = this.form.value;
    this.logger.debug(JSON.stringify(grupo));
    let observable: Observable<GrupoProduto>;
    if (this.id) {
      observable = this.grupoProdutoService.update(grupo);
    } else {
      observable = this.grupoProdutoService.save(grupo);
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
