package br.com.Acad.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.Acad.model.Contato;
import br.com.Acad.model.Endereco;
import br.com.Acad.model.Pessoa;
import br.com.Acad.model.Usuario;
import br.com.Acad.util.AutoCompleteComboBoxListener;
import br.com.Acad.util.SysLog;
import br.com.Acad.util.TextFieldFormatter;
import br.com.Acad.util.Util;
import br.com.Acad.util.UtilDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;

public class CadastrarUsuarioController implements Initializable{

    @FXML
    private JFXTextField nome;

    @FXML
    private JFXDatePicker dt_nascimento;

    @FXML
    private JFXTextField naturalidade;

    @FXML
    private JFXTextField cpf;

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
    private JFXTextField nomeUsuario;

    @FXML
    private JFXPasswordField senha;

    @FXML
    private ComboBox<String> tipoUsuario;

    @FXML
    private JFXButton btn_confirmar;

    private ObservableList<Pessoa> oblist_pessoas = FXCollections.observableArrayList();

    ObservableList<Usuario> oblist_usuarios = FXCollections.observableArrayList();

    @FXML
    void confirmar(ActionEvent event) {

    	if(checkTextFields()){

    		oblist_pessoas = UtilDao.daoPessoa.getAllPessoa();
    		for (int i = 0; i < oblist_pessoas.size(); i++) {
    			String obCPF = oblist_pessoas.get(i).getCpf();

				if(obCPF != null && obCPF.equals(cpf.getText())){
					Util.Alert("CPF já está cadastrado no sistema!");
					return;
				}
			}

    		try {

    			Pessoa p = new Pessoa();
        		Endereco e = new Endereco();
        		Usuario u = new Usuario();
        		Contato c = new Contato();

        		Pattern r = Pattern.compile("\\S+");
                Matcher m = r.matcher(nome.getText());

                String primeiroNome = "";

                if (m.find( )) {
                	primeiroNome = m.group(0);
                }

        		String passWord = primeiroNome+cpf.getText().substring(0, 3);

        		String hashPass = DigestUtils.md5Hex(passWord);

        		senha.setText(passWord);

        		p.setNome(nome.getText());
        		p.setCpf(cpf.getText());
        		Date date = Date.valueOf(dt_nascimento.getValue());
        		p.setDt_nascimento(date);
        		p.setNaturalidade(naturalidade.getText());
        		p.setStatus("Ativo");

        		int cod = UtilDao.daoPessoa.addPessoa(p);
        		SysLog.addLog(SysLog.createPessoas(cod));

        		e.setCodPessoa(cod);
        		e.setBairro(bairro.getText());
        		e.setCidade(cidade.getSelectionModel().getSelectedItem());
        		e.setEstado(estado.getSelectionModel().getSelectedItem());
        		e.setNumero(Integer.valueOf(numero.getText().replaceAll("\\s+", "")));
        		e.setRua(nomeRua.getText());
        		if(complemento.getText() != null && complemento.getText().length() > 0)e.setComplemento(complemento.getText());

        		UtilDao.daoEnderecos.addEndereco(e);
        		SysLog.addLog(SysLog.createDados("Endereço", cod));

        		c.setCodPessoa(cod);
        		if(email.getText().length() > 0)c.setEmail(email.getText());
    			if(telefone.getText().length() > 0)c.setTelefone(telefone.getText());
    			if(celular.getText().length() > 0)c.setCelular(celular.getText());
    			if(whatsapp.isSelected()){
    				c.setWhatsapp(1);
    			}
    			else{
    				c.setWhatsapp(0);
    			}

    			if(email.getText().length() > 0 || telefone.getText().length() > 0 || celular.getText().length() > 0){
    				UtilDao.daoContatos.addContato(c);
    				SysLog.addLog(SysLog.createDados("Contato", cod));
    			}

    			u.setCodPessoa(cod);
    			u.setCpf(cpf.getText());
    			u.setUser(nomeUsuario.getText());
    			u.setSenha(hashPass);
    			u.setTipo(tipoUsuario.getSelectionModel().getSelectedItem());
    			u.setStatus("Ativo");

        	    Util.setPrivileges(u);
        		Util.Alert("Usuário cadastrado com sucesso!");

        		limpar(event);

			} catch (Exception e) {
				Util.Alert("Erro ao concluir o cadastro!\nContate o administrador!");
				e.printStackTrace();
			}

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
    void limpar(ActionEvent event) {
    	nome.clear();cpf.clear();
		dt_nascimento.setValue(null);naturalidade.clear();
		nomeRua.clear();complemento.clear();numero.clear();
		whatsapp.setSelected(false);cidade.getSelectionModel().clearSelection();cidade.getEditor().clear();
		tipoUsuario.getSelectionModel().clearSelection();tipoUsuario.getEditor().clear();
		estado.getSelectionModel().clearSelection();estado.getEditor().clear();bairro.clear();
		nomeUsuario.clear();senha.clear();
    }

    void populateBoxes(){
    	estado.getItems().addAll("Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal",
				"Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba",
				"Paraná","Pernambuco","Piauí","Rio de Janeiro",
				"Rio Grande do Norte","Rio Grande do Sul","Rondônia",
				"Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins");

    	tipoUsuario.getItems().addAll("Admin", "Secretaria", "Direção", "CoordenacaoPedagogica");
		new AutoCompleteComboBoxListener<>(estado);
		new AutoCompleteComboBoxListener<>(cidade);
    }

    @FXML
    void populateCidades(ActionEvent event) {

		Util.populateCidade(estado, cidade);
    }

    @FXML
    void setUserName(KeyEvent event) {

    	String name = nome.getText();

    	String[] part = name.split(" ");

    	String teste = part[part.length - 1];

    	String ultimoNome = teste;

    	String pattern = "\\S+";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(nome.getText());

        String primeiroNome = "";

        if (m.find( )) {
        	primeiroNome = m.group(0);
        }

        if(primeiroNome.equals(ultimoNome)){
        	nomeUsuario.clear();
        	return;
        }

        nomeUsuario.setText(primeiroNome+ultimoNome);

        for (Usuario usuario : oblist_usuarios) {
			if(usuario.getUser().equals(nomeUsuario.getText())){
				Pattern p = Pattern.compile("\\d+");
				Matcher ma = p.matcher(usuario.getUser());
				if(ma.find()){
					String s = ma.group();
					int i = Integer.valueOf(s);
					i++;
					nomeUsuario.setText(primeiroNome+ultimoNome+String.valueOf(i));
				}else{
					nomeUsuario.setText(primeiroNome+ultimoNome+String.valueOf(1));
				}
			}
		}


    }

    boolean checkTextFields(){

    	TextFieldFormatter tff = new TextFieldFormatter();

    	if(nome.getText().length() == 0 || nome.getText() == null){
    		Util.Alert("Verifique o nome!");
    		return false;
    	}

    	if(nomeUsuario.getText().length() == 0 || nomeUsuario.getText() == null){
    		Util.Alert("Verifique o nome!");
    		return false;
    	}

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


    	if(celular.getText().length() > 0){
    		if(celular.getText().length() < 11 || (celular.getText().length() > 11 && celular.getText().length() < 14)){
    			Util.Alert("Verifique o número de celular.\nPs: celular não é obrigatório no cadastro!");
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



    	return true;

	}

    void initValidation(){
    	Util.requiredFieldSet(nome, naturalidade, cpf, nomeRua, numero);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    oblist_usuarios = UtilDao.daoUsuarios.getAllUsuarios();

		populateBoxes();
		initValidation();
	}

}
