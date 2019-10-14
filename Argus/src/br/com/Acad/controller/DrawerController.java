package br.com.Acad.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.Acad.model.Usuario;
import br.com.Acad.util.SetDbUser;
import br.com.Acad.util.Util;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class DrawerController implements Initializable{

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
    private Button boletin_btn;

    @FXML
    private Button config_btn;

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
    void gerenciar_alunos(ActionEvent event) {

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
    void boletim_escolar(ActionEvent event) {

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
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

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
    void pagamentos(ActionEvent event) {

    }

    void setUser(Usuario user){
    	this.label_user.setText(user.getUser());
    }

    void setMainTela(MainTelaController mc){
    	this.mainController = mc;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
