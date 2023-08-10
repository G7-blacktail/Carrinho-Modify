import { Municipio } from './municipio';
import { UF } from './uf';

export interface Posto {
  id?: string;
  codigo?: string;
  nome?: string;
  responsavel?: string;
  email?: string;
  endereco?: string;
  complemento?: string;
  numero?: string;
  bairro?: string;
  cep?: string;
  telefone?: string;
  mapa?: string;
  ativo?: boolean;
  uf?: UF;
  municipio?: Municipio;
}
