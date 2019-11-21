package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.SessaoPedagogica;
import br.com.Acad.model.SessaoPedagogicaID;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class CadastrarSessaoPedagogicaController implements Initializable{

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab alunosTab;

    @FXML
    private JFXTextField campo_pesquisa;

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
    private TableColumn<ViewAluno, String> col_status;

    @FXML
    private JFXDatePicker dt_detalhamento;

    @FXML
    private JFXTextArea detalhamentoArea;

    private FilteredList<ViewAluno> filteredData;

    @FXML
    void cadastrar(ActionEvent event) {
    	ViewAluno selecAluno = table_pessoas.getSelectionModel().getSelectedItem();
    	if(selecAluno != null && dt_detalhamento.getValue() != null){
    		if(detalhamentoArea.getText().isEmpty()){
    			Util.Alert("É nescessário adicionar o detalhamento!");
    			return;
    		}
    		try {

    			Date d = Date.valueOf(dt_detalhamento.getValue());
        		SessaoPedagogica s = new SessaoPedagogica();
        		s.setDetalhamento(detalhamentoArea.getText());
        		s.setStatus("Em atendimento");
        		s.setId(new SessaoPedagogicaID(MainTelaController.user.getCodPessoa(), selecAluno.getCodPessoa(), d));

        		SessaoPedagogica checkSessao = UtilDao.daoPedagogos.getSessao(s.getId());
        		if(checkSessao != null){
        			Util.Alert("Já existe uma sessão cadastrada pra esse aluno nesse dia!");
        			return;
        		}

        		UtilDao.daoPedagogos.addSessao(s);

        		Util.Alert("Sessão cadastrada com sucesso!");

        		table_pessoas.getSelectionModel().clearSelection();

        		detalhamentoArea.clear();

        		dt_detalhamento.setValue(null);
			} catch (Throwable e) {
				e.printStackTrace();
				Util.Alert("Ocorreu um erro!");
			}
    	}
    	else{
    		Util.Alert("Selecione um aluno e a data de atendimento");
    	}

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

    void initTable(){
    	ObservableList<ViewAluno> alunos = UtilDao.daoAlunos.getAllAlunosView();
    	if(alunos != null)filteredData = new FilteredList<>(alunos);

    	col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
    	col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	col_dt_nascimento.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));
    	col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	col_naturalidade.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));

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

    	table_pessoas.setItems(alunos);

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();
	}

}
