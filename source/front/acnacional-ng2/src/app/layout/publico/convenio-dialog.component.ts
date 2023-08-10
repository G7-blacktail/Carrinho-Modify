import { Observable } from 'rxjs';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Subject } from 'rxjs';

import { Convenio } from '../../shared/models';
import { Logger, ConvenioService } from '../../shared/services';

@Component({
  selector: 'app-convenio-dialog',
  templateUrl: './convenio-dialog.component.html',
  styleUrls: ['./convenio-dialog.component.css']
})
export class ConvenioDialogComponent {

  mode = 0;

  codigo: string;

  convenio: Convenio;

  error = false;

  validating = false;

  private subject: Subject<Convenio>;

  constructor(private logger: Logger, private convenioService: ConvenioService, private activeModal: NgbActiveModal) {
    this.subject = new Subject<Convenio>();
  }

  public configure(convenio?: Convenio): Observable<Convenio> {
    this.codigo = '';
    this.convenio = convenio;
    if (convenio) {
      this.codigo = convenio.codigo;
      this.mode = 1;
    }
    return this.subject.asObservable();
  }

  public onCheckItem(): void {
    if ((this.codigo) && (this.codigo.length > 0)) {

    }
  }

  public onNext(): void {
    this.mode = 1;
  }

  public onAdd(): void {
    if ((this.codigo) && (this.codigo.length > 1)) {
      this.validating = true;
      this.convenioService.get(this.codigo).subscribe(
        item => {
          this.subject.next(item);
          this.validating = false;
          this.activeModal.dismiss('Close');
        },
        err => {
          this.logger.debug(err);
          this.error = true;
          this.validating = false;
        },
        () => this.logger.debug('Complete')
      );
    }
  }

  public onCancel(): void {
    // this.subject.next(this.convenioList);
    this.subject.error('Cancelado');
    this.activeModal.dismiss('Close');
  }

}
