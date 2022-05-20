/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import modelo.dao.MovimentoDAO;
import produzconexao.ConexaoFirebird;

/**
 *
 * @author Pituba
 */
public class ControleMovimento {
    SimpleDateFormat formatbr = new SimpleDateFormat("dd/MM/yyyy");
    MovimentoDAO movdao;
    
     public ResultSet movimentousuario(int idponto){
    
        Connection con = ConexaoFirebird.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.prepareStatement("select * from MOVIMENTO where MOV_ID_PONTO = ?"
                                        + "order by ID_MOVIMENTO");
            stmt.setInt(1, idponto);
            rs = stmt.executeQuery();
        }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, "Erro: " + ex + " ao tentar selecionar o movimento!");

        }
        if(rs != null){
           return rs;
        }else{
           return null;
        }
    }
     
     public ResultSet movimentodiario(String data){
      
         Connection con = ConexaoFirebird.getConnection();
         PreparedStatement stmt = null;
         ResultSet rs = null;
         
         try{
             java.sql.Date inicio = new java.sql.Date(formatbr.parse(data).getTime());
             
             stmt = con.prepareStatement("select DT.DATA, CF.CONTAGEM, US.USUARIO,MV.HORA, " +
                           "TTV.VENDAS_A_VISTA, TTV.VENDAS_MAIS_ENTR, TTV.SOMAMOVIMENTO, MV.ENTREGA,\n" +
                           "RP.CLIENTE_PAGANTE, RP.COMPETENCIA, MV.RECEBIMENTO_PRAZO, MV.CARTAO, " +
                           "MV.VENDA_AVISTA, MV.VALE, VL.FUNCIONARIO, MV.SAQUE,\n" +
                           "PG.PG_PAGO, PG.EMPRESA, MV.PAGAMENTOS, MV.MOVIMENTO, CD.CAIXA_INICIAL_NOTAS, " +
                           "CD.CAIXA_INICIAL_MOEDAS, CD.CAIXA_INICIAL_TOTAL\n" +
                           "from MOVIMENTO MV join CARTAO_PONTO CP on MV.MOV_ID_PONTO = CP.ID_PONTO " +
                           "join DATA DT on CP.PT_DATA = DT.ID_DATA " +
                           "join TOTALVEM TTV on TTV.TTV_ID_DATA = DT.ID_DATA " +
                           "left join CAIXA_INICIO_DIA CD on CD.CAIXAINI_ID_DATA = DT.ID_DATA\n" +
                           "join CONTA_FREGUES CF on CF.CONTFREGUES_ID_DATA = DT.ID_DATA\n" +
                           "join USUARIOS US on CP.PT_USUARIO = US.ID left " +
                           "join RECEBIMENTOPRAZO RP on RP.RP_ID_MOVIMENTO = MV.ID_MOVIMENTO\n" +
                           "left join VALES VL on VL.VL_ID_MOVIMENTO = MV.ID_MOVIMENTO\n" +
                           "left join PAGAMENTOS PG on PG.PG_ID_MOVIMENTO = MV.ID_MOVIMENTO\n" +
                           "where DT.DATA = ? and MV.CAIXAINICIALTOTAL > 0 " +
                           "OR DT.DATA = ? and MV.ENTREGA > 0 " +
                           "OR DT.DATA = ? AND MV.RECEBIMENTO_PRAZO > 0 " +
                           "OR DT.DATA = ? AND MV.CARTAO > 0 " +
                           "OR DT.DATA = ? AND MV.VALE > 0 " +
                           "OR DT.DATA = ? AND MV.SAQUE > 0 " +
                           "OR DT.DATA = ? AND MV.PAGAMENTOS > 0 ");
             stmt.setDate(1, inicio);
             stmt.setDate(2, inicio);
             stmt.setDate(3, inicio);
             stmt.setDate(4, inicio);
             stmt.setDate(5, inicio);
             stmt.setDate(6, inicio);
             stmt.setDate(7, inicio);
             //stmt.setDate(7, inicio);
             //stmt.setDate(8, inicio);
             //stmt.setDate(9, inicio);
             //stmt.setDate(10, inicio);
             rs = stmt.executeQuery();
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, "Erro: " + e + " ao tentar selecionar o movimento do dia!");
         }
            if(rs != null){
               return rs;
            }else{
               return null;
            }
     }
    
}
