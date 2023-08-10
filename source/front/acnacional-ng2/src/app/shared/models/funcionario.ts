import { Posto } from './posto';
import { Usuario } from './usuario';

export interface Funcionario {
  id?: string;
  tipo?: number;
  posto?: Posto;
  usuario?: Usuario;
}
