package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Aluno;
import br.com.Acad.model.Contato;
import br.com.Acad.model.Curriculo;
import br.com.Acad.model.CurriculoID;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.ResponsavelFinanceiro;
import br.com.Acad.model.ResponsavelFinanceiroID;
import br.com.Acad.model.ViewResponsavelFinanceiro;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.TextFieldFormatter;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private VBox box_table;

    @FXML
    private VBox box_contatos;

    @FXML
    private TableView<ViewResponsavelFinanceiro> table_pessoas;

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
    private Tab tab_grade;

    @FXML
    private TableView<Curriculo> table_curriculo;

    @FXML
    private TableColumn<Curriculo, CurriculoID> col_cod;

    @FXML
    private TableColumn<Curriculo, String> col_nome;

    @FXML
    private TableColumn<Curriculo, CurriculoID> col_anoLetivo;

    @FXML
    private TableColumn<Curriculo, CurriculoID> col_tipo;

    private ObservableList<Curriculo> oblist_curriculos = FXCollections.observableArrayList();

    private ObservableList<ViewResponsavelFinanceiro> oblist_resp = FXCollections.observableArrayList();

    private int codResponsavel;

    @FXML
    void confirmar(ActionEvent event) {
    	Curriculo selectedCurriculo = table_curriculo.getSelectionModel().getSelectedItem();
    	if(selectedCurriculo != null){
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

	    	int cod = UtilDao.persist(pessoaAluno);

	    	alunoEnd.setCodPessoa(cod);
	    	alunoEnd.setRua(nomeRua.getText());
	    	alunoEnd.setNumero(Integer.valueOf(numero.getText().replaceAll("\\s+", "")));
			if(complemento.getText() != null && complemento.getText().length() > 0)alunoEnd.setComplemento(complemento.getText());
			alunoEnd.setBairro(bairro.getText());
			alunoEnd.setEstado(estado.getSelectionModel().getSelectedItem());
			alunoEnd.setCidade(cidade.getSelectionModel().getSelectedItem());

			UtilDao.persist(alunoEnd);

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
				UtilDao.persist(alunoCont);
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

	    			int codResp = UtilDao.persist(responsavel);

	    			responsavelEnd.setCodPessoa(codResp);
	    	    	responsavelEnd.setRua(nomeRua1.getText());
	    	    	responsavelEnd.setNumero(Integer.valueOf(numero1.getText().replaceAll("\\s+", "")));
	    			if(complemento1.getText() != null && complemento1.getText().length() > 0)responsavelEnd.setComplemento(complemento1.getText());
	    			responsavelEnd.setBairro(bairro1.getText());
	    			responsavelEnd.setEstado(estado1.getSelectionModel().getSelectedItem());
	    			responsavelEnd.setCidade(cidade1.getSelectionModel().getSelectedItem());

	    			UtilDao.persist(responsavelEnd);
	    			a.setCodResponsavelFin(codResp);

	    			responsavelCont.setCodPessoa(cod);
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
	    				UtilDao.persist(responsavelCont);
	    			}

	    			resp.setCpf(cpf1.getText());
	    			resp.setId(new ResponsavelFinanceiroID(codResp, a.getCodPessoa()));
	    			resp.setStatus("Ativo");

	    			UtilDao.persist(resp);
	    		}
	    	}
	    	else{ //ALUNORESPONSAVEL
	    		a.setCodResponsavelFin(pessoaAluno.getCodPessoa());
				resp.setCpf(cpf.getText());
				resp.setId(new ResponsavelFinanceiroID(pessoaAluno.getCodPessoa(), pessoaAluno.getCodPessoa()));
				resp.setStatus("Ativo");

				UtilDao.persist(resp);
	    	}
	    	a.setNomeMae(nomeMae.getText());
	    	a.setNomePai(nomePai.getText());
	    	a.setStatus("Ativo");

	    	UtilDao.persist(a);

	    	codResponsavel = 0;
	    	initTable();

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
    	nome.clear();cpf.clear();
		dt_nascimento.setValue(null);naturalidade.clear();
		nomeRua.clear();complemento.clear();numero.clear();
		whatsapp.setSelected(false);cidade.getSelectionModel().clearSelection();cidade.getEditor().clear();
		estado.getSelectionModel().clearSelection();estado.getEditor().clear();bairro.clear();
		nomePai.clear();nomeMae.clear();responsavelFin.setSelected(false);
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

		new AutoCompleteComboBoxListener<>(estado);
		new AutoCompleteComboBoxListener<>(cidade);
		new AutoCompleteComboBoxListener<>(estado1);
		new AutoCompleteComboBoxListener<>(cidade1);
    }

    @FXML
    void populateCidades(ActionEvent event) {
    	Util.populateCidade(estado, cidade);
    	Util.populateCidade(estado1, cidade1);
    }

    @FXML
    void proximo(ActionEvent event) {
    	if(checkTextFields()){
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

    @FXML
    void proximo2(ActionEvent event){
    	if(event.getSource() == btn_proximo1){
    		if(checkTextFields2()){
    			tab_grade.setDisable(false);
        		tabPane.getSelectionModel().select(tab_grade);
    		}
    	}
    	else{
    		ViewResponsavelFinanceiro select = table_pessoas.getSelectionModel().getSelectedItem();
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
    void selecionar(ActionEvent event) {
    	box_cadastro.setVisible(false);
    	box_table.setVisible(true);
    }

    @FXML
    void cadastrar(ActionEvent event) {
    	box_cadastro.setVisible(true);
    	box_table.setVisible(false);
    }

    void initTable(){
    	oblist_curriculos = UtilDao.getLists(Curriculo.class);
    	oblist_resp = UtilDao.getLists(ViewResponsavelFinanceiro.class);

    	col_cod.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_anoLetivo.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

		col_cod.setCellFactory(column -> {
			final TableCell<Curriculo, CurriculoID> cell = new TableCell<Curriculo, CurriculoID>(){

				@Override
				protected void updateItem(CurriculoID item, boolean empty) {
					super.updateItem(item, empty);

					if(empty){
						this.setText("");
					}else{
						this.setText(String.valueOf(item.getCodCurriculo()));
					}
				}

			};
			return cell;
		});

		col_anoLetivo.setCellFactory(column -> {
			final TableCell<Curriculo, CurriculoID> cell = new TableCell<Curriculo, CurriculoID>(){

				@Override
				protected void updateItem(CurriculoID item, boolean empty) {
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

		table_pessoas.setItems(oblist_resp);


    }

    boolean checkTextFields2(){

    	TextFieldFormatter tff = new TextFieldFormatter();

    	if(nome1.getText().length() == 0 || nome.getText() == null){
    		Util.Alert("Verifique o nome!");
    		return false;
    	}

    	if(cpf1.getText().length() > 11 && cpf1.getText().length() < 14){
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

    	if(estado1.getSelectionModel().getSelectedItem() == null || cidade1.getSelectionModel().getSelectedItem() == null){
    		Util.Alert("Selecione cidade e estado!");
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

    	if(cpf.getText().length() > 0 && cpf.getText().length() > 11 && cpf.getText().length() < 14){
    		Util.Alert("Verifique o CPF!");
    		return false;
    	}else{
    		tff = new TextFieldFormatter();
    		tff.setMask("###.###.###-##");
    		tff.setCaracteresValidos("0123456789");
    		tff.setTf(cpf);
    		tff.formatter();
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

    	if(estado.getSelectionModel().getSelectedItem() == null || cidade.getSelectionModel().getSelectedItem() == null){
    		Util.Alert("Selecione cidade e estado!");
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
    	Period diff = Period.between(dt_nascimento.getValue(), now);
    	if(diff.getYears() >= 18){
    		responsavelFin.setVisible(true);
    	}else{
    		responsavelFin.setVisible(false);
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
