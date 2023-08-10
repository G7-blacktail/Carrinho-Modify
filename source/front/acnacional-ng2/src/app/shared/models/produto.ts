import { GrupoProduto } from './grupo-produto';
import { SubGrupoProduto } from './subgrupo-produto';
import { TipoProduto } from './tipo-produto';

export interface Produto {
  id?: string;
  codigo?: string;
  codigoSerpro?: string;
  ordem?: number;
  nome?: string;
  descricao?: string;
  imagem?: string;
  validade?: number;
  valor?: string;
  valorDesconto?: string;
  valorReferencia?: string;
  ativo?: boolean;
  tipo?: TipoProduto;
  grupo?: GrupoProduto;
  subGrupo?: SubGrupoProduto;
  frete?:boolean;
}
