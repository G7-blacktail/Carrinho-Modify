import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, RouterModule, Router } from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { AbstractComponent } from '../../shared/components';
import { LocalStorageService, Logger } from '../../shared/services';

@Component({
  selector: 'app-pre-cadastro-publico-edit',
  templateUrl: './pre-cadastro-edit.component.html',
  styleUrls: ['./pre-cadastro-edit.component.css']
})
export class PreCadastroEditComponent extends AbstractComponent {

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
    private fb: FormBuilder, private route: ActivatedRoute, private router: Router) {
    super(logger, localStorageService, modalService);
  }

}
