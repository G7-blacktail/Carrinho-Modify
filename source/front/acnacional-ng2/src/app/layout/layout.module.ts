import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { SharedModule } from '../shared';

import { LayoutRoutingModule } from './layout-routing.module';
import { LayoutComponent } from './layout.component';

import { AdminModule, AdminRoutingModule } from './admin';

@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    // App
    LayoutRoutingModule,
    AdminModule,
    AdminRoutingModule,
    SharedModule
  ],
  declarations: [
    LayoutComponent,
  ]
})
export class LayoutModule { }
