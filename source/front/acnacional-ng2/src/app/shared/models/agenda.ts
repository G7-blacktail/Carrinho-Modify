import { DiaAgenda } from './dia-agenda';
import { Posto } from './posto';

export interface Agenda {
  mes?: string;
  ano?: string;
  mesStr?: string;
  posto?: Posto;
  diaList?: Array<DiaAgenda>;
}
