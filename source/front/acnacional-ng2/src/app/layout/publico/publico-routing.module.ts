import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './../../authguard';

import { ComprarComponent } from './comprar.component';
import { FinalizarComponent } from './finalizar.component';

const routes: Routes = [
  { path: '', component: ComprarComponent, canActivate: [AuthGuard] },
  { path: 'finalizar/:id', component: FinalizarComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicoRoutingModule { }
