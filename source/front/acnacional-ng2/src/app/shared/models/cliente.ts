import { Usuario } from './usuario';

export interface Cliente {
  id?: string;
  nome?: string;
  documento?: string;
  email?: string;
  usuario?: Usuario;
}
