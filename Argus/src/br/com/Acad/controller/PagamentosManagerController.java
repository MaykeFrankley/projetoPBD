package br.com.Acad.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Boleto_pdfID;
import br.com.Acad.model.Pagamento;
import br.com.Acad.model.PagamentoID;
import br.com.Acad.model.ViewBoleto;
import br.com.Acad.model.ViewPagamento;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
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

public class PagamentosManagerController implements Initializable{

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab pag_tab;

    @FXML
    private JFXTextField campo_pesquisa;

    @FXML
    private TableView<ViewPagamento> table_pag;

    @FXML
    private TableColumn<ViewPagamento, PagamentoID> col_cod;

    @FXML
    private TableColumn<ViewPagamento, String> col_nome;

    @FXML
    private TableColumn<ViewPagamento, Date> col_dt_vencimento;

    @FXML
    private TableColumn<ViewPagamento, Integer> col_parcela;

    @FXML
    private TableColumn<ViewPagamento, String> col_situacao;

    @FXML
    private JFXTextField nossoNum;

    @FXML
    private Tab boletos_tab;

    @FXML
    private JFXTextField campo_pesquisa1;

    @FXML
    private TableView<ViewBoleto> table_boletos;

    @FXML
    private TableColumn<ViewBoleto, Boleto_pdfID> col_cod1;

    @FXML
    private TableColumn<ViewBoleto, String> col_nome1;

    @FXML
    private TableColumn<ViewBoleto, String> col_aluno;

    @FXML
    private TableColumn<ViewBoleto, String> col_curriculo;

    @FXML
    private TableColumn<ViewBoleto, Boleto_pdfID> col_anoLetivo;

    private ObservableList<ViewPagamento> ob_pags = FXCollections.observableArrayList();

    private ObservableList<ViewBoleto> ob_boletos = FXCollections.observableArrayList();

    private FilteredList<ViewPagamento> filtered_pags;

    private FilteredList<ViewBoleto> filtered_bol;

    @FXML
    void gerarBoletos(ActionEvent event) throws IOException {
    	ViewBoleto selected = table_boletos.getSelectionModel().getSelectedItem();
    	if(selected != null){
    		File pdf = UtilDao.daoPagamentos.getBoletos(selected.getId());
    		Desktop.getDesktop().open(pdf);
    	}
    }

    @FXML
    void searchNossoNum(KeyEvent event) {
    	nossoNum.textProperty().addListener((observableValue, oldValue,newValue)->{
			filtered_pags.setPredicate(pag->{
				String lowerCaseFilter = newValue.toLowerCase();
				if(newValue==null || newValue.isEmpty()){
					return true;
				}

				else if(String.valueOf(pag.getId().getNossoNumero()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				return false;
			});
		});
		SortedList<ViewPagamento> sortedData = new SortedList<>(filtered_pags);
		sortedData.comparatorProperty().bind(table_pag.comparatorProperty());
		table_pag.setItems(sortedData);
    }

    @FXML
    void searchBoleto(KeyEvent event) {
    	campo_pesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
			filtered_pags.setPredicate(pag->{
				String lowerCaseFilter = newValue.toLowerCase();
				if(newValue==null || newValue.isEmpty()){
					return true;
				}

				else if(String.valueOf(pag.getId().getCodResponsavel()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}
				else if(pag.getDt_vencimento().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(lowerCaseFilter)){
					return true;
				}

				else if(String.valueOf(pag.getNum_parcela()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(pag.getSituacao().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(pag.getResponsavel().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				return false;
			});
		});
		SortedList<ViewPagamento> sortedData = new SortedList<>(filtered_pags);
		sortedData.comparatorProperty().bind(table_pag.comparatorProperty());
		table_pag.setItems(sortedData);
    }

    @FXML
    void searchResponsavel(KeyEvent event) {
    	campo_pesquisa1.textProperty().addListener((observableValue, oldValue,newValue)->{
			filtered_bol.setPredicate(bol->{
				String lowerCaseFilter = newValue.toLowerCase();
				if(newValue==null || newValue.isEmpty()){
					return true;
				}

				else if(String.valueOf(bol.getId().getAnoLetivo()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(String.valueOf(bol.getId().getCodResponsavel()).toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(bol.getAluno().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(bol.getCurriculo().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				else if(bol.getResponsavel().toLowerCase().contains(lowerCaseFilter)){
					return true;
				}

				return false;
			});
		});
		SortedList<ViewBoleto> sortedData = new SortedList<>(filtered_bol);
		sortedData.comparatorProperty().bind(table_boletos.comparatorProperty());
		table_boletos.setItems(sortedData);
    }

    @FXML
    void setPago(ActionEvent event) {
    	Pagamento p = UtilDao.daoPagamentos.getPagamento(Long.valueOf(nossoNum.getText()));
    	if(p != null){
    		p.setSituacao("Pago");
    		UtilDao.daoPagamentos.updatePagamento(p);
    		Util.Alert("Pagamento atualizado!");
    		initTables();
    	}
    }

    void initTables(){
    	ob_pags = UtilDao.daoPagamentos.getAllPagamentos();
    	ob_boletos = UtilDao.daoPagamentos.getAllBoletos();
    	if(ob_pags != null)filtered_pags = new FilteredList<>(ob_pags);
    	if(ob_boletos != null)filtered_bol = new FilteredList<>(ob_boletos);

    	col_cod.setCellValueFactory(new PropertyValueFactory<>("id"));
    	col_dt_vencimento.setCellValueFactory(new PropertyValueFactory<>("dt_vencimento"));
    	col_nome.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
    	col_parcela.setCellValueFactory(new PropertyValueFactory<>("num_parcela"));
    	col_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

    	col_cod.setCellFactory(column -> {
			TableCell<ViewPagamento, PagamentoID> cell = new TableCell<ViewPagamento, PagamentoID>() {

				@Override
				protected void updateItem(PagamentoID item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(String.valueOf(item.getCodResponsavel()));
					}
				}
			};
			return cell;
		});

    	col_dt_vencimento.setCellFactory(column -> {
			TableCell<ViewPagamento, Date> cell = new TableCell<ViewPagamento, Date>() {
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

    	table_pag.setItems(ob_pags);

    	col_cod1.setCellValueFactory(new PropertyValueFactory<>("id"));
    	col_nome1.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
    	col_aluno.setCellValueFactory(new PropertyValueFactory<>("aluno"));
    	col_curriculo.setCellValueFactory(new PropertyValueFactory<>("curriculo"));
    	col_anoLetivo.setCellValueFactory(new PropertyValueFactory<>("id"));

    	col_cod1.setCellFactory(column -> {
			TableCell<ViewBoleto, Boleto_pdfID> cell = new TableCell<ViewBoleto, Boleto_pdfID>() {

				@Override
				protected void updateItem(Boleto_pdfID item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(String.valueOf(item.getCodResponsavel()));
					}
				}
			};
			return cell;
		});

    	col_anoLetivo.setCellFactory(column -> {
			TableCell<ViewBoleto, Boleto_pdfID> cell = new TableCell<ViewBoleto, Boleto_pdfID>() {

				@Override
				protected void updateItem(Boleto_pdfID item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setText(null);
					}
					else {
						this.setText(String.valueOf(item.getAnoLetivo()));
					}
				}
			};
			return cell;
		});

    	table_boletos.setItems(ob_boletos);

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTables();

		nossoNum.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null){
				if(newValue.matches("\\d+")){
					nossoNum.setText(newValue);
				}

				if(newValue.equals("")){
					nossoNum.setText(newValue);
				}

				if(newValue.length() > 11){
					nossoNum.setText(oldValue);
				}
			}
		});
	}

}
