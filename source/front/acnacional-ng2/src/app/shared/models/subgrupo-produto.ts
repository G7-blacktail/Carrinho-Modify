import { GrupoProduto } from './grupo-produto';

export interface SubGrupoProduto {
  id?: string;
  codigo?: string;
  ordem?: number;
  nome?: string;
  descricao?: string;
  imagem?: string;
  grupo?: GrupoProduto;
}
