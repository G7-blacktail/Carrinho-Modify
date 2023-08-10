import { Cliente } from './cliente';
import { HorarioPosto } from './horario-posto';
import { Pedido } from './pedido';
import { PedidoProduto } from './pedido-produto';
import { PreCadastro } from './pre-cadastro';
import { Produto } from './produto';
import { Usuario } from './usuario';

export interface Voucher {
  id?: string;
  codigo?: string;
  ativo?: boolean;
  dataConfirmacao?: string;
  horaConfirmacao?: string;
  cliente?: Cliente;
  produto?: Produto;
  pedido?: Pedido;
  pedidoProduto?: PedidoProduto;
  horarioPosto?: HorarioPosto;
  preCadastro?: PreCadastro;
  usuarioConfirmacao?: Usuario;
}
