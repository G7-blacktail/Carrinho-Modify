import { HorarioPosto } from './horario-posto';
import { Voucher } from './voucher';

export interface ItemAgenda {
  numero?: number;
  horario?: HorarioPosto;
  voucher?: Voucher;
}
