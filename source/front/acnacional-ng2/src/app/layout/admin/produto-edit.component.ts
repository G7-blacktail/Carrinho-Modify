import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { AbstractComponent } from '../../shared/components';
import { GrupoProduto, Produto, Page, SubGrupoProduto, TipoProduto } from '../../shared/models';
import { LocalStorageService, Logger, GrupoProdutoService, ProdutoService, SubGrupoProdutoService, TipoProdutoService } from '../../shared/services';

@Component({
  selector: 'app-produto-edit',
  templateUrl: './produto-edit.component.html',
  styleUrls: ['./produto-edit.component.css']
})
export class ProdutoEditComponent extends AbstractComponent implements OnInit {

  id: string;

  form: FormGroup;

  tipoList: Array<TipoProduto>;

  grupoList: Array<GrupoProduto>;

  subGrupoList: Array<SubGrupoProduto>;

  readonly formErrors = {
    'codigo': '',
    'codigoSerpro': '',
    'ordem': '',
    'nome': '',
    'descricao': '',
    'tipo.id': '',
    'grupo.id': '',
    'subGrupo.id': '',
    'imagem': '',
    'validade': '',
    'valor': '',
    'valorDesconto': '',
    'valorReferencia': '',
    'ativo': ''
  };

  readonly validationMessages = {
    'codigo': {
      'required':      'O código deve ser informado',
      'minlength':     'O código deve ter pelo menos 3 caracteres',
      'maxlength':     'O código não pode ter mais de 20 caracteres'
    },
    'codigoSerpro': {
      'minlength':     'O código SERPRO deve ter pelo menos 2 caracteres',
      'maxlength':     'O código SERPRO não pode ter mais de 50 caracteres'
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
      'required':      'A descrição deve ser informada',
      'minlength':     'A descrição deve ter pelo menos 3 caracteres',
      'maxlength':     'A descrição não pode ter mais de 4000 caracteres'
    },
    'tipo.id': {
      'required':      'O tipo deve ser selecionada',
      'minlength':     'O tipo deve ter pelo menos 1 caracteres',
      'maxlength':     'O tipo não pode ter mais de 100 caracteres'
    },
    'grupo.id': {
      'required':      'O grupo deve ser selecionado',
      'minlength':     'O grupo deve ter pelo menos 1 caracteres',
      'maxlength':     'O grupo não pode ter mais de 100 caracteres'
    },
    'subGrupo.id': {
      'required':      'O subgrupo deve ser selecionado',
      'minlength':     'O subgrupo deve ter pelo menos 1 caracteres',
      'maxlength':     'O subgrupo não pode ter mais de 100 caracteres'
    },
    'imagem': {
      'maxlength':     'A imagem não pode ter mais de 4000 caracteres'
    },
    'validade': {
      'maxlength':     'A validade não pode ter mais de 3 caracteres'
    },
    'valor': {
      'required':      'O valor deve ser informado',
      'minlength':     'O valor deve ter pelo menos 3 caracteres',
      'maxlength':     'O valor não pode ter mais de 10 caracteres'
    },
    'valorDesconto': {
      'required':      'O valor do desconto deve ser informado',
      'minlength':     'O valor do desconto deve ter pelo menos 3 caracteres',
      'maxlength':     'O valor do desconto não pode ter mais de 10 caracteres'
    },
    'valorReferencia': {
      'required':      'O valor de referência deve ser informado',
      'minlength':     'O valor de referência deve ter pelo menos 3 caracteres',
      'maxlength':     'O valor de referência não pode ter mais de 10 caracteres'
    }

  };

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private fb: FormBuilder, private route: ActivatedRoute, private router: Router,
    private produtoService: ProdutoService, private tipoProdutoService: TipoProdutoService,
    private grupoProdutoService: GrupoProdutoService, private subGrupoProdutoService: SubGrupoProdutoService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('ProdutoEditComponent.ngOnInit();');

    this.tipoProdutoService.list(false, '', 0, 1000).subscribe(
      page => {
        this.tipoList = page.content;
        this.route.params.subscribe(
          (params: Params) => {
            if (params['id']) {
              const id: string = params['id'];
              this.produtoService.get(id).subscribe(
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
          Validators.minLength(3),
          Validators.maxLength(20)
        ]
      ],
      'codigoSerpro': ['', [
          Validators.minLength(2),
          Validators.maxLength(50)
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
          Validators.required,
          Validators.minLength(3),
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
      'grupo': this.fb.group({
        'id': ['', [
            Validators.required,
            Validators.minLength(1),
            Validators.maxLength(100)
          ]
        ]
      }),
      'subGrupo': this.fb.group({
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
      ],
      'validade': ['', [
          Validators.maxLength(3)
        ]
      ],
      'valor': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(10)
        ]
      ],
      'valorDesconto': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(10)
        ]
      ],
      'valorReferencia': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(10)
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
    this.logger.debug('ComprarComponent.onChangeTipo();');
    this.grupoList = null;
    this.subGrupoList = null;
    const tipo = (this.form.controls['tipo'] as FormGroup).controls['id'].value;
    this.grupoProdutoService.list(tipo, false, '', 0, 1000).subscribe(
      page => {
        this.grupoList = page.content;
        if (this.grupoList.length > 0) {
          const grupo = (this.form.controls['grupo'] as FormGroup).controls['id'].value;
          if (!grupo) {
            (this.form.controls['grupo'] as FormGroup).controls['id'].patchValue(this.grupoList[0].id);
          }
          this.onChangeGrupo();
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onChangeGrupo(): void {
    this.logger.debug('ComprarComponent.onChangeGrupo();');
    this.subGrupoList = null;
    const tipo = (this.form.controls['tipo'] as FormGroup).controls['id'].value;
    const grupo = (this.form.controls['grupo'] as FormGroup).controls['id'].value;
    this.subGrupoProdutoService.list(tipo, grupo, false, '', 0, 1000).subscribe(
      page => {
        this.subGrupoList = page.content;
        if (this.subGrupoList.length > 0) {
          const subGrupo = (this.form.controls['subGrupo'] as FormGroup).controls['id'].value;
          if (!subGrupo) {
            (this.form.controls['subGrupo'] as FormGroup).controls['id'].patchValue(this.subGrupoList[0].id);
          }
        }
      },
      response => this.onError(response),
      () => this.logger.debug('Complete')
    );
  }

  public onSubmit(): void {
    this.logger.debug('ProdutoEditComponent.onSubmit();');
    const produto: Produto = this.form.value;
    this.logger.debug(JSON.stringify(produto));
    let observable: Observable<Produto>;
    if (this.id) {
      observable = this.produtoService.update(produto);
    } else {
      observable = this.produtoService.save(produto);
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
