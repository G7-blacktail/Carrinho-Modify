import { Component, EventEmitter, OnInit, ViewChild } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { NgbModal, NgbModalRef, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { DialogComponent } from './dialog';
import { QueryData } from '../models';
import { LocalStorageService, Logger } from '../services';

export abstract class AbstractComponent {

  private loadingModal: NgbModalRef;

  constructor(protected logger: Logger, protected localStorageService: LocalStorageService, protected modalService: NgbModal) {
    //
  }

  public restore(name: string): any {
    return this.localStorageService.get(name);
  }

  public store(name: string, value: any): void {
    this.localStorageService.set(name, value);
  }

  public storeQueryData(name: string, text: string , pageNumber: number, pageSize: number): void {
    const queryData: QueryData = {
      'text': text,
      'pageNumber': pageNumber,
      'pageSize': pageSize
    };
    this.logger.debug('Storing Query Data ' + name + ' => ' + JSON.stringify(queryData));
    this.localStorageService.set(name, queryData);
  }

  public restoreQueryData(name: string): QueryData {
    const queryData: QueryData = this.localStorageService.get(name);
    if (queryData) {
      this.logger.debug('Restoring Query Data ' + name + ' => ' + JSON.stringify(queryData));
    } else {
      this.logger.debug('Restoring Query Data ' + name + ' => null');
    }

    return queryData;
  }

  protected setFormValue(form: FormGroup, errors: any, item: any) {
    form.patchValue(item, {onlySelf: false, emitEvent: true});
    for (const field in errors) {
      if (errors.hasOwnProperty(field)) {
        const control = form.get(field);
        // control.updateValueAndValidity({onlySelf: true, emitEvent: true} );
        control.markAsDirty();
        control.markAsTouched();
      }
    }
  }

  protected setFormFieldValue(control: AbstractControl, value: any, reset?: boolean) {
    control.setValue(value);
    if (!reset) {
      control.markAsDirty();
      control.markAsTouched();
    } else {
      control.markAsPristine();
      control.markAsUntouched();
    }
  }

  protected onValueChanged(form: FormGroup, errors: any, messages: any) {
    if (!form) {
      return;
    }

    for (const field in errors) {
      if (errors.hasOwnProperty(field)) {
        // clear previous error message (if any)
        const control = form.get(field);
        errors[field] = '';

        if (control && control.dirty && !control.valid) {
          const fieldMessages = messages[field];
          for (const key in control.errors) {
            if (control.errors.hasOwnProperty(key)) {
              errors[field] += fieldMessages[key] + ' ';
            }
          }
        }
      }
    }
  }

  protected onSuccess(item: any) {
    this.logger.debug('OK...');
    this.logger.debug(item);
    let msg = ['Operação realizada corretamente'];
    console.log('Tipo do item: ' + typeof item);
    if (typeof item === 'string') {
      msg = [item];
    } else if (item instanceof Array) {
      msg = item;
    } else if ((item) && (item.data)) {
      msg = [item.data];
    } else if ((item) && (item.content)) {
      msg = [item.content];
    }
    this.showDialog('Mensagem', msg, 'primary');
  }

  protected onError(response: any) {
    this.logger.debug('Error...');
    let msg = ['Operação não foi realizada corretamente'];
    if (typeof response === 'string') {
      msg = [response];
    } else if ((response) && (response.data)) {
      msg = [response.data];
    } else if ((response) && (response.content)) {
      msg = [response.content];
    }
    this.showDialog('Erro', msg, 'danger');
  }

  protected showDialog(title: string, msg: string| Array<string>, type: string) {
    if (this.modalService) {
      const ref = this.modalService.open(DialogComponent);
      const instance: DialogComponent = ref.componentInstance as DialogComponent;
      instance.configure(title, msg, type, true);
    } else {
      this.logger.debug('Modal Service not found');
    }
  }

}
