import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { AbstractComponent } from '../../shared/components';
import { Page, Convenio, Usuario } from '../../shared/models';
import { LocalStorageService, Logger, ConvenioService, UsuarioService } from '../../shared/services';

@Component({
  selector: 'app-convenio-edit',
  templateUrl: './convenio-edit.component.html',
  styleUrls: ['./convenio-edit.component.css']
})
export class ConvenioEditComponent extends AbstractComponent implements OnInit {

  id: string;

  form: FormGroup;

  readonly formErrors = {
    'codigo': '',
    'nome': '',
    'valor': '',
    'ativo': ''
  };

  readonly validationMessages = {
    'codigo': {
      'required':      'O código deve ser informado',
      'minlength':     'O código deve ter pelo menos 2 caracteres',
      'maxlength':     'O código não pode ter mais de 20 caracteres'
    },
    'nome': {
      'required':      'O nome deve ser informado',
      'minlength':     'O nome deve ter pelo menos 3 caracteres',
      'maxlength':     'O nome não pode ter mais de 100 caracteres'
    },
    'valor': {
      'required':      'O valor deve ser informado',
      'minlength':     'O valor deve ter pelo menos 1 caracteres',
      'maxlength':     'O valor não pode ter mais de 3 caracteres'
    }
  };

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private fb: FormBuilder, private route: ActivatedRoute, private router: Router,
    private convenioService: ConvenioService, private usuarioService: UsuarioService) {
    super(logger, localStorageService, modalService);
  }

  public ngOnInit() {
    this.logger.debug('ConvenioEditComponent.ngOnInit();');

    this.route.params.subscribe(
      (params: Params) => {
        if (params['id']) {
          const id: string = params['id'];
          this.convenioService.get(id).subscribe(
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
        }
      }
    );

    this.route.params.subscribe(
      (params: Params) => {
        if (params['id']) {
          const id: string = params['id'];
          this.convenioService.get(id).subscribe(
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
        }
      }
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
      'nome': ['', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100)
        ]
      ],
      'valor': ['', [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(3)
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

  public onSubmit(): void {
    this.logger.debug('ConvenioEditComponent.onSubmit();');
    const convenio: Convenio = this.form.value;
    this.logger.debug(JSON.stringify(convenio));

    if (this.id) {
      convenio.id = this.id;
      this.convenioService.update(convenio).subscribe(
        item => {
          this.onSuccess(item);
          this.setFormValue(this.form, this.formErrors, item);
        },
        response => this.onError(response),
        () => this.logger.debug('Complete')
      );
    } else {
      this.convenioService.save(convenio).subscribe(
        item => {
          this.id = item.id;
          this.onSuccess(item);
          this.setFormValue(this.form, this.formErrors, item);
        },
        response => this.onError(response),
        () => this.logger.debug('Complete')
      );
    }


  }

}
