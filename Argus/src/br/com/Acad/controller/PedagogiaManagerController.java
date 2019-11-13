package br.com.Acad.controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Escola;
import br.com.Acad.model.SessaoPedagogica;
import br.com.Acad.model.SessaoPedagogicaID;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.model.ViewSessao;
import br.com.Acad.sql.ConnectionClass;
import br.com.Acad.util.EscolaXML;
import br.com.Acad.util.SetDbUser;
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
import javafx.scene.layout.HBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PedagogiaManagerController implements Initializable{

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab alunosTab;

    @FXML
    private JFXTextField campo_pesquisa;

    @FXML
    private JFXTextField campo_Pesquisa2;

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
    private Tab relacaoTab;

    @FXML
    private HBox box_admin;

    @FXML
    private JFXTextField codPedagogo;

    @FXML
    private JFXDatePicker dt_atendimento;

    @FXML
    private TableView<ViewSessao> table_sessoes;

    @FXML
    private TableColumn<ViewSessao, String> col_codAluno_Sessao;

    @FXML
    private TableColumn<ViewSessao, String> col_nomeAluno_sessao;

    @FXML
    private TableColumn<ViewSessao, Date> col_dt_atendimento;

    @FXML
    private TableColumn<ViewSessao, String> col_status_sessao;

    @FXML
    private JFXTextArea detalhamentoArea;

    @FXML
    private ComboBox<String> box_situacao;

    @FXML
    private ComboBox<String> box_ano;

    private ObservableList<ViewAluno> oblist_pessoas = FXCollections.observableArrayList();

    private ObservableList<ViewSessao> oblist_sessoes = FXCollections.observableArrayList();

    private FilteredList<ViewAluno> filteredData;

    private FilteredList<ViewSessao> filteredData2;

    private int codigoPedagogoInt;

    @FXML
    void buscarPedagogo(ActionEvent event) {
    	oblist_sessoes.clear();
    	if(codPedagogo.getText().length() > 0){
    		codigoPedagogoInt = Integer.valueOf(codPedagogo.getText());
    		oblist_sessoes = UtilDao.daoPedagogos.getSessaoPorPedagogo(codigoPedagogoInt);
    		table_sessoes.setItems(oblist_sessoes);
    	}
    }

    @FXML
    void gerarRelacao(ActionEvent event) throws JRException {
    	if(!table_sessoes.getItems().isEmpty() && !box_ano.getSelectionModel().isEmpty()){
    		new SetDbUser("root", "9612").run();
    		Connection con = ConnectionClass.createConnection();
			JasperDesign jd = JRXmlLoader.load(new File("").getClass().getResourceAsStream("/br/com/Acad/reports/relacaoAcompanhamentos.jrxml"));
			HashMap<String, Object> param = new HashMap<>();
			param.put("codPedagogo", codigoPedagogoInt);
			param.put("data", Integer.valueOf(box_ano.getSelectionModel().getSelectedItem()));
			param.put("pedagogo", UtilDao.daoPessoa.getPessoa(codigoPedagogoInt).getNome());

			Thread thread = new Thread(){
				public void run() {
					JasperReport jr;
					JasperPrint jp = null;
					try {
						jr = JasperCompileManager.compileReport(jd);
						jp = JasperFillManager.fillReport(jr, param, con);
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JasperViewer jv = new JasperViewer(jp, false);
					jv.setVisible(true);
				};
			};

			thread.start();
			Util.Alert("Aguarde um instante!");

			new SetDbUser(MainTelaController.user.getUser(), MainTelaController.user.getSenha()).run();

    	}
    }

    @FXML
    void getDetalhamento(ActionEvent event) {
    	box_situacao.setMouseTransparent(true);
    	detalhamentoArea.setEditable(false);
    	detalhamentoArea.setPromptText(null);
    	detalhamentoArea.clear();
    	box_situacao.getSelectionModel().clearSelection();
    	if(dt_atendimento.getValue() == null) return;
    	Date dt = Date.valueOf(dt_atendimento.getValue());
    	ViewAluno selected = table_pessoas.getSelectionModel().getSelectedItem();
    	if(selected != null){
    		SessaoPedagogica sessao = UtilDao.daoPedagogos.getSessao(new SessaoPedagogicaID(codigoPedagogoInt, selected.getCodPessoa(), dt));
    		if(sessao != null){
	    		detalhamentoArea.setText(sessao.getDetalhamento());
	    		box_situacao.getSelectionModel().select(sessao.getStatus());
    		}
    	}
    	else{
    		Util.Alert("Seleciona um aluno");
    		dt_atendimento.setValue(null);
    	}

    }

    @FXML
    void gerarRelatorio(ActionEvent event) throws JRException {
    	ViewAluno a = table_pessoas.getSelectionModel().getSelectedItem();
    	if(a != null && dt_atendimento.getValue() != null && !detalhamentoArea.getText().isEmpty()){
    		new SetDbUser("root", "9612").run();
    		Connection con = ConnectionClass.createConnection();
			JasperDesign jd = JRXmlLoader.load(new File("").getClass().getResourceAsStream("/br/com/Acad/reports/acompanhamentoPoraluno.jrxml"));
			HashMap<String, Object> param = new HashMap<>();
			Escola escola = EscolaXML.get();
			Date at = Date.valueOf(dt_atendimento.getValue());

			LocalDate today = LocalDate.now();
			LocalDate birthday = a.getDt_nascimento().toLocalDate();

			Period p = Period.between(birthday, today);

			param.put("nomeAluno", a.getNome());param.put("dt_nascimento", a.getDt_nascimento());
			param.put("nomeEscola", escola.getNome());param.put("nomePedagogo", UtilDao.daoPessoa.getPessoa(codigoPedagogoInt).getNome());
			param.put("dt_atendimento", at);param.put("idadeAluno", p.getYears());param.put("codPedagogo", codigoPedagogoInt);
			param.put("codAluno", a.getCodPessoa());

			Thread thread = new Thread(){
				public void run() {
					JasperReport jr;
					JasperPrint jp = null;
					try {
						jr = JasperCompileManager.compileReport(jd);
						jp = JasperFillManager.fillReport(jr, param, con);
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JasperViewer jv = new JasperViewer(jp, false);
					jv.setVisible(true);
				};
			};

			thread.start();
			Util.Alert("Aguarde um instante!");

			new SetDbUser(MainTelaController.user.getUser(), MainTelaController.user.getSenha()).run();

    	}

    }

    @FXML
    void salvarEdicao(ActionEvent event) {
    	ViewAluno selected = table_pessoas.getSelectionModel().getSelectedItem();
    	if(detalhamentoArea.isEditable() && selected != null && dt_atendimento.getValue() != null){
    		Date data = Date.valueOf(dt_atendimento.getValue());
    		SessaoPedagogica s = UtilDao.daoPedagogos.getSessao(new SessaoPedagogicaID(MainTelaController.user.getCodPessoa(), selected.getCodPessoa(), data));
    		s.setDetalhamento(detalhamentoArea.getText());
    		s.setStatus(box_situacao.getSelectionModel().getSelectedItem());
    		UtilDao.daoPedagogos.updateSessao(s);
    		Util.Alert("Sessão atualizada com sucesso!");
    		getDetalhamento(event);
    	}
    }

     @FXML
    void editarDetalhamento(ActionEvent event) {
    	 if(detalhamentoArea.getText().isEmpty()) return;
    	 detalhamentoArea.setEditable(true);
    	 box_situacao.setMouseTransparent(false);
    	 detalhamentoArea.setPromptText("Editável");
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
    void searchSessao(KeyEvent event) {
    	campo_Pesquisa2.textProperty().addListener((observableValue, oldValue,newValue)->{
			filteredData2.setPredicate(sessao->{
				if(newValue==null || newValue.isEmpty()){
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if(String.valueOf(sessao.getCodAluno()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(sessao.getNome().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(sessao.getData().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(lowerCaseFilter)){
					return true;
				}

				else if(sessao.getStatus().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				return false;
			});
		});
		SortedList<ViewSessao> sortedData = new SortedList<>(filteredData2);
		sortedData.comparatorProperty().bind(table_sessoes.comparatorProperty());
		table_sessoes.setItems(sortedData);
    }


    void initTables(){
    	oblist_pessoas = UtilDao.daoAlunos.getAllAlunosView();
    	oblist_sessoes = UtilDao.daoPedagogos.getSessaoPorPedagogo(MainTelaController.user.getCodPessoa());

		if(oblist_pessoas != null)filteredData = new FilteredList<>(oblist_pessoas);
		if(oblist_sessoes != null)filteredData2 = new FilteredList<>(oblist_sessoes);

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

		col_codAluno_Sessao.setCellValueFactory(new PropertyValueFactory<>("codAluno"));
		col_nomeAluno_sessao.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_dt_atendimento.setCellValueFactory(new PropertyValueFactory<>("data"));
		col_status_sessao.setCellValueFactory(new PropertyValueFactory<>("status"));

		col_dt_atendimento.setCellFactory(column -> {
			TableCell<ViewSessao, Date> cell = new TableCell<ViewSessao, Date>() {
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

		table_sessoes.setItems(oblist_sessoes);

		table_pessoas.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
			if(newSelection != null){
				detalhamentoArea.clear();
				box_situacao.getSelectionModel().clearSelection();
				dt_atendimento.setValue(null);
			}
		});

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTables();
		if(MainTelaController.user.getTipo().equals("Admin")){
			box_admin.setVisible(true);
		}
		else{
			codigoPedagogoInt = MainTelaController.user.getCodPessoa();
			Connection con = ConnectionClass.createConnection();
			try {
				ResultSet rs = con.prepareStatement("SELECT YEAR(data) FROM argus.sessaopedagogica;").executeQuery();
				while(rs.next()){
					box_ano.getItems().add(String.valueOf(rs.getInt(1)));
				}
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		box_situacao.getItems().setAll("Em atendimento", "Concluído");
	}

}
