import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { TextMaskModule } from 'angular2-text-mask';

import { SharedModule } from '../../shared';

import { PrivadoRoutingModule } from './privado-routing.module';

import { AgendamentoDoneComponent } from './agendamento-done.component';
import { AgendamentoEditComponent } from './agendamento-edit.component';
import { ContaEditComponent } from './conta-edit.component';
import { PedidoListComponent } from './pedido-list.component';
import { PedidoViewComponent } from './pedido-view.component';
import { PreCadastroEditComponent } from './pre-cadastro-edit.component';
import { PreCadastroListComponent } from './pre-cadastro-list.component';
import { PreCadastroPfEditComponent } from './pre-cadastro-pf-edit.component';
import { PreCadastroPjEditComponent } from './pre-cadastro-pj-edit.component';
import { VoucherListComponent } from './voucher-list.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    // UI
    NgbModule,
    TextMaskModule,
    // App
    PrivadoRoutingModule,
    SharedModule
  ],
  declarations: [
    AgendamentoDoneComponent,
    AgendamentoEditComponent,
    ContaEditComponent,
    PedidoListComponent,
    PedidoViewComponent,
    PreCadastroEditComponent,
    PreCadastroListComponent,
    PreCadastroPfEditComponent,
    PreCadastroPjEditComponent,
    VoucherListComponent
  ]
})
export class PrivadoModule { }
