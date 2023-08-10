import { ItemAgenda } from './item-agenda';

export interface DiaAgenda {
  dia?: string;
  data?: string;
  diaStr?: string;
  itemList?: Array<ItemAgenda>;
}
