import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { SharedModule } from '../../shared';

import { PublicoRoutingModule } from './publico-routing.module';
import { ComprarComponent } from './comprar.component';
import { ConvenioDialogComponent } from './convenio-dialog.component';
import { FinalizarComponent } from './finalizar.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    // UI
    NgbModule,
    // App
    PublicoRoutingModule,
    SharedModule
  ],
  declarations: [
    ComprarComponent,
    ConvenioDialogComponent,
    FinalizarComponent
  ],
  entryComponents: [
    ConvenioDialogComponent
  ]

})
export class PublicoModule { }
