/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import modelo.bean.Datas;
import modelo.bean.Entradas;
import modelo.dao.MovimentoDAO;
import produzconexao.RefazerConexao;
import view.frmEntrar;
import view.frmMovimento;
import static view.frmMovimento.ftxtValor;
import static view.frmMovimento.txtAtendentecaixa;
import static view.frmMovimento.txtCaixainicial;
import static view.frmMovimento.txtMoedasinicio;
import static view.frmMovimento.txtNotasinicio;
import static view.frmMovimento.txtVendas;
import static view.frmPassagemDeCaixa.ftxtPassagemmoedasreservadas;
import static view.frmPassagemDeCaixa.ftxtPassagemnotasreservadas;
import static view.frmPrincipal.btnCaixa;
import static view.frmPrincipal.btnEntrar;
import static view.frmPrincipal.btnLogin;
import static view.frmPrincipal.dtpDescktop;
import static view.frmPrincipal.mnCaixa;
import static view.frmPrincipal.mnEntrar;
import static view.frmPrincipal.mnFecharEntrar;
import static view.frmPrincipal.mnFecharNovousuario;
import static view.frmPrincipal.mnFecharRelatorios;
import static view.frmPrincipal.mnFecharcaixa;
import static view.frmPrincipal.mnNovousuario;
import static view.frmPrincipal.mnRelatorios;
import static view.frmPrincipal.btnRelatorio;

/**
 *
 * @author Pituba
 */
public class EntradaNovo {
    GerenciadordeJanelas gerenciadordejanelas = new GerenciadordeJanelas(dtpDescktop);
    frmMovimento frmmovimento = new frmMovimento();
    GuardarUrl guardarurl = new GuardarUrl();
    private static frmEntrar frmentrar;
    private static JDesktopPane jdesktoppane;
    String nomeusuario = "", tipousuario = "";
    int iddata;
    Date datahoje;
    int idusuario;                                        
    String valorinicialcedula, valorinicialmoedas;
    MovimentoDAO movdao = new MovimentoDAO();
    public void EntradaNovo(String agora, String horaagora, int iddata, int idusuario){
                
               float valorinicialn = 0;
               DecimalFormat obj_formato = new DecimalFormat();
               obj_formato.applyPattern("##,##0.00");
               dtpDescktop.add(frmmovimento);
               frmmovimento.setVisible(true);
               frmmovimento.setPosicao(); 
                 valorinicialcedula = ftxtPassagemnotasreservadas.getText();//JOptionPane.showInputDialog(null, "Digite o valor em cédulas para início de caixa.\n Somente números, ponto e vírgula no\n formato '00.000,00' são aceitos!");
                 valorinicialmoedas = ftxtPassagemmoedasreservadas.getText(); //JOptionPane.showInputDialog(null, "Digite o valor em moédas para início de caixa.\n Somente números, ponto e vírgula no\n formato '00.000,00' são aceitos!");
                 try{
                     valorinicialn = Float.parseFloat(valorinicialcedula.replaceAll("\\.", "").replaceAll(",","."));
                     valorinicialcedula = obj_formato.format(valorinicialn);
                     
                     valorinicialn = Float.parseFloat(valorinicialmoedas.replaceAll("\\.", "").replaceAll(",","."));
                     valorinicialmoedas = obj_formato.format(valorinicialn);
                     RefazerConexao refc2 = new RefazerConexao();
                     refc2.refazerconexao();
                     List<Datas> selecionadatahoje2 = new ArrayList<>();
                     MovimentoDAO movdao2 = new MovimentoDAO();
                     selecionadatahoje2 = movdao2.selecionardata(agora);
                        for(Datas datas : selecionadatahoje2){
                            iddata = datas.getId();
                            datahoje = datas.getData();
                        }
                     RefazerConexao refc3 = new RefazerConexao();
                     refc3.refazerconexao();
                     movdao.salvar_ponto_entrada(iddata, idusuario, horaagora, Float.parseFloat(valorinicialcedula.
                                   replaceAll("\\.", "").replaceAll(",",".")), 
                                   Float.parseFloat(valorinicialmoedas.replaceAll("\\.", "").replaceAll(",",".")));
                     RefazerConexao refc4 = new RefazerConexao();
                     refc4.refazerconexao();
                     List<Entradas> selecionaentradahoje = new ArrayList<>();
                     MovimentoDAO movdao3 = new MovimentoDAO();
                     selecionaentradahoje = movdao3.selecionarentrada(agora, idusuario);
                        for(Entradas entradas : selecionaentradahoje){
                            txtAtendentecaixa.setText("Caixa: " + entradas.getUsuario());
                            txtNotasinicio.setText("Notas: " + String.format("%,.2f", entradas.getValorinicialcedula()));
                            txtMoedasinicio.setText("Moedas: " + String.format("%,.2f", entradas.getValorinicialmoedas()));
                            txtCaixainicial.setText("Início: " + String.format("%,.2f", entradas.getValorinicialcedula() + entradas.getValorinicialmoedas()));  
                            frmmovimento.recebemovidponto(entradas.getIdponto(), entradas.getIddata());
                            
                            SelecionandoReservaDeCaixa selecionandoreservadecaixa = new SelecionandoReservaDeCaixa();
                            selecionandoreservadecaixa.SelecionandoReservaDeCaixa(agora, entradas.getIdponto(), iddata);
                        }
                        RefazerConexao refc11 = new RefazerConexao();
                        refc11.refazerconexao();
                        MovimentoDAO movdao31 = new MovimentoDAO();
                        txtVendas.setText("Vendas:  " + movdao31.selecionacontagem(iddata));
                        ftxtValor.requestFocus();
                        mnEntrar.setEnabled(false);
                        mnFecharEntrar.setEnabled(false);
                        mnNovousuario.setEnabled(false);
                        mnFecharNovousuario.setEnabled(false);
                        btnEntrar.setEnabled(false);
                        btnLogin.setEnabled(false);
                 }catch(Exception ex){
                     
                     JOptionPane.showMessageDialog(null, "Somente números, ponto e vírgula no\n formato '00.000,00' são aceitos!");
                     mnCaixa.setEnabled(false);
                     mnFecharcaixa.setEnabled(false);
                     btnCaixa.setEnabled(false);            
                     btnRelatorio.setEnabled(false);
                     mnRelatorios.setEnabled(false);
                     mnFecharRelatorios.setEnabled(false);                 
                     mnEntrar.setEnabled(true);
                     mnFecharEntrar.setEnabled(false);
                     mnNovousuario.setEnabled(true);
                     mnFecharNovousuario.setEnabled(false);
                     btnEntrar.setEnabled(true);
                     btnLogin.setEnabled(true);
                     gerenciadordejanelas.fecharjanelas(frmMovimento.getInstancia());
                     frmmovimento.dispose(); 
                 }
                
    }
}
