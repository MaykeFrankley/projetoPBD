package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.json.simple.JSONObject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Aluno;
import br.com.Acad.model.AlunoTurma;
import br.com.Acad.model.AlunoTurmaID;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoDisciplina;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.ResponsavelFinanceiro;
import br.com.Acad.model.ResponsavelFinanceiroID;
import br.com.Acad.model.Turma;
import br.com.Acad.model.TurmaID;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.Settings;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class CadastrarAlunoController implements Initializable{

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private JFXTextField nome;

	@FXML
	private JFXDatePicker dt_nascimento;

	@FXML
	private JFXTextField naturalidade;

	@FXML
	private JFXTextField cpf;

	@FXML
	private JFXTextField nomeMae;

	@FXML
	private JFXTextField nomePai;

	@FXML
	private JFXCheckBox responsavelFin;

	@FXML
	private JFXTextField nomeRua;

	@FXML
	private JFXTextField numero;

	@FXML
	private JFXTextField complemento;

	@FXML
	private JFXTextField bairro;

	@FXML
	private ComboBox<String> estado;

	@FXML
	private ComboBox<String> cidade;

	@FXML
	private JFXTextField email;

	@FXML
	private JFXTextField telefone;

	@FXML
	private JFXTextField celular;

	@FXML
	private JFXCheckBox whatsapp;

	@FXML
	private JFXButton btn_proximo;

	@FXML
	private Tab tab_responsavel;

	@FXML
	private JFXTextField nome1;

	@FXML
	private JFXDatePicker dt_nascimento1;

	@FXML
	private JFXTextField naturalidade1;

	@FXML
	private JFXTextField cpf1;

	@FXML
	private JFXTextField nomeRua1;

	@FXML
	private JFXTextField numero1;

	@FXML
	private JFXTextField complemento1;

	@FXML
	private JFXTextField bairro1;

	@FXML
	private ComboBox<String> estado1;

	@FXML
	private ComboBox<String> cidade1;

	@FXML
	private JFXTextField email1;

	@FXML
	private JFXTextField telefone1;

	@FXML
	private JFXTextField celular1;

	@FXML
	private JFXCheckBox whatsapp1;

	@FXML
	private JFXButton btn_proximo1;

	@FXML
	private VBox box_cadastro;

	@FXML
	private VBox box_cadastroAluno;

	@FXML
	private VBox box_table;

	@FXML
	private VBox box_tableAluno;

	@FXML
	private VBox box_contatos;

	@FXML
	private TableView<ViewResponsavelFinanceiro> table_responsaveis;

	@FXML
	private TableColumn<ViewResponsavelFinanceiro, ResponsavelFinanceiroID> col_codPessoa;

	@FXML
	private TableColumn<ViewResponsavelFinanceiro, String> col_nome1;

	@FXML
	private TableColumn<ViewResponsavelFinanceiro, String> col_cpf;

	@FXML
	private TableColumn<ViewResponsavelFinanceiro, Date> col_dt_nascimento;

	@FXML
	private TableColumn<ViewResponsavelFinanceiro, String> col_naturalidade;

	@FXML
	private TableColumn<ViewResponsavelFinanceiro, String> col_status;

	@FXML
	private JFXTextField campo_pesquisa;

	@FXML
	private JFXTextField campo_pesquisa2;

	@FXML
	private TableView<ViewAluno> table_alunos;

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
	private TableColumn<ViewAluno, String> col_nomeMae;

	@FXML
	private TableColumn<ViewAluno, String> col_nomePai;

	@FXML
	private TableColumn<ViewAluno, String> col_status2;


	@FXML
	private Tab tab_grade;

	@FXML
	private TableView<Curriculo> table_curriculo;

	@FXML
	private TableColumn<Curriculo, String> col_cod;

	@FXML
	private TableColumn<Curriculo, String> col_nome;

	@FXML
	private TableColumn<Curriculo, String> col_tipo;

	@FXML
	private ComboBox<String> box_anoLetivo;

	private ObservableList<Curriculo> oblist_curriculos = FXCollections.observableArrayList();

	private ObservableList<ViewResponsavelFinanceiro> oblist_resp = FXCollections.observableArrayList();

	private ObservableList<ViewAluno> oblist_alunos = FXCollections.observableArrayList();

	private FilteredList<ViewResponsavelFinanceiro> filtered_resp;

	private FilteredList<ViewAluno> filtered_aluno;

	private int codAluno;

	private int codResponsavel;

	private JSONObject options = Settings.get();

	@FXML
	void confirmar(ActionEvent event) {
		Curriculo selectedCurriculo = table_curriculo.getSelectionModel().getSelectedItem();
		if(selectedCurriculo != null && codAluno == 0 && !box_anoLetivo.getSelectionModel().isEmpty()){
			Pessoa pessoaAluno = new Pessoa();
			Endereco alunoEnd = new Endereco();
			Contato alunoCont = new Contato();

			Pessoa responsavel = new Pessoa();
			Endereco responsavelEnd = new Endereco();
			Contato responsavelCont = new Contato();

			Aluno a = new Aluno();
			ResponsavelFinanceiro resp = new ResponsavelFinanceiro();

			// ALUNOPESSOA
			pessoaAluno.setNome(nome.getText());
			if(cpf.getText().length() > 0){
				pessoaAluno.setCpf(cpf.getText());
			}
			Date dateA = Date.valueOf(dt_nascimento.getValue());
			pessoaAluno.setDt_nascimento(dateA);
			pessoaAluno.setNaturalidade(naturalidade.getText());
			pessoaAluno.setStatus("Ativo");

			int cod = UtilDao.daoPessoa.addPessoa(pessoaAluno);
			SysLog.addLog(SysLog.createPessoas(cod));

			alunoEnd.setCodPessoa(cod);
			alunoEnd.setRua(nomeRua.getText());
			alunoEnd.setNumero(Integer.valueOf(numero.getText().replaceAll("\\s+", "")));
			if(complemento.getText() != null && complemento.getText().length() > 0)alunoEnd.setComplemento(complemento.getText());
			alunoEnd.setBairro(bairro.getText());
			alunoEnd.setEstado(estado.getSelectionModel().getSelectedItem());
			alunoEnd.setCidade(cidade.getSelectionModel().getSelectedItem());

			UtilDao.daoEnderecos.addEndereco(alunoEnd);
			SysLog.addLog(SysLog.createDados("Endereço", cod));

			alunoCont.setCodPessoa(cod);
			if(email.getText().length() > 0)alunoCont.setEmail(email.getText());
			if(telefone.getText().length() > 0)alunoCont.setTelefone(telefone.getText());
			if(celular.getText().length() > 0)alunoCont.setCelular(celular.getText());
			if(whatsapp.isSelected()){
				alunoCont.setWhatsapp(1);
			}
			else{
				alunoCont.setWhatsapp(0);
			}

			if(email.getText().length() > 0 || telefone.getText().length() > 0 || celular.getText().length() > 0){
				UtilDao.daoContatos.addContato(alunoCont);
				SysLog.addLog(SysLog.createDados("Contato", cod));
			}
			// END ALUNOPESSOA

			a.setCodPessoa(cod);
			if(!responsavelFin.isSelected()){
				if(codResponsavel > 0){
					a.setCodResponsavelFin(codResponsavel);
				}
				else{
					responsavel.setCpf(cpf1.getText());
					Date dateB = Date.valueOf(dt_nascimento1.getValue());
					responsavel.setDt_nascimento(dateB);
					responsavel.setNaturalidade(naturalidade1.getText());
					responsavel.setNome(nome1.getText());
					responsavel.setStatus("Ativo");

					int codResp = UtilDao.daoPessoa.addPessoa(responsavel);
					SysLog.addLog(SysLog.createPessoas(codResp));

					responsavelEnd.setCodPessoa(codResp);
					responsavelEnd.setRua(nomeRua1.getText());
					responsavelEnd.setNumero(Integer.valueOf(numero1.getText().replaceAll("\\s+", "")));
					if(complemento1.getText() != null && complemento1.getText().length() > 0)responsavelEnd.setComplemento(complemento1.getText());
					responsavelEnd.setBairro(bairro1.getText());
					responsavelEnd.setEstado(estado1.getSelectionModel().getSelectedItem());
					responsavelEnd.setCidade(cidade1.getSelectionModel().getSelectedItem());

					UtilDao.daoEnderecos.addEndereco(responsavelEnd);
					SysLog.addLog(SysLog.createDados("Endereço", codResp));
					a.setCodResponsavelFin(codResp);

					responsavelCont.setCodPessoa(codResp);
					if(email1.getText().length() > 0)responsavelCont.setEmail(email1.getText());
					if(telefone1.getText().length() > 0)responsavelCont.setTelefone(telefone1.getText());
					if(celular1.getText().length() > 0)responsavelCont.setCelular(celular1.getText());
					if(whatsapp1.isSelected()){
						responsavelCont.setWhatsapp(1);
					}
					else{
						responsavelCont.setWhatsapp(0);
					}

					if(email1.getText().length() > 0 || telefone1.getText().length() > 0 || celular1.getText().length() > 0){
						UtilDao.daoContatos.addContato(responsavelCont);
						SysLog.addLog(SysLog.createDados("Contato", codResp));
					}

					resp.setCpf(cpf1.getText());
					resp.setId(new ResponsavelFinanceiroID(codResp, a.getCodPessoa()));
					resp.setStatus("Ativo");

					UtilDao.daoResponsaveis.addResponsavel(resp);
					SysLog.addLog(SysLog.message("Cadastrou um novo responsável financeiro de cod:"+resp.getId().getCodPessoa()));

					gerarBoletos(resp);

				}
			}
			else{ //ALUNORESPONSAVEL
				a.setCodResponsavelFin(pessoaAluno.getCodPessoa());
				resp.setCpf(cpf.getText());
				resp.setId(new ResponsavelFinanceiroID(pessoaAluno.getCodPessoa(), pessoaAluno.getCodPessoa()));
				resp.setStatus("Ativo");

				UtilDao.daoResponsaveis.addResponsavel(resp);
				SysLog.addLog(SysLog.message("Cadastrou um novo responsável financeiro de cod:"+resp.getId().getCodPessoa()));

				gerarBoletos(resp);
			}
			a.setNomeMae(nomeMae.getText());
			a.setNomePai(nomePai.getText());
			a.setStatus("Ativo");

			UtilDao.daoAlunos.addAluno(a);
			SysLog.addLog(SysLog.message("Cadastrou um novo aluno de cod:"+a.getCodPessoa()));

			String[] alphabet = { "A", "B", "C", "D", "E", "F", "G",
					"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
					"U", "V", "W", "X", "Y", "Z" };

			ObservableList<String> ob_alfa = FXCollections.observableArrayList(alphabet);

			ObservableList<CurriculoDisciplina> disciplinas = UtilDao.daoCurriculo.getAllDisciplinas(selectedCurriculo.getCodCurriculo());
			ObservableList<Turma> turmas = UtilDao.daoTurmas.getAllTurmas();
			for (int i = 0; i < turmas.size(); i++) {
				Turma turma = turmas.get(i);

				if(turma.getCodCurriculo().equals(selectedCurriculo.getCodCurriculo())){
					if(turma.getAno() == disciplinas.get(0).getId().getAno() &&
							turma.getId().getAnoLetivo() == Integer.valueOf(box_anoLetivo.getSelectionModel().getSelectedItem())){
						ObservableList<ViewAluno> alunosDaTurma = UtilDao.daoTurmas.getAlunos(turma.getId().getCodTurma(), turma.getId().getAnoLetivo());
						if(alunosDaTurma.size() < (Long)options.get("maxAlunos")){
							AlunoTurma at = new AlunoTurma();
							at.setId(new AlunoTurmaID(a.getCodPessoa(), turma.getId().getCodTurma(), turma.getId().getAnoLetivo()));
							at.setSituacao("Pendente");
							UtilDao.daoTurmas.addAlunoTurma(at);
							SysLog.addLog(SysLog.message("adicionou automaticamente um aluno cod:"+at.getId().getCodAluno()+" na turma cod:"+turma.getId().getCodTurma()));

							codResponsavel = 0;
							initTable();
							limpar(event);

							return;
						}

						else{

							for (int j = i+1; j < turmas.size(); j++) {
								Turma tempTurma = turmas.get(j);
								int alunosSize = UtilDao.daoTurmas.getAlunos(tempTurma.getId().getCodTurma(), tempTurma.getId().getAnoLetivo()).size();
								if(tempTurma.getCodCurriculo().equals(turma.getCodCurriculo()) && alunosSize < (Long)options.get("maxAlunos")){
									AlunoTurma at = new AlunoTurma();
									at.setId(new AlunoTurmaID(a.getCodPessoa(), tempTurma.getId().getCodTurma(), tempTurma.getId().getAnoLetivo()));
									at.setSituacao("Pendente");
									UtilDao.daoTurmas.addAlunoTurma(at);
									SysLog.addLog(SysLog.message("adicionou automaticamente um aluno cod:"+at.getId().getCodAluno()+" na turma cod:"+tempTurma.getId().getCodTurma()));

									codResponsavel = 0;
									initTable();
									limpar(event);

									return;
								}
							}

							String letraTurma = turma.getId().getCodTurma().substring(turma.getId().getCodTurma().length() - 1);
							Turma novaTurma = new Turma();
							for (String letra : ob_alfa) {
								if(!letra.equals(letraTurma)){
									novaTurma.setId(new TurmaID(selectedCurriculo.getCodCurriculo()+"-"+letra, turma.getId().getAnoLetivo()));
									break;
								}
							}
							novaTurma.setCodCurriculo(selectedCurriculo.getCodCurriculo());
							novaTurma.setAno(disciplinas.get(0).getId().getAno());

							UtilDao.daoTurmas.addTurma(novaTurma);
							SysLog.addLog(SysLog.message("cadastrou automaticamente uma nova turma de cod:"+novaTurma.getId().getCodTurma()+
									" para o curriculo:"+novaTurma.getCodCurriculo()+
									" ano/Série: "+novaTurma.getAno()+" e ano letivo:"+novaTurma.getId().getAnoLetivo()));

							AlunoTurma at = new AlunoTurma();
							at.setId(new AlunoTurmaID(a.getCodPessoa(), novaTurma.getId().getCodTurma(), novaTurma.getId().getAnoLetivo()));
							at.setSituacao("Pendente");
							UtilDao.daoTurmas.addAlunoTurma(at);
							SysLog.addLog(SysLog.message("adicionou automaticamente um aluno cod:"+at.getId().getCodAluno()+" na turma cod:"+novaTurma.getId().getCodTurma()));

							codResponsavel = 0;
							initTable();
							limpar(event);
							Util.Alert("Aluno cadastrado com sucesso!");
							return;
						}
					}
				}
			}
			Turma turma = new Turma();
			turma.setId(new TurmaID(selectedCurriculo.getCodCurriculo()+"-A", Integer.valueOf(box_anoLetivo.getSelectionModel().getSelectedItem())));
			turma.setCodCurriculo(selectedCurriculo.getCodCurriculo());
			turma.setAno(disciplinas.get(0).getId().getAno());

			UtilDao.daoTurmas.addTurma(turma);
			SysLog.addLog(SysLog.message("cadastrou automaticamente uma nova turma de cod:"+turma.getId().getCodTurma()+
					" para o curriculo:"+turma.getCodCurriculo()+
					" ano/Série: "+turma.getAno()+" e ano letivo:"+turma.getId().getAnoLetivo()));

			AlunoTurma at = new AlunoTurma();
			at.setId(new AlunoTurmaID(a.getCodPessoa(), turma.getId().getCodTurma(), turma.getId().getAnoLetivo()));
			at.setSituacao("Pendente");
			UtilDao.daoTurmas.addAlunoTurma(at);
			SysLog.addLog(SysLog.message("adicionou automaticamente um aluno cod:"+at.getId().getCodAluno()+" na turma cod:"+turma.getId().getCodTurma()));

			codResponsavel = 0;
			initTable();
			limpar(event);
			Util.Alert("Aluno cadastrado com sucesso!");

		}
		else if(selectedCurriculo != null && codAluno > 0 && !box_anoLetivo.getSelectionModel().isEmpty()){
			ObservableList<CurriculoDisciplina> disciplinas = UtilDao.daoCurriculo.getAllDisciplinas(selectedCurriculo.getCodCurriculo());
			ObservableList<Turma> turmas = UtilDao.daoTurmas.getAllTurmas();
			for (int i = 0; i < turmas.size(); i++) {
				Turma turma = turmas.get(i);

				if(turma.getCodCurriculo().equals(selectedCurriculo.getCodCurriculo())){
					if(turma.getAno() == disciplinas.get(0).getId().getAno()){
						ObservableList<ViewAluno> alunosDaTurma = UtilDao.daoTurmas.getAlunos(turma.getId().getCodTurma(), turma.getId().getAnoLetivo());
						if(alunosDaTurma.size() < (Long)options.get("maxAlunos")){
							AlunoTurma at = new AlunoTurma();
							at.setId(new AlunoTurmaID(codAluno, turma.getId().getCodTurma(), turma.getId().getAnoLetivo()));
							at.setSituacao("Pendente");
							UtilDao.daoTurmas.addAlunoTurma(at);
							SysLog.addLog(SysLog.message("adicionou automaticamente um aluno cod:"+at.getId().getCodAluno()+" na turma cod:"+turma.getId().getCodTurma()));

							codResponsavel = 0;
							codAluno = 0;
							initTable();
							return;
						}
						else{
							String[] alphabet = { "A", "B", "C", "D", "E", "F", "G",
									"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
									"U", "V", "W", "X", "Y", "Z" };

							String letraTurma = turma.getId().getCodTurma().substring(turma.getId().getCodTurma().length() - 1);

							ObservableList<String> ob_alfa = FXCollections.observableArrayList(alphabet);

							Turma novaTurma = new Turma();
							for (String letra : ob_alfa) {
								if(!letra.equals(letraTurma)){
									novaTurma.setId(new TurmaID(selectedCurriculo.getCodCurriculo()+"-"+letra, turma.getId().getAnoLetivo()));
								}
							}
							novaTurma.setCodCurriculo(selectedCurriculo.getCodCurriculo());
							novaTurma.setAno(disciplinas.get(0).getId().getAno());

							UtilDao.daoTurmas.addTurma(novaTurma);
							SysLog.addLog(SysLog.message("cadastrou automaticamente uma nova turma de cod:"+novaTurma.getId().getCodTurma()+
									" para o curriculo:"+novaTurma.getCodCurriculo()+
									" ano/Série: "+novaTurma.getAno()+" e ano letivo:"+novaTurma.getId().getAnoLetivo()));

							AlunoTurma at = new AlunoTurma();
							at.setId(new AlunoTurmaID(codAluno, novaTurma.getId().getCodTurma(), novaTurma.getId().getAnoLetivo()));
							at.setSituacao("Pendente");
							UtilDao.daoTurmas.addAlunoTurma(at);
							SysLog.addLog(SysLog.message("adicionou automaticamente um aluno cod:"+at.getId().getCodAluno()+" na turma cod:"+novaTurma.getId().getCodTurma()));

							codResponsavel = 0;
							codAluno = 0;
							initTable();
							limpar(event);
							Util.Alert("Aluno cadastrado com sucesso!");
							return;
						}
					}
				}
			}
			Turma turma = new Turma();
			turma.setId(new TurmaID(selectedCurriculo.getCodCurriculo()+"-A", Integer.valueOf(box_anoLetivo.getSelectionModel().getSelectedItem())));
			turma.setCodCurriculo(selectedCurriculo.getCodCurriculo());
			turma.setAno(disciplinas.get(0).getId().getAno());

			UtilDao.daoTurmas.addTurma(turma);
			SysLog.addLog(SysLog.message("cadastrou automaticamente uma nova turma de cod:"+turma.getId().getCodTurma()+
					" para o curriculo:"+turma.getCodCurriculo()+
					" ano/Série: "+turma.getAno()+" e ano letivo:"+turma.getId().getAnoLetivo()));

			AlunoTurma at = new AlunoTurma();
			at.setId(new AlunoTurmaID(codAluno, turma.getId().getCodTurma(), turma.getId().getAnoLetivo()));
			at.setSituacao("Pendente");
			UtilDao.daoTurmas.addAlunoTurma(at);
			SysLog.addLog(SysLog.message("adicionou automaticamente um aluno cod:"+at.getId().getCodAluno()+" na turma cod:"+turma.getId().getCodTurma()));

			codResponsavel = 0;
			codAluno = 0;
			initTable();
			limpar(event);
			Util.Alert("Aluno cadastrado com sucesso!");
		}
	}

	@FXML
	void formatNumeroTxt(KeyEvent event) {
		TextFieldFormatter tff = new TextFieldFormatter();
		tff.setMask("#####");
		tff.setCaracteresValidos("0123456789");
		tff.setTf(numero);
		tff.formatter();
	}

	@FXML
	void formatNumeroTxt2(KeyEvent event) {
		TextFieldFormatter tff = new TextFieldFormatter();
		tff.setMask("#####");
		tff.setCaracteresValidos("0123456789");
		tff.setTf(numero1);
		tff.formatter();
	}

	@FXML
	void limpar(ActionEvent event) {
		nome.clear();cpf.clear();nome1.clear();cpf1.clear();
		dt_nascimento.setValue(null);naturalidade.clear();
		nomeRua.clear();complemento.clear();numero.clear();
		whatsapp.setSelected(false);cidade.getSelectionModel().clearSelection();cidade.getEditor().clear();
		estado.getSelectionModel().clearSelection();estado.getEditor().clear();bairro.clear();
		nomePai.clear();nomeMae.clear();responsavelFin.setSelected(false);celular.clear();telefone.clear();email.clear();
		responsavelFin.setVisible(false);box_contatos.setVisible(false);estado.getItems().clear();cidade.getItems().clear();
	}

	void populateBoxes(){
		estado.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");
		estado1.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");

		box_anoLetivo.getItems().addAll(String.valueOf(LocalDate.now().getYear()), String.valueOf(LocalDate.now().getYear()+1));

		new AutoCompleteComboBoxListener<>(estado);
		new AutoCompleteComboBoxListener<>(cidade);
		new AutoCompleteComboBoxListener<>(estado1);
		new AutoCompleteComboBoxListener<>(cidade1);
	}

	@FXML
	void populateCidades(ActionEvent event) {
		Util.populateCidade(estado, cidade);
	}

	@FXML
	void populateCidades1(ActionEvent event) {
		Util.populateCidade(estado1, cidade1);
	}

	@FXML
	void searchAluno(KeyEvent event) {
		campo_pesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
			filtered_aluno.setPredicate(viewAluno->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(viewAluno.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(viewAluno.getNaturalidade().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(String.valueOf(viewAluno.getCodPessoa()).contains(lowerCaseFilter)){
					return true;
				}
				else if(viewAluno.getStatus().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(viewAluno.getDt_nascimento().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(lowerCaseFilter)){
					return true;
				}
				else if(viewAluno.getCpf() != null){
					if(viewAluno.getCpf().toLowerCase().contains(lowerCaseFilter)){
						return true;
					}
				}
				else if(viewAluno.getNomeMae().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(viewAluno.getNomePai().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				return false;
			});
		});
		SortedList<ViewAluno> sortedData = new SortedList<>(filtered_aluno);
		sortedData.comparatorProperty().bind(table_alunos.comparatorProperty());
		table_alunos.setItems(sortedData);
	}

	@FXML
	void searchResponsavel(KeyEvent event) {
		campo_pesquisa2.textProperty().addListener((observableValue, oldValue,newValue)->{
			filtered_resp.setPredicate(pessoa->{
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
		SortedList<ViewResponsavelFinanceiro> sortedData = new SortedList<>(filtered_resp);
		sortedData.comparatorProperty().bind(table_responsaveis.comparatorProperty());
		table_responsaveis.setItems(sortedData);
	}


	@FXML
	void proximo(ActionEvent event) {
		if(event.getSource() == btn_proximo){
			if(checkTextFields()){
				ObservableList<Pessoa> oblist = UtilDao.daoPessoa.getAllPessoa();
				for (int i = 0; i < oblist.size(); i++) {
					String obCPF = oblist.get(i).getCpf();

					if(cpf.getText().length() > 10 && obCPF != null && obCPF.equals(cpf.getText())){
						Util.Alert("CPF já está cadastrado no sistema!");
						return;
					}

				}
				if(responsavelFin.isSelected()){
					tab_grade.setDisable(false);
					tab_responsavel.setDisable(true);
					tabPane.getSelectionModel().select(tab_grade);
				}
				else{
					tab_responsavel.setDisable(false);
					tabPane.getSelectionModel().select(tab_responsavel);
				}
			}
		}
		else{
			ViewAluno selected = table_alunos.getSelectionModel().getSelectedItem();
			if(selected != null){
				codAluno = selected.getCodPessoa();
				tab_grade.setDisable(false);
				tab_responsavel.setDisable(true);
				tabPane.getSelectionModel().select(tab_grade);
			}
		}
	}

	@FXML
	void proximo2(ActionEvent event){
		if(event.getSource() == btn_proximo1){
			if(checkTextFields2()){
				ObservableList<Pessoa> oblist = UtilDao.daoPessoa.getAllPessoa();
				for (int i = 0; i < oblist.size(); i++) {
					String obCPF = oblist.get(i).getCpf();

					if(cpf1.getText().length() > 10 && obCPF != null && obCPF.equals(cpf1.getText())){
						Util.Alert("CPF já está cadastrado no sistema!");
						return;
					}

				}
				tab_grade.setDisable(false);
				tabPane.getSelectionModel().select(tab_grade);
			}
		}
		else{
			ViewResponsavelFinanceiro select = table_responsaveis.getSelectionModel().getSelectedItem();
			if(select != null){
				codResponsavel = select.getCodPessoa();
				tab_grade.setDisable(false);
				tabPane.getSelectionModel().select(tab_grade);
			}
		}
	}

	@FXML
	void setContatoVisible(ActionEvent event) {
		if(responsavelFin.isSelected()){
			box_contatos.setVisible(true);
		}
		else {
			box_contatos.setVisible(false);
		}
	}

	@FXML
	void selecionarAluno(ActionEvent event) {
		box_cadastroAluno.setVisible(false);
		box_tableAluno.setVisible(true);
	}

	@FXML
	void cadastrarAluno(ActionEvent event) {
		box_cadastroAluno.setVisible(true);
		box_tableAluno.setVisible(false);
	}


	@FXML
	void selecionarResponsavel(ActionEvent event) {
		box_cadastro.setVisible(false);
		box_table.setVisible(true);
	}

	@FXML
	void cadastrarResponsavel(ActionEvent event) {
		box_cadastro.setVisible(true);
		box_table.setVisible(false);
	}

	void initTable(){
		oblist_curriculos = UtilDao.daoCurriculo.getAllCurriculo();
		oblist_resp = UtilDao.daoResponsaveis.getAllViewResponsavel();
		oblist_alunos = UtilDao.daoAlunos.getAllAlunosView();

		filtered_resp = new FilteredList<>(oblist_resp);
		filtered_aluno = new FilteredList<>(oblist_alunos);

		col_cod.setCellValueFactory(new PropertyValueFactory<>("codCurriculo"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		table_curriculo.setItems(oblist_curriculos);

		col_nome1.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_naturalidade.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_dt_nascimento.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));

		col_dt_nascimento.setCellFactory(column -> {
			TableCell<ViewResponsavelFinanceiro, Date> cell = new TableCell<ViewResponsavelFinanceiro, Date>() {
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

		table_responsaveis.setItems(oblist_resp);

		col_nome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_cpf2.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_naturalidade2.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_status2.setCellValueFactory(new PropertyValueFactory<>("status"));
		col_codPessoa2.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_dt_nascimento2.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));
		col_nomeMae.setCellValueFactory(new PropertyValueFactory<>("nomeMae"));
		col_nomePai.setCellValueFactory(new PropertyValueFactory<>("nomePai"));

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

		table_alunos.setItems(oblist_alunos);


	}

	///// Geração de boletos /////////

	void gerarBoletos(ResponsavelFinanceiro resp){

		//		for (int i = 2; i < 12; i++) {
		//			LocalDate hj = LocalDate.now();
		//			Datas datas = Datas.novasDatas()
		//					.comDocumento(hj.getDayOfMonth(), hj.getMonthValue(), hj.getYear())
		//					.comProcessamento(hj.getDayOfMonth(), hj.getMonthValue(), hj.getYear())
		//					.comVencimento(hj.getDayOfMonth(), i, hj.getYear());
		//
		//			Endereco end = UtilDao.daoEnderecos.getEndereco(resp.getId().getCodPessoa());
		//			br.com.caelum.stella.boleto.Endereco enderecoBeneficiario = br.com.caelum.stella.boleto.Endereco.novoEndereco()
		//					.comBairro(end.getBairro())
		//					.comCidade(end.getCidade())
		//					.comUf(Util.getUF(end.getEstado()))
		//					.comLogradouro(end.getRua()+", "+end.getNumero());
		//
		//		}
	}

	boolean checkTextFields2(){

		TextFieldFormatter tff = new TextFieldFormatter();

		if(nome1.getText().length() == 0 || nome.getText() == null){
			Util.Alert("Verifique o nome!");
			return false;
		}

		if(cpf1.getText().length() < 11 || (cpf1.getText().length() > 11 && cpf1.getText().length() < 14)){
			Util.Alert("Verifique o CPF!");
			return false;
		}else{
			tff = new TextFieldFormatter();
			tff.setMask("###.###.###-##");
			tff.setCaracteresValidos("0123456789");
			tff.setTf(cpf1);
			tff.formatter();
		}

		if(naturalidade1.getText().length() < 5 || naturalidade1.getText() == null){
			Util.Alert("Verifique a naturalidade!");
			return false;
		}

		if(dt_nascimento1.getValue() == null){
			Util.Alert("Verifique a Data de nascimento!");
			return false;
		}

		if(nomeRua1.getText().length() < 5){
			Util.Alert("Verifique o nome da rua!");
			return false;
		}

		if(numero1.getText().length() < 1){
			Util.Alert("Digite o número da residência!");
			return false;
		}

		if(bairro1.getText().length() < 1){
			Util.Alert("Digite o nome do bairro!");
			return false;
		}

		if(estado1.getSelectionModel().getSelectedItem() == null){
			Util.Alert("Selecione o estado!");
			return false;
		}
		if(cidade1.getSelectionModel().getSelectedItem() == null){
			Util.Alert("Selecione a cidade!");
			return false;
		}

		if(email1.getText().length() > 0){
			if(!Util.validarEmail(email1.getText())){
				Util.Alert("Verifique o email.\nPs: Email não é obrigatório no cadastro!");
				return false;
			}
		}

		if(celular1.getText().length() < 11 || (celular1.getText().length() > 11 && celular1.getText().length() < 14)){
			Util.Alert("Verifique o número de celular!");
			return false;
		}else{
			tff.setMask("(##)#####-####");
			tff.setCaracteresValidos("0123456789");
			tff.setTf(celular1);
			tff.formatter();
		}

		if(telefone1.getText().length() > 0){
			if(telefone1.getText().length() < 10 || (telefone1.getText().length() > 10 && telefone1.getText().length() < 13)){
				Util.Alert("Verifique o número de telefone.\nPs: telefone não é obrigatório no cadastro!");
				return false;
			}else{
				tff.setMask("(##)####-####");
				tff.setCaracteresValidos("0123456789");
				tff.setTf(telefone1);
				tff.formatter();
			}
		}

		return true;
	}

	boolean checkTextFields(){

		TextFieldFormatter tff = new TextFieldFormatter();

		if(nome.getText().length() == 0 || nome.getText() == null){
			Util.Alert("Verifique o nome!");
			return false;
		}

		if(cpf.getText().length() > 0){
			if(cpf.getText().length() < 11 || (cpf.getText().length() > 11 && cpf.getText().length() < 14)){
				Util.Alert("Verifique o CPF!");
				return false;
			}else{
				tff = new TextFieldFormatter();
				tff.setMask("###.###.###-##");
				tff.setCaracteresValidos("0123456789");
				tff.setTf(cpf);
				tff.formatter();
			}
		}

		if(responsavelFin.isSelected()){
			if(cpf.getText().isEmpty() || cpf.getText().length() > 11 && cpf.getText().length() < 14){
				Util.Alert("Verifique o CPF!");
				return false;
			}else{
				tff = new TextFieldFormatter();
				tff.setMask("###.###.###-##");
				tff.setCaracteresValidos("0123456789");
				tff.setTf(cpf);
				tff.formatter();
			}
		}

		if(naturalidade.getText().length() < 5 || naturalidade.getText() == null){
			Util.Alert("Verifique a naturalidade!");
			return false;
		}

		if(dt_nascimento.getValue() == null){
			Util.Alert("Verifique a Data de nascimento!");
			return false;
		}

		if(nomeRua.getText().length() < 5){
			Util.Alert("Verifique o nome da rua!");
			return false;
		}

		if(numero.getText().length() < 1){
			Util.Alert("Digite o número da residência!");
			return false;
		}

		if(bairro.getText().length() < 1){
			Util.Alert("Digite o nome do bairro!");
			return false;
		}

		if(estado.getSelectionModel().getSelectedItem() == null){
			Util.Alert("Selecione o estado!");
			return false;
		}
		if(cidade.getSelectionModel().getSelectedItem() == null){
			Util.Alert("Selecione a cidade!");
			return false;
		}

		if(email.getText().length() > 0){
			if(!Util.validarEmail(email.getText())){
				Util.Alert("Verifique o email.\nPs: Email não é obrigatório no cadastro!");
				return false;
			}
		}


		if(responsavelFin.isSelected()){
			if(celular.getText().length() < 11 || (celular.getText().length() > 11 && celular.getText().length() < 14)){
				Util.Alert("Verifique o número de celular!");
				return false;
			}else{
				tff.setMask("(##)#####-####");
				tff.setCaracteresValidos("0123456789");
				tff.setTf(celular);
				tff.formatter();
			}
		}

		if(telefone.getText().length() > 0){
			if(telefone.getText().length() < 10 || (telefone.getText().length() > 10 && telefone.getText().length() < 13)){
				Util.Alert("Verifique o número de telefone.\nPs: telefone não é obrigatório no cadastro!");
				return false;
			}else{
				tff.setMask("(##)####-####");
				tff.setCaracteresValidos("0123456789");
				tff.setTf(telefone);
				tff.formatter();
			}
		}

		if(nomeMae.getText().isEmpty()){
			Util.Alert("Verifique o nome da mãe!");
			return false;
		}

		if(nomePai.getText().isEmpty()){
			Util.Alert("Verifique o nome do pai!");
			return false;
		}

		return true;
	}

	@FXML
	void checkIdade(ActionEvent event) {
		LocalDate now = LocalDate.now();
		if(dt_nascimento.getValue() != null){
			Period diff = Period.between(dt_nascimento.getValue(), now);
			if(diff.getYears() >= 18){
				responsavelFin.setVisible(true);
			}else{
				responsavelFin.setVisible(false);
			}
		}
	}

	void initValidation(){
		Util.requiredFieldSet(nome, naturalidade, nomeMae, nomePai, nomeRua, numero);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();
		initValidation();
		populateBoxes();
	}

}
