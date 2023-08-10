import { Observable } from 'rxjs';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Subject } from 'rxjs';

import { Voucher } from '../../shared/models';
import { Logger } from '../../shared/services';

@Component({
  selector: 'app-precadastro-dialog',
  templateUrl: './precadastro-dialog.component.html',
  styleUrls: ['./precadastro-dialog.component.css']
})
export class PrecadastroDialogComponent {

  voucher: Voucher;

  constructor(private logger: Logger, private activeModal: NgbActiveModal) {
  }

  public configure(voucher?: Voucher): void {
    this.voucher = voucher;
  }

  public onCancel(): void {
    // this.subject.next(this.convenioList);
    this.activeModal.dismiss('Close');
  }

}
