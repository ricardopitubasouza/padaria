/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produzconexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.GuardarUrl;
import view.frmPrincipal;

/**
 *
 * @author Pituba
 */
public class RefazerConexao {
    public void refazerconexao(){
             
                 Connection conectado = ConexaoFirebird.getConnection();
       
        try {
            if(conectado.isClosed()){
                GuardarUrl guardarurl = new GuardarUrl();
                String resultado = guardarurl.GetProp("conectar");
                String ip = guardarurl.GetProp("IP");
                ip = "localhost";
                if(resultado != null){
                    ConexaoFirebird conect = new ConexaoFirebird(resultado, ip);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
             
             }
}
