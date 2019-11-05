package br.com.Acad.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Curriculo;
import br.com.Acad.model.Escola;
import br.com.Acad.model.ViewAluno;
import br.com.Acad.sql.ConnectionClass;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.EscolaXML;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class HistoricoEscolarController implements Initializable{

	@FXML
	private JFXTabPane tabPane;

	@FXML
	private Tab selecionarAluno;

	@FXML
	private Tab dadosEscola;

	@FXML
	private JFXTextField nomeEscola;

	@FXML
	private JFXTextField endereco;

	@FXML
	private JFXTextField bairro;

	@FXML
	private ComboBox<String> estado;

	@FXML
	private ComboBox<String> cidade;

	@FXML
	private JFXTextField telefone;

	@FXML
	private JFXTextField cnpj;

	@FXML
	private TableView<Curriculo> table_curriculo;

	@FXML
	private TableColumn<Curriculo, String> col_curriculo;

	@FXML
	private JFXTextField campoPesquisa;

	@FXML
	private TableView<ViewAluno> table_alunos;

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
	private TableColumn<ViewAluno, String> col_situacaoAluno;

	private ObservableList<Curriculo> oblist_curriculo = FXCollections.observableArrayList();

	private ObservableList<ViewAluno> oblist_alunos = FXCollections.observableArrayList();

	private FilteredList<ViewAluno> filteredData;

	@FXML
	void salvarEscola(ActionEvent event){
		if(!nomeEscola.getText().isEmpty() && !endereco.getText().isEmpty() && !bairro.getText().isEmpty() && estado.getSelectionModel().getSelectedItem() != null &&
				cidade.getSelectionModel().getSelectedItem() != null && !telefone.getText().isEmpty() && !cnpj.getText().isEmpty()){

			Escola escola = new Escola(nomeEscola.getText(), endereco.getText(), bairro.getText(), estado.getSelectionModel().getSelectedItem(),
					cidade.getSelectionModel().getSelectedItem(), telefone.getText(), cnpj.getText());
			EscolaXML.Save(escola);
			Util.Alert("Os dados foram salvos com sucesso!");
		}
		else{
			Util.Alert("Verifique os campos!");
		}
	}

	@FXML
	void gerarHistorico(ActionEvent event) throws JRException, IOException {
		ViewAluno aluno = table_alunos.getSelectionModel().getSelectedItem();
		Curriculo curriculo = table_curriculo.getSelectionModel().getSelectedItem();
		if(aluno != null && curriculo != null){
			Connection con = ConnectionClass.createConnection();
			JasperDesign jd = JRXmlLoader.load(new File("").getClass().getResourceAsStream("/br/com/Acad/reports/historicoEscolar.jrxml"));
			HashMap<String, Object> param = new HashMap<>();
			param.put("nomeEscola", nomeEscola.getText());param.put("endereco", endereco.getText());
			param.put("bairro", bairro.getText());param.put("cnpj", cnpj.getText());
			param.put("uf", estado.getSelectionModel().getSelectedItem());param.put("cidade", cidade.getSelectionModel().getSelectedItem());
			param.put("telefone", telefone.getText());param.put("nomeAluno", aluno.getNome());
			param.put("dt_nascimento", aluno.getDt_nascimento());
			param.put("naturalidade", aluno.getNaturalidade());param.put("nomeMae", aluno.getNomeMae());
			param.put("nomePai", aluno.getNomePai());param.put("ufAluno", UtilDao.daoEnderecos.getEndereco(aluno.getCodPessoa()).getEstado());
			param.put("codAluno", aluno.getCodPessoa());param.put("nomeCurriculo", curriculo.getNome());
			Image img = ImageIO.read(new File(getClass().getResource("/images/argus_logo_transparent.png").getFile()));
			param.put("codCurriculo", curriculo.getCodCurriculo());param.put("argusLogo", img);

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

		}else{
			Util.Alert("Selecione um aluno!");
		}
	}

	@FXML
	void searchAlunoTurma(KeyEvent event) {
		campoPesquisa.textProperty().addListener((observableValue, oldValue,newValue)->{
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
		sortedData.comparatorProperty().bind(table_alunos.comparatorProperty());
		table_alunos.setItems(sortedData);
	}

	void initTables(){
		oblist_curriculo = UtilDao.daoCurriculo.getAllCurriculo();

		col_curriculo.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_codPessoa.setCellValueFactory(new PropertyValueFactory<>("codPessoa"));
		col_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		col_dt_nascimento.setCellValueFactory(new PropertyValueFactory<>("dt_nascimento"));
		col_naturalidade.setCellValueFactory(new PropertyValueFactory<>("naturalidade"));
		col_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		col_nomeMae.setCellValueFactory(new PropertyValueFactory<>("nomeMae"));
		col_nomePai.setCellValueFactory(new PropertyValueFactory<>("nomePai"));
		col_situacaoAluno.setCellValueFactory(new PropertyValueFactory<>("situacao"));
		col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

		table_curriculo.setItems(oblist_curriculo);

		table_curriculo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null){
				Connection con = ConnectionClass.createConnection();
				try {
					oblist_alunos.clear();
					table_alunos.getItems().clear();

					PreparedStatement stmt = con.prepareStatement("SELECT codTurma FROM argus.turmas WHERE codCurriculo = ? LIMIT 1;");
					stmt.setString(1, newSelection.getCodCurriculo());
					ResultSet rs = stmt.executeQuery();
					if(rs.next()){
						oblist_alunos = UtilDao.daoTurmas.getAlunos(rs.getInt(1));
						rs.close();
					}
					table_alunos.setItems(oblist_alunos);
					filteredData = new FilteredList<>(oblist_alunos);

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

	}

	@FXML
	void populateCidades(ActionEvent event) {
		Util.populateCidade(estado, cidade);

	}

	void populateBoxes(){
		estado.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");

		new AutoCompleteComboBoxListener<>(estado);
		new AutoCompleteComboBoxListener<>(cidade);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selecionarAluno.setOnSelectionChanged(e ->{
			if(selecionarAluno.isSelected()){
				if(nomeEscola.getText().isEmpty() && endereco.getText().isEmpty() && bairro.getText().isEmpty() && estado.getSelectionModel().getSelectedItem() == null &&
						cidade.getSelectionModel().getSelectedItem() == null && telefone.getText().isEmpty() && cnpj.getText().isEmpty()){
					tabPane.getSelectionModel().select(dadosEscola);
					Util.Alert("Verifique os dados da escola");
				}
			}

		});

		initTables();
		populateBoxes();

		Escola escola = EscolaXML.get();
		if(escola != null){
			nomeEscola.setText(escola.getNome());
			endereco.setText(escola.getEndereco());
			bairro.setText(escola.getBairro());
			estado.getSelectionModel().select(escola.getEstado());
			estado.fireEvent(new ActionEvent());
			cidade.getSelectionModel().select(escola.getCidade());
			telefone.setText(escola.getTelefone());
			cnpj.setText(escola.getCnpj());
		}

	}

}
