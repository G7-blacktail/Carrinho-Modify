import { Horario } from './horario';
import { Posto } from './posto';

export interface HorarioPosto {
  id?: string;
  data?: string;
  evento?: string;
  tipo?: number;
  disponivel?: boolean;
  horario?: Horario;
  posto?: Posto;
}
