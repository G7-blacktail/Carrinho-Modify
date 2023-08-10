import { Cliente } from './cliente';

export interface PreCadastro {
  id?: string;
  idVoucher?: string;
  tipo?: number;

  // Comum?: string;
  nomeResp?: string;
  dataNascimentoResp?: string;
  cpfResp?: string;
  emailResp?: string;
  rgResp?: string;
  orgaoEmissorRgResp?: string;
  ufOrgaoEmissorRgResp?: string;

  pisPasepResp?: string;
  cepResp?: string;
  logradouroResp?: string;
  numeroEnderecoResp?: string;
  complementoEnderecoResp?: string;
  bairroResp?: string;
  municipioResp?: string;
  ufResp?: string;
  telefonePrincipal?: string;
  telefoneAlternativo?: string;

  // PF?: string;
  tituloEleitor?: string;
  zonaTituloEleitor?: string;
  secaoTituloEleitor?: string;
  municipioTituloEleitor?: string;
  ufTituloEleitor?: string;
  inssCeipf?: string;

  // PJ?: string;
  nomeEmpresarial?: string;
  cnpj?: string;
  inssCei?: string;
  municipioEmpresa?: string;
  ufEmpresa?: string;
  nomeRepresentanteLegal?: string;
  cpfRepresentanteLegal?: string;
  dataNascimentoRepresentanteLegal?: string;

  cliente: Cliente;
}
