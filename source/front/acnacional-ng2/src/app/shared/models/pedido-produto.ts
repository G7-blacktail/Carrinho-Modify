import { Pedido } from './pedido';
import { Produto } from './produto';

export interface PedidoProduto {
  id?: string;
  quantidade?: number;
  valor?: number;
  pedido?: Pedido;
  produto?: Produto;
}
