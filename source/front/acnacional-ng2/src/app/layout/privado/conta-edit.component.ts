import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NavigationEnd, Router } from '@angular/router';

import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';
import { Subject } from 'rxjs';

import { AbstractComponent } from '../../shared/components/abstract.component';

import { LocalStorageService, Logger, SessionService, UsuarioService } from '../../shared/services';

import { Usuario } from '../../shared/models';

@Component({
  selector: 'app-conta-edit',
  templateUrl: './conta-edit.component.html',
  styleUrls: ['./conta-edit.component.css']
})
export class ContaEditComponent extends AbstractComponent implements OnInit {

  email: string;

  nome: string;

  cpf: string;

  dataNascimento: string;

  telefone: string;

  senhaAtual: string;

  novaSenha: string;

  confirmacaoSenha: string;

  cpfMask: Array<string | RegExp>;

  dataMask: Array<string | RegExp>;

  executing = false;

  error = false;

  errorMessage: string;

  constructor(logger: Logger, localStorageService: LocalStorageService, modalService: NgbModal,
              private usuarioService: UsuarioService, private sessionService: SessionService) {
    super(logger, localStorageService, modalService);

    this.cpfMask = [/\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/, '-', /\d/, /\d/];
    this.dataMask = [/\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/];
  }

  public ngOnInit() {
    const u = this.sessionService.getUsuario();
    console.log(JSON.stringify(u));
    this.email = u.email;
    this.nome = u.nome;
  }

  public save(): void {
    this.executing = true;
    this.error = false;
    this.errorMessage = null;

    if ((!this.email) || (!this.email.trim())) {
      this.error = true;
      this.errorMessage = 'O E-mail deve ser informado';
      this.executing = false;
      return;
    }

    if (this.email.length < 3) {
      this.error = true;
      this.errorMessage = 'E-mail inválido';
      this.executing = false;
      return;
    }

    if (!this.nome) {
      this.error = true;
      this.errorMessage = 'O nome deve ser informado';
      this.executing = false;
      return;
    }

    if (this.nome.length < 3) {
      this.error = true;
      this.errorMessage = 'Nome inválido';
      this.executing = false;
      return;
    }

    if (this.nome.trim().split(' ').length < 2) {
      this.error = true;
      this.errorMessage = 'Nome inválido';
      this.executing = false;
      return;
    }

    if (this.novaSenha) {
      if ((this.novaSenha.length < 6) || (this.novaSenha.length > 20)) {
        this.error = true;
        this.errorMessage = 'A senha deve ter entre 6 e 20 caracteres';
        this.executing = false;
        return;
      }

      if (!this.confirmacaoSenha) {
        this.error = true;
        this.errorMessage = 'A confirmação da senha deve ser informada';
        this.executing = false;
        return;
      }

      if (this.novaSenha !== this.confirmacaoSenha) {
        this.error = true;
        this.errorMessage = 'As senhas digitadas não são iguais';
        this.executing = false;
        return;
      }
    }

    const usuario: Usuario = {
      nome: this.nome,
    };
    // TODO
    /*this.usuarioService.update(usuario).subscribe(
      item => {
        this.onSuccess(item);
        this.executing = false;
        this.error = false;
        this.errorMessage = null;
      },
      response => {
        this.executing = false;
        this.error = true;
        this.errorMessage = response;
      },
      () => this.logger.debug('Complete')
    );*/
  }

}
