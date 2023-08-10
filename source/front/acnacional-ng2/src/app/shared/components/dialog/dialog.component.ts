import { Component, Input } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Logger } from '../../services';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {

  @Input()
  title: string;

  @Input()
  messages: Array<string>;

  @Input()
  type: string;

  @Input()
  closeable: boolean;

  constructor(public activeModal: NgbActiveModal) {
    //
  }

  public configure(title: string, msg: string|Array<string>, type: string = 'primary', closeable: boolean = true): void {
    this.title = title;
    this.type = type;
    this.closeable = closeable;
    if (msg instanceof Array) {
      this.messages = msg;
    } else {
      this.messages = [msg];
    }
  }

}
