import { TipoProduto } from './tipo-produto';

export interface GrupoProduto {
  id?: string;
  codigo?: string;
  ordem?: number;
  nome?: string;
  descricao?: string;
  imagem?: string;
  tipo?: TipoProduto;
}
