import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { AbstractPreCadastroEditComponent } from './abstract-pre-cadastro-edit.component';
import { Municipio, PreCadastro, Page, UF } from '../../shared/models';
import { LocalStorageService, Logger, MunicipioService, PreCadastroService, UfService, VoucherService } from '../../shared/services';

@Component({
  selector: 'app-pre-cadastro-pj-publico-edit',
  templateUrl: './pre-cadastro-pj-edit.component.html',
  styleUrls: ['./pre-cadastro-pj-edit.component.css']
})
export class PreCadastroPjEditComponent extends AbstractPreCadastroEditComponent {

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    fb: FormBuilder, route: ActivatedRoute, router: Router,
    preCadastroService: PreCadastroService, ufService: UfService, municipioService: MunicipioService,
    voucherService: VoucherService) {
    super(logger, localStorageService, modalService, fb, route, router, preCadastroService, ufService, municipioService, voucherService, 2);
  }

}
