package br.com.Acad.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Aluno;
import br.com.Acad.model.AlunoNota;
import br.com.Acad.model.AlunoNotaID;
import br.com.Acad.model.AlunoTurma;
import br.com.Acad.model.AlunoTurmaID;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Turma;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import br.com.Acad.model.ViewTurma;
import br.com.Acad.sql.ConnectionReserva;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.TextFieldFormatter;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class AlunosManagerController implements Initializable{

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private Tab listarTab;

	@FXML
	private JFXTextField campo_pesquisa;

	@FXML
	private JFXTextField campoPesquisa2;

	@FXML
	private TableView<ViewAluno> table_pessoas;

	@FXML
	private TableColumn<ViewAluno, String> col_codPessoa;

	@FXML
	private TableColumn<ViewAluno, String> col_nome;

	@FXML
	private TableColumn<ViewAluno, String> col_cpf;

	@FXML
	private TableColumn<ViewAluno, Date> col_dt_nascimento;

	@FXML
	private TableColumn<ViewAluno, String> col_naturalidade;

	@FXML
	private TableColumn<ViewAluno, String> col_nomeMae;

	@FXML
	private TableColumn<ViewAluno, String> col_nomePai;

	@FXML
	private TableColumn<ViewAluno, String> col_status;

	@FXML
	private JFXTextField nomeRua_listar;

	@FXML
	private JFXTextField numero_listar;

	@FXML
	private JFXTextField complemento_listar;

	@FXML
	private JFXTextField bairro_listar;

	@FXML
	private JFXTextField cidade_listar;

	@FXML
	private JFXTextField estado_listar;

	@FXML
	private JFXTextField email_listar;

	@FXML
	private JFXTextField telefone_listar;

	@FXML
	private JFXTextField celular_listar;

	@FXML
	private JFXCheckBox whatsapp_listar;

	@FXML
	private JFXTextField codResp;

	@FXML
	private JFXTextField nomeResp;

	@FXML
	private JFXTextField cpfResp;

	@FXML
	private JFXTextField dt_resp;

	@FXML
	private JFXTextField naturalidadeResp;

	@FXML
	private JFXTextField statusResp;

	@FXML
	private JFXTextField nomeRua_listar1;

	@FXML
	private JFXTextField numero_listar1;

	@FXML
	private JFXTextField complemento_listar1;

	@FXML
	private JFXTextField bairro_listar1;

	@FXML
	private JFXTextField cidade_listar1;

	@FXML
	private JFXTextField estado_listar1;

	@FXML
	private JFXTextField email_listar1;

	@FXML
	private JFXTextField telefone_listar1;

	@FXML
	private JFXTextField celular_listar1;

	@FXML
	private JFXCheckBox whatsapp_listar1;

	@FXML
	private Tab atualizarTab;

	@FXML
	private VBox vbox_atualizar;

	@FXML
	private Label codigo_listar;

	@FXML
	private JFXTextField nome_update;

	@FXML
	private JFXDatePicker dt_nascimento_update;

	@FXML
	private JFXTextField naturalidade_update;

	@FXML
	private JFXTextField cpf_update;

	@FXML
	private JFXTextField nomeMae_update;

	@FXML
	private JFXTextField nomePai_update;

	@FXML
	private JFXTextField nomeRua_update;

	@FXML
	private JFXTextField numero_update;

	@FXML
	private JFXTextField complemento_update;

	@FXML
	private JFXTextField bairro_update;

	@FXML
	private ComboBox<String> estado_update;

	@FXML
	private ComboBox<String> cidade_update;

	@FXML
	private JFXTextField email_update;

	@FXML
	private JFXTextField telefone_update;

	@FXML
	private JFXTextField celular_update;

	@FXML
	private JFXCheckBox whatsapp_update;

	@FXML
	private Tab turmasTab;

	@FXML
	private TableView<ViewTurma> table_turmas;

	@FXML
	private TableColumn<ViewTurma, Integer> col_codTurma;

	@FXML
	private TableColumn<ViewTurma, String> col_curriculo;

	@FXML
	private TableColumn<ViewTurma, String> col_ano;

	@FXML
	private TableColumn<ViewTurma, Integer> col_anoLetivo;

	@FXML
	private TableView<ViewAluno> table_pessoas2;

	@FXML
	private TableColumn<ViewAluno, Integer> col_codPessoa2;

	@FXML
	private TableColumn<ViewAluno, String> col_nome2;

	@FXML
	private TableColumn<ViewAluno, String> col_cpf2;

	@FXML
	private TableColumn<ViewAluno, Date> col_dt_nascimento2;

	@FXML
	private TableColumn<ViewAluno, String> col_naturalidade2;

	@FXML
	private TableColumn<ViewAluno, String> col_nomeMae2;

	@FXML
	private TableColumn<ViewAluno, String> col_nomePai2;

	@FXML
	private TableColumn<ViewAluno, String> col_status2;

	@FXML
	private TableColumn<ViewAluno, String> col_situacaoAluno;

	@FXML
	private Tab notasTab;

	@FXML
	private JFXTextField codAluno;

	@FXML
	private JFXTextField nomeAluno;

	@FXML
	private JFXTextField dt_aluno;

	@FXML
	private JFXTextField naturalidadeAluno;

	@FXML
	private JFXTextField statusAluno;

	@FXML
	private TableView<AlunoNota> table_notas;

	@FXML
	private TableColumn<AlunoNota, AlunoNotaID> col_disciplina;

	@FXML
	private TableColumn<AlunoNota, AlunoNotaID> col_anoNota;

	@FXML
	private TableColumn<AlunoNota, Float> col_media;

	@FXML
	private TableColumn<AlunoNota, AlunoNotaID> col_anoLetivoNota;

	@FXML
	private TableColumn<AlunoNota, String> col_situacao;

	@FXML
	private JFXTextField nota_txt;

	@FXML
	private JFXCheckBox finalCheck;

	@FXML
	private JFXButton btn_finalizar;

	private Aluno oldAluno;

	private Pessoa oldPessoa;

	private String oldCPF;

	private Endereco oldEndereco;

	private Contato oldContato;

	private FilteredList<ViewAluno> filteredData;

	private FilteredList<ViewTurma> filteredData2;

	private ObservableList<ViewAluno> oblist_pessoas = FXCollections.observableArrayList();

	private ObservableList<ViewTurma> oblist_turmas= FXCollections.observableArrayList();

	private AlunoTurma alunoAprovado;

	private AlunoTurma alunoReprovado;

	@FXML
	void atualizar(ActionEvent event) {
		if(checkTextFields()){
			for (int i = 0; i < oblist_pessoas.size(); i++) {
				String obCPF = oblist_pessoas.get(i).getCpf();

				if((obCPF != null && oldCPF != null) || obCPF != null){
					if(obCPF.equals(cpf_update.getText()) && !cpf_update.getText().equals(oldCPF)){
						Util.Alert("CPF já está cadastrado no sistema!");
						return;
					}

				}
			}

			Date date = Date.valueOf(dt_nascimento_update.getValue());
			Pessoa p = UtilDao.daoPessoa.getPessoa(Integer.valueOf(codigo_listar.getText()));
			Endereco e = new Endereco();
			Contato c = new Contato();

			p.setNome(nome_update.getText());
			p.setDt_nascimento(date);
			p.setNaturalidade(naturalidade_update.getText());
			p.setStatus("Ativo");
			if(cpf_update.getText().length() > 0)p.setCpf(cpf_update.getText());
			else p.setCpf(null);

			UtilDao.daoPessoa.UpdatePessoa(p);
			int cod = p.getCodPessoa();

			Aluno a = UtilDao.daoAlunos.getAluno(cod);
			a.setNomeMae(nomeMae_update.getText());
			a.setNomePai(nomePai_update.getText());
			UtilDao.daoAlunos.updateAluno(a);

			e.setCodPessoa(cod);
			e.setRua(nomeRua_update.getText());
			e.setNumero(Integer.valueOf(numero_update.getText().replaceAll("\\s+", "")));
			if(complemento_update.getText() != null && complemento_update.getText().length() > 0)e.setComplemento(complemento_update.getText());
			e.setBairro(bairro_update.getText());
			e.setEstado(estado_update.getSelectionModel().getSelectedItem());
			e.setCidade(cidade_update.getSelectionModel().getSelectedItem());


			UtilDao.daoEnderecos.UpdateEndereco(e);

			c.setCodPessoa(cod);
			if(email_update.getText().length() > 0)c.setEmail(email_update.getText());
			if(telefone_update.getText().length() > 0)c.setTelefone(telefone_update.getText());
			if(celular_update.getText().length() > 0)c.setCelular(celular_update.getText());
			if(whatsapp_update.isSelected()){
				c.setWhatsapp(1);
			}
			else{
				c.setWhatsapp(0);
			}

			if(email_update.getText().length() > 0 || telefone_update.getText().length() > 0 || celular_update.getText().length() > 0){
				UtilDao.daoContatos.UpdateContato(c);
			}

			Util.Alert("Cod: "+cod+"\nNome: "+p.getNome()+"\nAtualizado com sucesso!");

			if(!oldPessoa.SameAs(p)){
				SysLog.addLog(SysLog.updatePessoas("Dados", cod));
			}

			if(!oldEndereco.equals(e)){
				SysLog.addLog(SysLog.updatePessoas("Endereço", cod));
			}

			if(oldContato != null && !oldContato.equals(c)){
				SysLog.addLog(SysLog.updatePessoas("Contatos", cod));
			}

			if(!oldAluno.equals(a)){
				SysLog.addLog(SysLog.message("Atualizou dados do aluno cod:"+a.getCodPessoa()));
			}
			initTables();

		}
	}

	@FXML
	void finalizarAluno(ActionEvent event) {
		if(alunoAprovado != null){
			UtilDao.daoTurmas.updateAlunoTurma(alunoAprovado);
			Connection con = ConnectionReserva.createConnection();

			String codCurriculo = null;
			int anoLetivo = 0;
			int ano = 0;
			PreparedStatement stmt;
			try {
				stmt = con.prepareStatement("SELECT Turmas.codCurriculo, Turmas.anoLetivo, Turmas.ano FROM Turmas WHERE Turmas.codTurma = ?;");
				stmt.setInt(1, alunoAprovado.getId().getCodTurma());
				ResultSet getTurma = stmt.executeQuery();
				if(getTurma.next()){
					codCurriculo = getTurma.getString("codCurriculo");
					anoLetivo = getTurma.getInt("anoLetivo");
					ano = getTurma.getInt("ano");
				}

				stmt = con.prepareStatement("Select codTurma FROM Turmas WHERE codCurriculo = ? AND anoLetivo = ? AND ano = ?");
				stmt.setString(1, codCurriculo);
				stmt.setInt(2, anoLetivo+1);
				stmt.setInt(3, ano+1);

				ResultSet checkNovaTurma = stmt.executeQuery();
				if(!checkNovaTurma.next()){
					stmt = con.prepareStatement("SELECT DISTINCT `Curriculo-disciplina`.ano FROM "
							+ "`Curriculo-disciplina` WHERE `Curriculo-disciplina`.codCurriculo = ? ORDER BY ano DESC LIMIT 1");
					stmt.setString(1, codCurriculo);

					ResultSet rs = stmt.executeQuery();
					if(rs.next()){
						if(rs.getInt("ano") > ano+1){
							Turma novaTurma = new Turma();
							novaTurma.setCodCurriculo(codCurriculo);
							novaTurma.setAnoLetivo(anoLetivo+1);
							novaTurma.setAno(ano+1);
							int codNovaTurma = UtilDao.daoTurmas.addTurma(novaTurma);

							AlunoTurma aluno = new AlunoTurma();
							aluno.setId(new AlunoTurmaID(alunoAprovado.getId().getCodAluno(), codNovaTurma));
							aluno.setSituacao("Pendente");
							UtilDao.daoTurmas.addAlunoTurma(aluno);

							Util.Alert("O Aluno foi aprovado no currículo e foi cadastrado em uma nova turma!");
						}
					}

				}else{
					AlunoTurma aluno = new AlunoTurma();
					aluno.setId(new AlunoTurmaID(alunoAprovado.getId().getCodAluno(), checkNovaTurma.getInt("codTurma")));
					aluno.setSituacao("Pendente");
					UtilDao.daoTurmas.addAlunoTurma(aluno);

					Util.Alert("O Aluno foi aprovado no currículo e foi cadastrado em uma nova turma!");
				}


			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		else if(alunoReprovado != null){
			UtilDao.daoTurmas.updateAlunoTurma(alunoReprovado);
			Connection con = ConnectionReserva.createConnection();

			String codCurriculo = null;
			int anoLetivo = 0;
			int ano = 0;
			PreparedStatement stmt;
			try {
				stmt = con.prepareStatement("SELECT Turmas.codCurriculo, Turmas.anoLetivo, Turmas.ano FROM Turmas WHERE Turmas.codTurma = ?;");
				stmt.setInt(1, alunoReprovado.getId().getCodTurma());
				ResultSet getTurma = stmt.executeQuery();
				if(getTurma.next()){
					codCurriculo = getTurma.getString("codCurriculo");
					anoLetivo = getTurma.getInt("anoLetivo");
					ano = getTurma.getInt("ano");
				}

				stmt = con.prepareStatement("Select codTurma FROM Turmas WHERE codCurriculo = ? AND anoLetivo = ? AND ano = ?");
				stmt.setString(1, codCurriculo);
				stmt.setInt(2, anoLetivo+1);
				stmt.setInt(3, ano);

				ResultSet checkNovaTurma = stmt.executeQuery();
				if(!checkNovaTurma.next()){

					Turma novaTurma = new Turma();
					novaTurma.setCodCurriculo(codCurriculo);
					novaTurma.setAnoLetivo(anoLetivo+1);
					novaTurma.setAno(ano);
					int codNovaTurma = UtilDao.daoTurmas.addTurma(novaTurma);

					AlunoTurma aluno = new AlunoTurma();
					aluno.setId(new AlunoTurmaID(alunoReprovado.getId().getCodAluno(), codNovaTurma));
					aluno.setSituacao("Pendente");
					UtilDao.daoTurmas.addAlunoTurma(aluno);

					Util.Alert("O Aluno foi reprovado no currículo e foi cadastrado em uma nova turma!");


				}else{
					AlunoTurma aluno = new AlunoTurma();
					aluno.setId(new AlunoTurmaID(alunoReprovado.getId().getCodAluno(), checkNovaTurma.getInt("codTurma")));
					aluno.setSituacao("Pendente");
					UtilDao.daoTurmas.addAlunoTurma(aluno);

					Util.Alert("O Aluno foi aprovado no currículo e foi cadastrado em uma nova turma!");
				}


			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		else{
			Util.Alert("Ocorreu um erro!\nContate o administrador");
		}


		initTables();
	}

	@FXML
	void searchTurma(KeyEvent event){
		campoPesquisa2.textProperty().addListener((observableValue, oldValue, newValue)->{
			filteredData2.setPredicate(turma ->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(String.valueOf(turma.getCodTurma()).contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(turma.getAno()).contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(turma.getAnoLetivo()).contains(lowerCaseFilter)){
					return true;
				}
				else if(turma.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(turma.getCodCurriculo().contains(lowerCaseFilter)){
					return true;
				}
				else{
					return false;
				}
			});
		});
		SortedList<ViewTurma> sortedData = new SortedList<>(filteredData2);
		sortedData.comparatorProperty().bind(table_turmas.comparatorProperty());
		table_turmas.setItems(sortedData);
	}


	@FXML
	void searchPessoa(KeyEvent event) {
		campo_pesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData.setPredicate(pessoa->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(pessoa.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getNaturalidade().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(pessoa.getCodPessoa()).contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getStatus().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getDt_nascimento().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(lowerCaseFilter)){
					return true;
				}
				else if(pessoa.getCpf() != null){
					if(pessoa.getCpf().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
				}

				return false;
			});
		});
		SortedList<ViewAluno> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table_pessoas.comparatorProperty());
		table_pessoas.setItems(sortedData);
	}

	@FXML
	void selecionarPessoa(ActionEvent event) {
		Pessoa p = table_pessoas.getSelectionModel().getSelectedItem();
		if(p != null){
			oldPessoa = p;
			limpar(event);

			Aluno a = UtilDao.daoAlunos.getAluno(p.getCodPessoa());
			oldAluno = a;
			codigo_listar.setText(String.valueOf(p.getCodPessoa()));

			Endereco e = UtilDao.daoEnderecos.getEndereco(p.getCodPessoa());
			Contato c = UtilDao.daoContatos.getContato(p.getCodPessoa());

			nome_update.setText(p.getNome());
			naturalidade_update.setText(p.getNaturalidade());
			nomeMae_update.setText(a.getNomeMae());
			nomePai_update.setText(a.getNomePai());
			if(p.getCpf() != null){
				cpf_update.setText(p.getCpf());
				oldCPF = p.getCpf();
			}
			LocalDate dt = LocalDate.parse(p.getDt_nascimento().toLocalDate().toString());
			dt_nascimento_update.setValue(dt);

			nomeRua_update.setText(e.getRua());
			numero_update.setText(String.valueOf(e.getNumero()));
			complemento_update.setText(e.getComplemento());
			bairro_update.setText(e.getBairro());
			estado_update.getSelectionModel().select(e.getEstado());
			cidade_update.getSelectionModel().select(e.getCidade());

			oldEndereco = e;
			if(c != null){
				if(c.getEmail() != null)email_update.setText(c.getEmail());
				if(c.getTelefone() != null)telefone_update.setText(c.getTelefone());
				if(c.getCelular() != null)celular_update.setText(c.getCelular());
				if(c.getWhatsapp() == 1){
					whatsapp_update.setSelected(true);
				}else{
					whatsapp_update.setSelected(false);
				}

				oldContato = c;
			}

			enableAtualizar();

			SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

			selectionModel.select(atualizarTab);


		}else{
			Util.Alert("Selecione uma pessoa antes de atualizar!");
		}


	}

	@FXML
	void selecionarAluno(ActionEvent event) {

		int ano = table_turmas.getSelectionModel().getSelectedItem().getAno();
		int anoLetivo = table_turmas.getSelectionModel().getSelectedItem().getAnoLetivo();
		ViewAluno selected = table_pessoas2.getSelectionModel().getSelectedItem();

		if(selected != null && ano > 0){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			codAluno.setText(String.valueOf(selected.getCodPessoa()));nomeAluno.setText(String.valueOf(selected.getNome()));
			dt_aluno.setText(format.format(selected.getDt_nascimento()));naturalidadeAluno.setText(selected.getNaturalidade());
			statusAluno.setText(selected.getStatus());

			ObservableList<AlunoNota> oblist = FXCollections.observableArrayList();
			oblist = UtilDao.daoAlunos.getNotas(selected.getCodPessoa(), ano, anoLetivo);

			table_notas.setItems(oblist);

			tabPane.getSelectionModel().select(notasTab);
			btn_finalizar.setVisible(false);
		}
	}

	@FXML
	void alterarNota(ActionEvent event) {
		AlunoNota selected = table_notas.getSelectionModel().getSelectedItem();
		if(selected != null){
			nota_txt.requestFocus();
			try {
				if(Float.valueOf(nota_txt.getText()) > 10){
					nota_txt.setText(String.valueOf(10.0));
				}
				selected.setMedia(Float.valueOf(nota_txt.getText()));
			} catch (Exception e) {
				Util.Alert("Verifique o valor!");
				return;
			}

			if(finalCheck.isSelected())selected.setFoiFinal(1);

			UtilDao.daoAlunos.setAlunoNota(selected);
			table_notas.getItems().clear();
			table_notas.setItems(UtilDao.daoAlunos.getNotas(selected.getId().getCodAluno(), selected.getId().getSerie(), selected.getId().getAnoLetivo()));
			Util.Alert("Média atualizada!");

			boolean aprovadoCurriculo = false;
			boolean reprovadoCurriculo = false;
			for (AlunoNota nota : table_notas.getItems()) {
				if(nota.getSituacao().equals("AP") || nota.getSituacao().equals("AM")){
					aprovadoCurriculo = true;
				}
				else{
					aprovadoCurriculo = false;
				}

				if(nota.getSituacao().equals("RP")){
					aprovadoCurriculo = false;
					reprovadoCurriculo = true;
					break;
				}
			}

			if(aprovadoCurriculo){
				int codTurma = table_turmas.getSelectionModel().getSelectedItem().getCodTurma();
				if(codTurma > 0){
					btn_finalizar.setVisible(true);
					AlunoTurma at = UtilDao.daoTurmas.getAlunoTurma(new AlunoTurmaID(Integer.valueOf(codAluno.getText()), codTurma));
					at.setSituacao("Aprovado");
					alunoAprovado = at;
					alunoReprovado = null;
				}
			}
			else if(reprovadoCurriculo){
				int codTurma = table_turmas.getSelectionModel().getSelectedItem().getCodTurma();
				if(codTurma > 0){
					AlunoTurma at = new AlunoTurma();
					at.setId(new AlunoTurmaID(Integer.valueOf(codAluno.getText()), codTurma));
					at.setSituacao("Reprovado");
					alunoAprovado = null;
					alunoReprovado = at;

					for(AlunoNota nota: table_notas.getItems()){
						if(nota.getSituacao().equals("Pendente")){
							btn_finalizar.setVisible(false);
							break;
						}else{
							btn_finalizar.setVisible(true);
						}
					}
				}
			}

		}
		else{
			Util.Alert("Selecione uma nota!!");
		}

	}

	void initTables(){
		oblist_pessoas = UtilDao.daoAlunos.getAllAlunosView();
		oblist_turmas = UtilDao.daoTurmas.getAllTurmasView();

		if(oblist_pessoas != null)filteredData = new FilteredList<>(oblist_pessoas);
		if(oblist_turmas != null)filteredData2 = new FilteredList<>(oblist_turmas);

		//Pessoas
		col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_naturalidade.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		col_dt_nascimento.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));
		col_nomeMae.setCellValueFactory(new PropertyValueFactory<>("nomeMae"));
		col_nomePai.setCellValueFactory(new PropertyValueFactory<>("nomePai"));


		col_dt_nascimento.setCellFactory(column -> {
			TableCell<ViewAluno, Date> cell = new TableCell<ViewAluno, Date>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(format.format(item));
					}
				}
			};
			return cell;
		});

		table_pessoas.setItems(oblist_pessoas);

		table_pessoas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				ViewAluno selectedPessoa = table_pessoas.getSelectionModel().getSelectedItem();
				int cod = selectedPessoa.getCodPessoa();

				if(selectedPessoa != null){
					//Endereco
					Endereco end = UtilDao.daoEnderecos.getEndereco(cod);
					if(end != null){

						nomeRua_listar.setText(end.getRua());
						numero_listar.setText(String.valueOf(end.getNumero()));
						complemento_listar.clear();
						complemento_listar.setText(end.getComplemento());
						bairro_listar.setText(end.getBairro());
						cidade_listar.setText(end.getCidade());
						estado_listar.setText(end.getEstado());
					}
					//Contatos
					Contato con = UtilDao.daoContatos.getContato(cod);
					celular_listar.clear();email_listar.clear();telefone_listar.clear();
					if(con != null){
						if(con.getCelular() != null)celular_listar.setText(con.getCelular());
						if(con.getEmail() != null)email_listar.setText(con.getEmail());
						if(con.getTelefone() != null)telefone_listar.setText(con.getTelefone());
						if(con.getWhatsapp() == 1){
							whatsapp_listar.setSelected(true);
						}else{
							whatsapp_listar.setSelected(false);
						}
					}
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					ViewResponsavelFinanceiro resp = UtilDao.daoResponsaveis.getResponsavelFinanceiro(selectedPessoa.getCodResponsavelFin());

					codResp.setText(String.valueOf(resp.getCodPessoa()));nomeResp.setText(resp.getNome());
					cpfResp.setText(resp.getCpf());dt_resp.setText(format.format(resp.getDt_nascimento()));
					naturalidadeResp.setText(resp.getNaturalidade());statusResp.setText(resp.getStatus());

					Endereco respEnd = UtilDao.daoEnderecos.getEndereco(resp.getCodPessoa());
					nomeRua_listar1.setText(respEnd.getRua());
					numero_listar1.setText(String.valueOf(respEnd.getNumero()));
					complemento_listar1.clear();
					complemento_listar1.setText(respEnd.getComplemento());
					bairro_listar1.setText(respEnd.getBairro());
					cidade_listar1.setText(respEnd.getCidade());
					estado_listar1.setText(respEnd.getEstado());

					Contato conResp = UtilDao.daoContatos.getContato(resp.getCodPessoa());
					celular_listar1.clear();email_listar1.clear();telefone_listar1.clear();
					if(conResp != null){
						if(conResp.getCelular() != null)celular_listar1.setText(conResp.getCelular());
						if(conResp.getEmail() != null)email_listar1.setText(conResp.getEmail());
						if(conResp.getTelefone() != null)telefone_listar1.setText(conResp.getTelefone());
						if(conResp.getWhatsapp() == 1){
							whatsapp_listar1.setSelected(true);
						}else{
							whatsapp_listar1.setSelected(false);
						}
					}

				}

			}
		});

		col_codPessoa2.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_nome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf2.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_naturalidade2.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_status2.setCellValueFactory(new PropertyValueFactory<>("status"));
		col_dt_nascimento2.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));
		col_nomeMae2.setCellValueFactory(new PropertyValueFactory<>("nomeMae"));
		col_nomePai2.setCellValueFactory(new PropertyValueFactory<>("nomePai"));
		col_situacaoAluno.setCellValueFactory(new PropertyValueFactory<>("situacao"));


		col_dt_nascimento2.setCellFactory(column -> {
			TableCell<ViewAluno, Date> cell = new TableCell<ViewAluno, Date>() {
				private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(format.format(item));
					}
				}
			};
			return cell;
		});

		col_codTurma.setCellValueFactory(new PropertyValueFactory<>("codTurma"));
		col_curriculo.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_ano.setCellValueFactory(new PropertyValueFactory<>("ano"));
		col_anoLetivo.setCellValueFactory(new PropertyValueFactory<>("anoLetivo"));

		table_turmas.setItems(oblist_turmas);

		table_turmas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
			if(newSelection != null){
				table_pessoas2.getItems().clear();
				ViewTurma selected = table_turmas.getSelectionModel().getSelectedItem();
				ObservableList<ViewAluno> alunos = UtilDao.daoTurmas.getAlunos(selected.getCodTurma());
				table_pessoas2.setItems(alunos);
			}
		});

		col_disciplina.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
		col_anoNota.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_anoLetivoNota.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_media.setCellValueFactory(new PropertyValueFactory<>("media"));
		col_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

		table_notas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->{
			if(newSelection != null){
				nota_txt.setText(String.valueOf(newSelection.getMedia()));
			}
		});

		col_anoNota.setCellFactory(column -> {
			final TableCell<AlunoNota, AlunoNotaID> cell = new TableCell<AlunoNota, AlunoNotaID>(){

				@Override
				protected void updateItem(AlunoNotaID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getSerie())+"ª ano");
					}
				}
			};
			return cell;
		});

		col_anoLetivoNota.setCellFactory(column -> {
			final TableCell<AlunoNota, AlunoNotaID> cell = new TableCell<AlunoNota, AlunoNotaID>(){

				@Override
				protected void updateItem(AlunoNotaID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getAnoLetivo()));
					}
				}
			};
			return cell;
		});
	}

	public boolean checkTextFields(){

		if(atualizarTab.isSelected()){
			TextFieldFormatter tff = new TextFieldFormatter();

			if(nome_update.getText().length() == 0 || nome_update.getText() == null){
				Util.Alert("Verifique o nome!");
				return false;
			}

			if(cpf_update.getText().length() > 0){
				if(cpf_update.getText().length() < 11 || (cpf_update.getText().length() > 11 && cpf_update.getText().length() < 14)){
					Util.Alert("Verifique o CPF!");
					return false;
				}else{
					tff = new TextFieldFormatter();
					tff.setMask("###.###.###-##");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(cpf_update);
					tff.formatter();
				}
			}

			if(naturalidade_update.getText().length() < 1 || naturalidade_update.getText() == null){
				Util.Alert("Verifique a naturalidade!");
				return false;
			}

			if(dt_nascimento_update.getValue() == null){
				Util.Alert("Verifique a Data de nascimento!");
				return false;
			}

			if(nomeRua_update.getText().length() < 1){
				Util.Alert("Verifique o nome da rua!");
				return false;
			}

			if(numero_update.getText().length() < 1){
				Util.Alert("Digite o número da residência!");
				return false;
			}

			if(bairro_update.getText().length() < 1){
				Util.Alert("Digite o nome do bairro!");
				return false;
			}

			if(estado_update.getSelectionModel().getSelectedItem() == null){
				Util.Alert("Selecione o estado!");
				return false;
			}

			if(cidade_update.getSelectionModel().getSelectedItem() == null){
				Util.Alert("Selecione a cidade!");
				return false;
			}

			if(email_update.getText().length() > 0){
				if(!Util.validarEmail(email_update.getText())){
					Util.Alert("Verifique o email!");
					return false;
				}
			}


			if(celular_update.getText().length() > 0){
				if(celular_update.getText().length() < 11 || (celular_update.getText().length() > 11 && celular_update.getText().length() < 14)){
					Util.Alert("Verifique o número de celular!");
					return false;
				}else{
					tff.setMask("(##)#####-####");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(celular_update);
					tff.formatter();
				}
			}

			if(telefone_update.getText().length() > 0){
				if(telefone_update.getText().length() < 10 || (telefone_update.getText().length() > 10 && telefone_update.getText().length() < 13)){
					Util.Alert("Verifique o número de telefone!");
					return false;
				}else{
					tff.setMask("(##)####-####");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(telefone_update);
					tff.formatter();
				}
			}

			if(nomeMae_update.getText().isEmpty()){
				Util.Alert("Verifique o nome da mãe!");
				return false;
			}

			if(nomePai_update.getText().isEmpty()){
				Util.Alert("Verifique o nome do pai!");
				return false;
			}

		}

		return true;
	}



	@FXML
	void formatNumeroTxt(KeyEvent event) {
		if(atualizarTab.isSelected()){
			TextFieldFormatter tff = new TextFieldFormatter();
			tff.setMask("#####");
			tff.setCaracteresValidos("0123456789");
			tff.setTf(numero_update);
			tff.formatter();
		}
	}

	@FXML
	void limpar(ActionEvent event) {
		if(atualizarTab.isSelected()){
			nome_update.clear();dt_nascimento_update.setValue(null);naturalidade_update.clear();cpf_update.clear();
			nomeRua_update.clear();complemento_update.clear();numero_update.clear();email_update.clear();telefone_update.clear();
			celular_update.clear();whatsapp_update.setSelected(false);cidade_update.getSelectionModel().clearSelection();
			estado_update.getSelectionModel().clearSelection();bairro_update.clear();codigo_listar.setText("Código: ");
			disableAtualizar();
		}
	}

	@FXML
	void populateCidades(ActionEvent event) {
		if(atualizarTab.isSelected()){
			Util.populateCidade(estado_update, cidade_update);
		}
	}

	void populateBoxes(){
		estado_update.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");

		new AutoCompleteComboBoxListener<>(estado_update);
		new AutoCompleteComboBoxListener<>(cidade_update);
	}

	void disableAtualizar(){
		vbox_atualizar.setDisable(true);
	}

	void enableAtualizar(){
		vbox_atualizar.setDisable(false);
	}

	void formatMedia(){
		nota_txt.setText(nota_txt.getText().replaceAll(",", "."));
		nota_txt.setText(nota_txt.getText().replaceAll("\\s+", "."));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		atualizarTab.setOnSelectionChanged(e ->{
			if(atualizarTab.isSelected()){
				if(nome_update.getText().isEmpty()){
					Util.Alert("Selecione um aluno na aba \"Listar\"");
				}
			}

		});

		notasTab.setOnSelectionChanged(e ->{
			if(notasTab.isSelected()){
				if(codAluno.getText().isEmpty()){
					Util.Alert("Selecione um aluno na aba \"Turmas\"");
				}
			}

		});

		nota_txt.textProperty().addListener(
				(observable, old_value, new_value) -> {

					if(new_value.contains(" ")){
						nota_txt.setText(old_value);
					}
					if(new_value.length() > 3){
						nota_txt.setText(old_value);
					}

					formatMedia();
				}
				);

		initTables();
		populateBoxes();
	}

}
