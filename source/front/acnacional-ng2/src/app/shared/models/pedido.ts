import { Cliente } from './cliente';
import { Convenio } from './convenio';
import { PedidoProduto } from './pedido-produto';
import { Posto } from './posto';
import { Voucher } from './voucher';

export interface Pedido {
  id?: string;
  codigo?: string;
  data?: String;
  hora?: String;
  valor?: number;
  situacao?: number;
  codigoSolicitacaoPagamento?: string;
  codigoTransacaoPagamento?: string;
  urlSolicitacaoPagamento?: string;
  cliente?: Cliente;
  posto?: Posto;
  convenio?: Convenio;
  produtoList?: Array<PedidoProduto>;
  voucherList?: Array<Voucher>;
}
