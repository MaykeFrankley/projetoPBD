package br.com.Acad.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import br.com.Acad.exceptions.HandleSQLException;
import br.com.Acad.model.Usuario;
import br.com.Acad.sql.ConnectionClass;
import br.com.Acad.util.SetDbUser;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import net.sf.jasperreports.engine.JRException;

public class DrawerController{

	@FXML
	private FontAwesomeIconView icon_arrow;

	@FXML
	private Label label_user;

	@FXML
	private Button cadastrar_btn;

	@FXML
	private VBox box_cadastrar;

	@FXML
	private VBox box_buttons;

	@FXML
	private Button pessoas_btn;

	@FXML
	private Button usuarios_btn;

	@FXML
	private Button alunos_btn;

	@FXML
	private Button professores_btn;

	@FXML
	private Button log_btn;

	@FXML
	private Button pagamentos_btn;

	@FXML
	private Button historico_btn;

	@FXML
	private Button config_btn;

	@FXML
	private Button mudarSenha_btn;

	private MainTelaController mainController;

	@FXML
	void cadastrarDropDown(ActionEvent event) throws IOException {

		if(!box_cadastrar.isVisible()){
			icon_arrow.setGlyphName("CHEVRON_DOWN");
			int idx = box_buttons.getChildren().indexOf(cadastrar_btn)+1;
			box_buttons.getChildren().add(idx, box_cadastrar);
			box_cadastrar.setVisible(true);
		}else{
			icon_arrow.setGlyphName("CHEVRON_UP");
			box_buttons.getChildren().remove(box_cadastrar);
			box_cadastrar.setVisible(false);
		}

	}


	@FXML
	void cadastrar_curriculo(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/CadastrarCurriculo.fxml"), scene, "y");
	}

	@FXML
	void cadastrar_professor(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/CadastrarProfessor.fxml"), scene, "x");

	}

	@FXML
	void cadastrar_disciplinas(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/CadastrarDisciplina.fxml"), scene, "y");

	}

	@FXML
	void cadastrar_alunos(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/CadastrarAlunos.fxml"), scene, "x");
	}

	@FXML
	void cadastrar_usuario(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/CadastrarUsuario.fxml"), scene, "x");

	}

	@FXML
	void gerenciar_usuarios(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/UsuariosManager.fxml"), scene, "y");

	}

	@FXML
	void gerenciar_alunos(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/AlunosManager.fxml"), scene, "y");
	}

	@FXML
	void gerenciar_pessoas(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/PessoasManager.fxml"), scene, "x");

	}

	@FXML
	void gerenciar_professores(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/ProfessoresManager.fxml"), scene, "y");
	}

	@FXML
	void configuracoes(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/Settings.fxml"), scene, "x");
	}


	@FXML
	void historico_escolar(ActionEvent event) throws JRException, IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/HistoricoEscolar.fxml"), scene, "y");
	}


	@FXML
	void log_sistema(ActionEvent event) throws IOException {
		Scene scene = (Scene) ((Node) event.getSource()).getScene();
		Util.LoadWindow(getClass().getResource("/br/com/Acad/view/LogSistema.fxml"), scene, "y");

	}

	@FXML
	void logout_handler(ActionEvent event) {
		JFXButton yes = new JFXButton("LogOut");
		yes.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent even1) ->{
			mainController.disableDrawer();
			new SetDbUser("root", "9612").run();

			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/br/com/Acad/view/LoginTela.fxml"));
				Parent root = loader.load();
				LoginController control = loader.getController();
				control.setMainTela(mainController);

				Util.contentPane.getChildren().add(root);
				if(Util.contentPane.getChildren().size() > 1){
					Util.contentPane.getChildren().remove(0);
				}

				mainController.cancelNotificationTask();
				mainController.enableHamburger();
				MainTelaController.user = null;

			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		JFXButton cancel = new JFXButton("Cancelar");
		cancel.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event2) -> {
			Util.contentPane.getChildren().get(0).setEffect(null);
		});

		Util.confirmation(Arrays.asList(yes, cancel),"Tem certeza que deseja sair?");
	}

	@FXML
	void mudarSenha(ActionEvent event) {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.initStyle(StageStyle.UNDECORATED);
		dialog.getDialogPane().setPrefSize(300, 350);
		Util.setStyle(dialog.getDialogPane());

		ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(40);
		JFXPasswordField antigaSenha = new JFXPasswordField();
		antigaSenha.setLabelFloat(true);
		antigaSenha.setPromptText("Senha antiga");
		antigaSenha.requestFocus();
		box.getChildren().add(antigaSenha);

		JFXPasswordField novaSenha = new JFXPasswordField();
		novaSenha.setLabelFloat(true);
		novaSenha.setPromptText("Nova senha(De 6 a 11 caracteres)");
		novaSenha.requestFocus();
		box.getChildren().add(novaSenha);

		JFXPasswordField confirmarSenha = new JFXPasswordField();
		confirmarSenha.setLabelFloat(true);
		confirmarSenha.setPromptText("Confirmar Senha");
		confirmarSenha.requestFocus();
		box.getChildren().add(confirmarSenha);

		dialog.getDialogPane().setContent(box);

		antigaSenha.textProperty().addListener(
				(observable, old_value, new_value) -> {

					if(new_value.contains(" ")){
						antigaSenha.setText(old_value);
					}
					if(new_value.length() > 11){
						antigaSenha.setText(old_value);
					}
				}
				);

		novaSenha.textProperty().addListener(
				(observable, old_value, new_value) -> {

					if(new_value.contains(" ")){
						novaSenha.setText(old_value);
					}
					if(new_value.length() > 11){
						novaSenha.setText(old_value);
					}
				}
				);

		confirmarSenha.textProperty().addListener(
				(observable, old_value, new_value) -> {

					if(new_value.contains(" ")){
						confirmarSenha.setText(old_value);
					}
					if(new_value.length() > 11){
						confirmarSenha.setText(old_value);
					}
				}
				);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(novaSenha.getText(), confirmarSenha.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(pair -> {

			String hashantigo = DigestUtils.md5Hex(antigaSenha.getText());
			if(hashantigo.equals(MainTelaController.user.getSenha()) && pair.getKey().equals(pair.getValue()) && pair.getKey().length() >= 6){
				String hashNovo = DigestUtils.md5Hex(novaSenha.getText());

            	MainTelaController.user.setSenha(hashNovo);

            	UtilDao.daoUsuarios.updateUsuario(MainTelaController.user);

            	Connection con;
        	    con = ConnectionClass.createConnection();
        	    try {
           		 PreparedStatement stmt = con.prepareStatement("ALTER USER ?@'localhost' IDENTIFIED BY ?;");
            	    stmt.setString(1, MainTelaController.user.getUser());
            	    stmt.setString(2, MainTelaController.user.getSenha());
            	    stmt.executeUpdate();
            	    stmt.close();
            	    con.close();
				} catch (Throwable e) {
					new HandleSQLException(e);
				}

        	    Platform.runLater(() -> {
					Util.Alert("Senha alterada com sucesso!\nÉ nescessário fazer o login novamente.");
				});

        	    SysLog.addLog(SysLog.message("O usuário "+MainTelaController.user.getUser()+" alterou sua própria senha."));

			}
			else{
				Platform.runLater(() -> {
					Util.Alert("Verifique os campos e tente novamente!");
				});
			}
		});
	}

	@FXML
	void pagamentos(ActionEvent event) {

	}

	void setUser(Usuario user){
		this.label_user.setText(user.getUser());
		if(!MainTelaController.user.getTipo().equals("Admin")){
			pessoas_btn.setDisable(true);
		}else{
			pessoas_btn.setDisable(false);
		}

	}

	void setMainTela(MainTelaController mc){
		this.mainController = mc;
	}

}
