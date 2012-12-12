package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Servico;
import br.com.cesaretransportes.modelo.Veiculo;
import br.com.cesaretransportes.util.AcaoVeiculo;
import br.com.cesaretransportes.util.CesareUtil;
import br.com.cesaretransportes.util.MSG;

public class VeiculoServlet extends HttpServlet {	
	private static final long serialVersionUID = 1L;
	
	private String placa;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			VeiculoDao veiculoDao = new VeiculoDao(conexao);
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			ServicoDao servicoDao = new ServicoDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			
			Veiculo veiculo = new Veiculo();
			List<Veiculo> veiculos;
			String pagina = "/index.jsp";
			int idVeiculo;
			String localizacao;
			
			final int acaoVeiculo = Integer.parseInt(request.getParameter("acao"));					
			switch (acaoVeiculo) {
			case AcaoVeiculo.ACESSAR_CADASTRO:
				request.setAttribute("cadastrarNovo", true);
				pagina = "/mostrar-veiculo.jsp";			
				break;
				
			case AcaoVeiculo.CADASTRAR:				
				boolean validado = validarDados(request, veiculo, veiculoDao);			
				if (validado){					
					veiculoDao.cadastrar(veiculo);
					pagina = "/index-sistema-interno.jsp";					
					request.setAttribute("mensagem", "Ve&iacute;culo cadastrado com sucesso !");			
				}else{
					veiculo = veiculoDao.getVeiculo(placa, orcamentoDao);
					request.setAttribute("veiculoCadastrado", veiculo);
					pagina = "/mostrar-veiculo.jsp";
					request.setAttribute("cadastrarNovo", true);
				}			
				break;
				
			case AcaoVeiculo.CONFIRMAR_ENTREGAS:
				idVeiculo = Integer.parseInt(request.getParameter("idVeiculo"));
				veiculo = veiculoDao.getVeiculo(idVeiculo);
				atualizarVeiculo(servicoDao, orcamentoDao, enderecoDao, veiculo);
				servicoDao.confirmarEntregas(veiculo.getServicos());				
			case AcaoVeiculo.LISTAR_CADASTRADOS:
				veiculos = atualizarTodosOsVeiculos(veiculoDao, servicoDao, orcamentoDao, enderecoDao);
				request.setAttribute("veiculos", veiculos);
				pagina = "/mostrar-veiculo.jsp";
				break;
				
			case AcaoVeiculo.DELETAR:
				idVeiculo = Integer.parseInt(request.getParameter("id"));
				veiculoDao.deletar(idVeiculo);
				veiculos = atualizarTodosOsVeiculos(veiculoDao, servicoDao, orcamentoDao, enderecoDao);
				request.setAttribute("veiculos", veiculos);
				pagina = "/mostrar-veiculo.jsp";
				break;
				
			case AcaoVeiculo.ACESSAR_EDITAR:
				idVeiculo = Integer.parseInt(request.getParameter("id"));
				veiculo = veiculoDao.getVeiculo(idVeiculo);
				localizacao = request.getParameter("localizacao");
				// sinaliza a página para rederizar o formulário de cadastro com os dados preenchidos.
				request.setAttribute("flagAlterar", true);
				request.setAttribute("localizacao", localizacao);
				request.setAttribute("cadastrarNovo", true);
				configurarRequestParaAlteracao(request, veiculo);
				pagina = "/mostrar-veiculo.jsp";
				break;
				
			case AcaoVeiculo.EDITAR:
				idVeiculo = Integer.parseInt(request.getParameter("id"));
				veiculo = new Veiculo();
				veiculo.setLocalizacao(request.getParameter("localizacao"));
				veiculo.setIdVeiculo(idVeiculo);			
				veiculoDao.editar(veiculo);
				
				veiculos = atualizarTodosOsVeiculos(veiculoDao, servicoDao, orcamentoDao, enderecoDao);
				request.setAttribute("veiculos", veiculos);				
				request.setAttribute("veiculoAlterado", CesareUtil.formatarPlaca(request.getParameter("placa")));
				pagina = "/mostrar-veiculo.jsp";						
				break;
				
			case AcaoVeiculo.BUSCA:
				String filtro = request.getParameter("filtro");
				String parametro = request.getParameter("parametro");		
				
				if(parametro.equals("")){
					request.setAttribute("msgBusca", "Digite um parametro para busca!");
					pagina = "/mostrar-veiculo.jsp";					
				}else{
					if(filtro.equals("id")){
						try {
							idVeiculo = Integer.parseInt(parametro);
							veiculo = veiculoDao.getVeiculo(idVeiculo);							
							if(veiculo == null){
								request.setAttribute("msgBusca", "Resultado: 0 ocorrencias para id:" + parametro + "!");
								pagina = "/mostrar-veiculo.jsp";
							}else{
								atualizarVeiculo(servicoDao, orcamentoDao, enderecoDao, veiculo);
								request.setAttribute("veiculos", Arrays.asList(veiculo));
								pagina = "/mostrar-veiculo.jsp";
							}
						} catch (NumberFormatException e) {
							request.setAttribute("msgBusca", "Digite apenas numeros para filtro por id!");
							pagina = "/mostrar-veiculo.jsp";
						}
					}else{						
						veiculos = filtrar(atualizarTodosOsVeiculos(veiculoDao, servicoDao, orcamentoDao, enderecoDao), filtro, parametro);
						request.setAttribute("msgBusca", "Resultado: " + veiculos.size() + " ocorrencias para " + filtro  + " : " + parametro);
						request.setAttribute("veiculos", veiculos);
						pagina = "/mostrar-veiculo.jsp";						
					}
				}				
				break;
				
			case AcaoVeiculo.VISUALIZAR_ORCAMENTOS:
				idVeiculo = Integer.parseInt(request.getParameter("idVeiculo"));
				veiculo = veiculoDao.getVeiculo(idVeiculo);
				atualizarVeiculo(servicoDao, orcamentoDao, enderecoDao, veiculo);
				request.setAttribute("veiculo", veiculo);
				request.setAttribute("orcamentosPorVeiculo", true);
				pagina = "/mostrar-veiculo.jsp";				
				break;
				
			case AcaoVeiculo.RECADASTRAR_VEICULO:
				idVeiculo = Integer.parseInt(request.getParameter("idVeiculo"));
				String placa = request.getParameter("placa");
				String marca = request.getParameter("marca");
				String cor = request.getParameter("cor");
				localizacao = request.getParameter("localizacao");	
				
				veiculoDao.recadastrar(idVeiculo, marca, cor, localizacao);
				
				veiculos = atualizarTodosOsVeiculos(veiculoDao, servicoDao, orcamentoDao, enderecoDao);
				request.setAttribute("veiculos", veiculos);
				request.setAttribute("veiculoRecadastrado", CesareUtil.formatarPlaca(placa));
				pagina = "/mostrar-veiculo.jsp";
				break;			
				
			default:
				new CetransServletException("VeiculoServlet", getClass().getSimpleName(), "No switch for '" + acaoVeiculo + "' option.");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}
	}
	
	private List<Veiculo> atualizarTodosOsVeiculos(VeiculoDao veiculoDao, ServicoDao servicoDao, OrcamentoDao orcamentoDao, EnderecoDao enderecoDao) throws SQLException {
		List<Veiculo> veiculos = veiculoDao.getAll();
		if(veiculos.size() > 0){
			for (Veiculo veiculo : veiculos){				
				atualizarVeiculo(servicoDao, orcamentoDao, enderecoDao, veiculo);				
			}
		}
		
		return veiculos;
	}

	protected void atualizarVeiculo(ServicoDao servicoDao, OrcamentoDao orcamentoDao, EnderecoDao enderecoDao, Veiculo veiculo) throws SQLException {
		List<Servico> servicos = servicoDao.getServicosPorVeiculo(veiculo.getIdVeiculo(), true);				
		for(Servico servico : servicos){
			servico.setVeiculo(veiculo);
			servico.setOrcamento(orcamentoDao.getOrcamento(servico.getOrcamento().getIdOrcamento()));
			servico.getOrcamento().setEnderecos(enderecoDao.getEnderecosPorOrcamentos(servico.getOrcamento().getIdOrcamento()));
		}
		veiculo.setServicos(servicos);
	}

	private List<Veiculo> filtrar(List<Veiculo> veiculos, String filtro, String parametro) {
		List<Veiculo> veiculosFiltrados = new ArrayList<Veiculo>();
		
		if(filtro.equals("placa")){
			for(Veiculo veiculo : veiculos){
				if(veiculo.getPlaca().contains(parametro)){
					veiculosFiltrados.add(veiculo);
				}
			}
		}else{
			for(Veiculo veiculo : veiculos){
				if(veiculo.getLocalizacao().contains(parametro)){
					veiculosFiltrados.add(veiculo);
				}
			}
		}
		
		return veiculosFiltrados;
	}

	private boolean validarDados(HttpServletRequest request, Veiculo veiculo, VeiculoDao veiculoDao) throws SQLException {
		boolean validado = true;
		
		String prefixoPlaca = request.getParameter("prefixoPlaca");
		request.setAttribute("prefixoPlaca", prefixoPlaca);
		if (!prefixoPlaca.matches("[A-Za-z]{3}")){
			request.setAttribute("erroPrefixoPlaca", MSG.ERRO_PREFIXO_PLACA.toString());
			validado = false;
		}else{			
			prefixoPlaca = prefixoPlaca.toUpperCase();
		}
		
		String sufixoPlaca = request.getParameter("sufixoPlaca");
		request.setAttribute("sufixoPlaca", sufixoPlaca);
		if (!sufixoPlaca.matches("\\d{4}")){
			request.setAttribute("erroSufixoPlaca", MSG.ERRO_SUFIXO_PLACA.toString());
			validado = false;
		}
		
		placa = prefixoPlaca + sufixoPlaca;
		if(veiculoDao.veiculoJaCadastrado(placa, true)){
			request.setAttribute("erroVeiculoCadAtivo", MSG.ERRO_VEICULO_CADASTRADO.toString());
			validado = false;
		}else if (veiculoDao.veiculoJaCadastrado(placa, false)){
			request.setAttribute("erroVeiculoCadInativo", MSG.ERRO_VEICULO_CADASTRADO.toString());
			validado = false;
		}
		veiculo.setPlaca(placa);
	
		String marca = request.getParameter("marca");
		request.setAttribute("marca", marca);
		if (marca.isEmpty()){
			request.setAttribute("erroMarca", MSG.ERRO_MARCA.toString());
			validado = false;
		}		
		veiculo.setMarca(marca);
		
		String cor = request.getParameter("cor");
		request.setAttribute("cor", cor);
		if (cor.isEmpty()){
			request.setAttribute("erroCor", MSG.ERRO_COR.toString());
			validado = false;
		}		
		veiculo.setCor(cor);
		
		String localizacao = request.getParameter("localizacao");
		veiculo.setLocalizacao(localizacao);	
				
		return validado;
	}

	/*protected boolean validarData(HttpServletRequest request, Veiculo veiculo, String dtDia, String dtMes,	String dtAno, Data data) {
		if (!dtDia.isEmpty() && !dtMes.isEmpty() && !dtAno.isEmpty()){		
			if (dataConfere(dtDia, dtMes, dtAno)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setLenient(false);
				calendar.set(Integer.parseInt(dtAno),
						Integer.parseInt(dtMes) - 1,
						Integer.parseInt(dtDia));
				if (data == Data.SAIDA){
					veiculo.setDataSaida(calendar);
				}else{
					veiculo.setDataChegada(calendar);
				}				
			} else {
				request.setAttribute(data == Data.SAIDA ? "erroDataSaida" : "erroDataChegada", MSG.ERRO_DATA.toString());
				return false;
			}
		}
		return true;
	}

	private boolean dataConfere(String dtDia, String dtMes, String dtAno) {
		
		try {
			int dia = Integer.parseInt(dtDia);
			if(dia < 0 || dia > 31){
				return false;
			}
			
			int mes = Integer.parseInt(dtMes);
			if(mes < 0 || mes > 12){
				return false;
			}
			
			if (mes == 2 && dia > 28){
				return false;
			}	
			
			if (dtAno.length() != 4){
				return false;
			}
			Integer.parseInt(dtAno);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}		
	}*/
	
	private void configurarRequestParaAlteracao(HttpServletRequest request,	Veiculo veiculo) {
		request.setAttribute("id", veiculo.getIdVeiculo());
		request.setAttribute("prefixoPlaca", veiculo.getPlaca().substring(0, 3));
		request.setAttribute("sufixoPlaca", veiculo.getPlaca().substring(3));
		request.setAttribute("marca", veiculo.getMarca());
		request.setAttribute("cor", veiculo.getCor());
		/*request.setAttribute("origem", veiculo.getOrigem());
		request.setAttribute("estadoOrigem", veiculo.getEstadoOrigem());
		request.setAttribute("enderecoOrigem", veiculo.getEnderecoOrigem());		
		request.setAttribute("destino", veiculo.getDestino());
		request.setAttribute("estadoDestino", veiculo.getEstadoDestino());
		request.setAttribute("enderecoDestino", veiculo.getEnderecoDestino());
		request.setAttribute("localizacao", veiculo.getLocalizacao());*/
		
		/*if(veiculo.getDataSaida() != null){
			request.setAttribute("dtSaidaDia", CesareUtil.formatarData(veiculo.getDataSaida(), "dd"));
			request.setAttribute("dtSaidaMes", CesareUtil.formatarData(veiculo.getDataSaida(), "MM"));
			request.setAttribute("dtSaidaAno", CesareUtil.formatarData(veiculo.getDataSaida(), "yyyy"));
		}
		
		if(veiculo.getDataChegada() != null){
			request.setAttribute("dtChegadaDia", CesareUtil.formatarData(veiculo.getDataChegada(), "dd"));
			request.setAttribute("dtChegadaMes", CesareUtil.formatarData(veiculo.getDataChegada(), "MM"));
			request.setAttribute("dtChegadaAno", CesareUtil.formatarData(veiculo.getDataChegada(), "yyyy"));
		}	*/		
	}	
}
