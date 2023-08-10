import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './../../authguard';

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

const routes: Routes = [
  { path: 'conta-edit', component: ContaEditComponent, canActivate: [AuthGuard] },
  { path: 'pedido', component: PedidoListComponent, canActivate: [AuthGuard] },
  { path: 'pedido/view/:id', component: PedidoViewComponent, canActivate: [AuthGuard] },
  { path: 'pedido/:idPedido/voucher/:idVoucher/agendamento-edit', component: AgendamentoEditComponent, canActivate: [AuthGuard] },
  { path: 'pedido/:idPedido/voucher/:idVoucher/agendamento-done', component: AgendamentoDoneComponent, canActivate: [AuthGuard] },
  { path: 'pre-cadastro', component: PreCadastroListComponent, canActivate: [AuthGuard] },
  { path: 'pre-cadastro/new', component: PreCadastroEditComponent, canActivate: [AuthGuard] },
  { path: 'pre-cadastro-pf/new', component: PreCadastroPfEditComponent, canActivate: [AuthGuard] },
  { path: 'pre-cadastro-pf/edit/:id', component: PreCadastroPfEditComponent, canActivate: [AuthGuard] },
  { path: 'pre-cadastro-pj/new', component: PreCadastroPjEditComponent, canActivate: [AuthGuard] },
  { path: 'pre-cadastro-pj/edit/:id', component: PreCadastroPjEditComponent, canActivate: [AuthGuard] },
  { path: 'voucher', component: VoucherListComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PrivadoRoutingModule { }
