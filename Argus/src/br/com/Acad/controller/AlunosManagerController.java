package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Aluno;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import br.com.Acad.model.ViewTurma;
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
	private JFXTextArea dadosResponsavel;

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

    private Aluno oldAluno;

	private Pessoa oldPessoa;

	private String oldCPF;

	private Endereco oldEndereco;

	private Contato oldContato;

	private FilteredList<ViewAluno> filteredData;

	private FilteredList<ViewTurma> filteredData2;

	private ObservableList<ViewAluno> oblist_pessoas = FXCollections.observableArrayList();

	private ObservableList<ViewTurma> oblist_turmas= FXCollections.observableArrayList();

	@FXML
	void atualizar(ActionEvent event) {
		if(checkTextFields()){
			for (int i = 0; i < oblist_pessoas.size(); i++) {
				String obCPF = oblist_pessoas.get(i).getCpf();

				if((obCPF != null && oldCPF != null) || obCPF != null){
					if(obCPF.equals(cpf_update.getText()) && !cpf_update.getText().equals(oldCPF)){
						Util.Alert("CPF j� est� cadastrado no sistema!");
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
				SysLog.addLog(SysLog.updatePessoas("Endere�o", cod));
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
			estado_update.getSelectionModel().clearSelection();bairro_update.clear();codigo_listar.setText("C�digo: ");
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
		estado_update.getItems().addAll("Acre","Alagoas","Amap�","Amazonas","Bahia","Cear�","Distrito Federal",
				"Esp�rito Santo","Goi�s","Maranh�o","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Par�","Para�ba",
				"Paran�","Pernambuco","Piau�","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rond�nia",
				"Roraima","Santa Catarina","S�o Paulo","Sergipe","Tocantins");

		new AutoCompleteComboBoxListener<>(estado_update);
		new AutoCompleteComboBoxListener<>(cidade_update);
	}

	void disableAtualizar(){
		vbox_atualizar.setDisable(true);
	}

	void enableAtualizar(){
		vbox_atualizar.setDisable(false);
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
					dadosResponsavel.setText("C�digo:"+resp.getCodPessoa()+"            "+"Nome:"+resp.getNome()+"            "+"CPF:"+resp.getCpf()+"            "+"Data de nascimento:"+format.format(resp.getDt_nascimento())+"            "+
					"Naturalidade:"+resp.getNaturalidade()+"            "+"Status:"+resp.getStatus());

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
				Util.Alert("Digite o n�mero da resid�ncia!");
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
					Util.Alert("Verifique o n�mero de celular!");
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
					Util.Alert("Verifique o n�mero de telefone!");
					return false;
				}else{
					tff.setMask("(##)####-####");
					tff.setCaracteresValidos("0123456789");
					tff.setTf(telefone_update);
					tff.formatter();
				}
			}

			if(nomeMae_update.getText().isEmpty()){
				Util.Alert("Verifique o nome da m�e!");
				return false;
			}

			if(nomePai_update.getText().isEmpty()){
				Util.Alert("Verifique o nome do pai!");
				return false;
			}

		}

		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		atualizarTab.setOnSelectionChanged(e ->{
			if(atualizarTab.isSelected()){
				if(nome_update.getText().isEmpty()){
					Util.Alert("Selecione um professor na aba \"Listar\"");
				}
			}

		});

		initTables();
		populateBoxes();
	}

}
