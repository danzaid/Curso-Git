    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package izziImpl;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelBusObject;
import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelException;
import dao.ConexionDB;
import dao.ConexionSiebel;
import dao.Reportes;
import interfaces.DAOInterciudades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import objetos.Interciudades;

/**
 *
 * @author hector.pineda
 */
public class DAOInterciudadesImpl extends ConexionDB implements DAOInterciudades {//Modificado

    @Override
    public void inserta(List<Interciudades> interciudades, String it, String fechaIni, String fechaTer,String url,String usuarioconn,String passw,String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception {

        String Conectar = "NO";
        int Contador = 0;
        int Maxima = Integer.parseInt(it);
        
         Boolean conteopadre = interciudades.isEmpty(); // Valida si la lista esta vacia
        if (!conteopadre) {

            ConexionSiebel conSiebel = new ConexionSiebel();
            SiebelDataBean m_dataBean = new SiebelDataBean();
            conSiebel.ConexionSiebel(m_dataBean, "SI",url,usuarioconn,passw);
            
        for (Interciudades sInter : interciudades) {
            if ("SI".equals(Conectar)) {
                conSiebel.ConexionSiebel(m_dataBean, Conectar,url,usuarioconn,passw);
                Conectar = "NO";
            }
            try {
                String MsgError = "";
                SiebelBusObject BO = m_dataBean.getBusObject("TT Interciudades");
                SiebelBusComp BC = BO.getBusComp("TT Interciudades");
                SiebelBusComp oBCPick = new SiebelBusComp();
                BC.activateField("TT Expertise Area");
                BC.activateField("TT RPT Atiende");
                BC.activateField("TT TPR Origen");
                BC.clearToQuery();
                BC.setSearchSpec("TT Expertise Area", sInter.getAreaConocimiento());
                BC.setSearchSpec("TT RPT Atiende", sInter.getCiudadAtiende());
                BC.setSearchSpec("TT TPR Origen", sInter.getCiudadOrigen());
                BC.executeQuery(true);
                boolean reg = BC.firstRecord();
                if (reg) {
                    System.out.println("Se valida existencia y/o actualizacion Interciudades:  " + sInter.getAreaConocimiento() + " : " + sInter.getCiudadAtiende() + " : " + sInter.getCiudadOrigen());
                    String MsgSalida = "Se valida existencia y/o actualizacion de Interciudades";
                    Reportes rep = new Reportes();
                    rep.agregarTextoAlfinal(MsgSalida);

                    this.CargaBitacoraSalidaValidado(sInter.getRowId(), MsgSalida, "Y", MsgError,usuario,version,ambienteInser,ambienteExtra);

                    BC.release();
                    BO.release();
                } else {
                    BC.newRecord(0);
                    if (sInter.getAreaConocimiento() != null) {
                        oBCPick = BC.getPicklistBusComp("TT Expertise Area");
                        oBCPick.clearToQuery();
                        oBCPick.setSearchSpec("Value", sInter.getAreaConocimiento());
                        oBCPick.executeQuery(true);
                        if (oBCPick.firstRecord()) {
                            oBCPick.pick();
                        }
                        oBCPick.release();
                    }

                    if (sInter.getCiudadAtiende() != null) {
                        oBCPick = BC.getPicklistBusComp("TT RPT Atiende");
                        oBCPick.clearToQuery();
                        oBCPick.setSearchSpec("Value", sInter.getCiudadAtiende());
                        oBCPick.executeQuery(true);
                        if (oBCPick.firstRecord()) {
                            oBCPick.pick();
                        }
                        oBCPick.release();
                    }

                    if (sInter.getCiudadOrigen() != null) {
                        oBCPick = BC.getPicklistBusComp("TT TPR Origen");
                        oBCPick.clearToQuery();
                        oBCPick.setSearchSpec("Value", sInter.getCiudadOrigen());
                        oBCPick.executeQuery(true);
                        if (oBCPick.firstRecord()) {
                            oBCPick.pick();
                        }
                        oBCPick.release();
                    }

                    BC.writeRecord();

                    System.out.println("Se crea registro de Interciudades: " + sInter.getAreaConocimiento() + " : " + sInter.getCiudadAtiende() + " : " + sInter.getCiudadOrigen());
                    String mensaje = ("Se crea registro de Interciudades: " + sInter.getAreaConocimiento() + " : " + sInter.getCiudadAtiende() + " : " + sInter.getCiudadOrigen());

                    String MsgSalida = "Creado Interciudades";
                    this.CargaBitacoraSalidaCreado(sInter.getRowId(), MsgSalida, "Y", MsgError,usuario,version,ambienteInser,ambienteExtra);

                    Reportes rep = new Reportes();
                    rep.agregarTextoAlfinal(mensaje);

                    BC.release();
                    BO.release();
                }

            } catch (SiebelException e) {
                String error = e.getErrorMessage();
                System.out.println("Error en creacion Interciudades:  " + sInter.getAreaConocimiento() + " : " + sInter.getCiudadAtiende() + " : " + sInter.getCiudadOrigen() + "     , con el mensaje:   " + error.replace("'", " "));
                String MsgSalida = "Error al Crear Interciudades";
                String FlagCarga = "E";
                String MsgError = "Error en creacion Interciudades:  " + sInter.getAreaConocimiento() + " : " + sInter.getCiudadAtiende() + " : " + sInter.getCiudadOrigen() + "     , con el mensaje:   " + error.replace("'", " ");

                this.CargaBitacoraSalidaError(sInter.getRowId(), MsgSalida, FlagCarga, MsgError,usuario,version,ambienteInser,ambienteExtra);
                Reportes rep = new Reportes();
                rep.agregarTextoAlfinal(MsgError);
            } finally {
                Contador++;
//                System.out.println("Contador =  " + Contador);

                if (Contador == Maxima) {
                    conSiebel.CloseSiebel(m_dataBean);
                    Contador = 0;
                    Conectar = "SI";
//                    System.out.println("Se inicia contador a =  " + Contador);
                }
            }
        }
        }
    }

    @Override
    public List<Interciudades> consultaPadre(String fechaI, String fechaT,String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception {

        String readRecordSQL = "SELECT A.ROW_ID, A.X_EXPERTISE_AREA \"Area de conocimiento\", A.X_TT_RPT_ATIENDE \"Ciudad que atiende\", A.X_TT_RPT_ORIGEN \"Ciudad de origen\"\n"
                + "FROM SIEBEL.CX_TT_INTERCD A, SIEBEL.S_USER H\n"
                + "WHERE H.ROW_ID = A.LAST_UPD_BY\n"
                + "AND A.LAST_UPD BETWEEN TO_DATE ('" + fechaI + "', 'MM/dd/yyyy') AND TO_DATE ('" + fechaT + "', 'MM/dd/yyyy') +1\n"
                + "AND H.LOGIN ='" + usuario + "'\n"
                + "AND NOT EXISTS (SELECT ROW_ID FROM SIEBEL.CT_BITACORA D WHERE D.ROW_ID = A.ROW_ID AND D.USUARIO ='" + usuario + "' AND D.VERSION ='" + version + "' AND D.EXTRAER ='" + ambienteExtra + "' AND D.INSERTAR ='" + ambienteInser + "')\n"
                + "ORDER BY A.LAST_UPD ASC";

        List<Interciudades> interciudades = new LinkedList();

        try (PreparedStatement ps = conexion.prepareStatement(readRecordSQL); ResultSet rs = ps.executeQuery()) {
            System.out.println("Creando Lista de registros a procesar.");

            while (rs.next()) {
                Interciudades lista = new Interciudades();
                lista.setRowId(rs.getString("ROW_ID"));
                lista.setAreaConocimiento(rs.getString("Area de conocimiento"));
                lista.setCiudadAtiende(rs.getString("Ciudad que atiende"));
                lista.setCiudadOrigen(rs.getString("Ciudad de origen"));
                interciudades.add(lista);
                this.CargaBitacoraEntrada(rs.getString("ROW_ID"), rs.getString("Area de conocimiento"), rs.getString("Ciudad que atiende"), rs.getString("Ciudad de origen"), "SE OBTIENEN DATOS",usuario,version,ambienteInser,ambienteExtra);

            }
            int Registros = interciudades.size();
            Boolean conteo = interciudades.isEmpty(); // Valida si la lista esta vacia
            String mensaje = "";
            if (conteo) {
                System.out.println("No se obtuvieron listas de Interciudades o estas ya fueron procesadas.");
                mensaje = "No se obtuvieron listas de Interciudades o estas ya fueron procesadas.";
            } else {
                System.out.println("Se obtiene listas de Interciudades, se procesaran: " + Registros + " registros.");
                mensaje = "Se obtiene listas de Interciudades, se procesaran: " + Registros + " registros.";
            }
            Reportes rep = new Reportes();
            rep.agregarTextoAlfinal(mensaje);

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error en ConsultaPadre, con el error:  " + ex);
            throw ex;
        }
        return interciudades;
    }

    private void CargaBitacoraEntrada(String Row_Id, String Val1, String Val2, String Val3, String Seguimiento,String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception {

        String TipoObjeto = Val1 + " : " + Val2 + " : " + Val3;

        String searchRecordSQL1 = "SELECT ROW_ID FROM SIEBEL.CT_BITACORA WHERE ROW_ID = '" + Row_Id + "' AND USUARIO = '" + usuario + "' AND VERSION = '" + version + "' AND EXTRAER = '" + ambienteExtra + "' AND INSERTAR = '" + ambienteInser + "'";

        try (PreparedStatement ps = conexion.prepareStatement(searchRecordSQL1); ResultSet rs1 = ps.executeQuery()) {
            if (rs1.next()) {
                // sin accion
            } else {
                String setRecordSQL2 = "INSERT INTO SIEBEL.CT_BITACORA  (ROW_ID, TIPO_CATALOGO, TIPO_OBJETO, FLAG_CARGA, SEGUIMIENTO, MENSAJE_ERROR,USUARIO, VERSION, EXTRAER, INSERTAR)\n"
                        + "VALUES('" + Row_Id + "','INTERCIUDADES','" + TipoObjeto + "','N','" + Seguimiento + "','','" + usuario + "','" + version + "','" + ambienteExtra + "','" + ambienteInser + "')";

                PreparedStatement ps1 = conexion.prepareStatement(setRecordSQL2);
                ps1.executeUpdate();
                ps1.close();
            }
            rs1.close();
            ps.close();

        }
    }

    private void CargaBitacoraSalidaCreado(String rowId, String MsgSalida, String FlagCarga, String MsgError,String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception {

        String readRecordSQL4 = "UPDATE SIEBEL.CT_BITACORA SET FLAG_CARGA = '" + FlagCarga + "', SEGUIMIENTO = '" + MsgSalida + "', MENSAJE_ERROR = '" + MsgError + "'\n"
                + "WHERE ROW_ID = '" + rowId + "' AND USUARIO = '" + usuario + "' AND VERSION = '" + version + "' AND EXTRAER = '" + ambienteExtra + "' AND INSERTAR = '" + ambienteInser + "' ";

        PreparedStatement ps3 = conexion.prepareStatement(readRecordSQL4);
        ps3.executeUpdate();
        ps3.close();
    }

    private void CargaBitacoraSalidaValidado(String rowId, String MsgSalida, String FlagCarga, String MsgError,String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception {

        String searchRecordSQL3 = "SELECT ROW_ID FROM SIEBEL.CT_BITACORA \n"
                + "WHERE FLAG_CARGA= 'Y' AND SEGUIMIENTO LIKE '%Creado%' AND ROW_ID = '" + rowId + "' AND USUARIO = '" + usuario + "' AND VERSION = '" + version + "' AND EXTRAER = '" + ambienteExtra + "' AND INSERTAR = '" + ambienteInser + "'";

        PreparedStatement ps3 = conexion.prepareStatement(searchRecordSQL3);
        if (ps3.executeQuery().next()) {
            // sin accion
        } else {
            String readRecordSQL5 = "UPDATE SIEBEL.CT_BITACORA SET FLAG_CARGA = '" + FlagCarga + "', SEGUIMIENTO = '" + MsgSalida + "', MENSAJE_ERROR = '" + MsgError + "'\n"
                    + "WHERE ROW_ID = '" + rowId + "'";

            PreparedStatement ps4 = conexion.prepareStatement(readRecordSQL5);
            ps4.executeUpdate();
            ps4.close();
        }
        ps3.close();
    }

    private void CargaBitacoraSalidaError(String rowId, String MsgSalida, String FlagCarga, String MsgError,String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception {

        String readRecordSQL6 = "UPDATE SIEBEL.CT_BITACORA SET FLAG_CARGA = '" + FlagCarga + "', SEGUIMIENTO = '" + MsgSalida + "', MENSAJE_ERROR = '" + MsgError + "'\n"
                + "WHERE ROW_ID = '" + rowId + "' AND USUARIO = '" + usuario + "' AND VERSION = '" + version + "' AND EXTRAER = '" + ambienteExtra + "' AND INSERTAR = '" + ambienteInser + "'";

        PreparedStatement ps5 = conexion.prepareStatement(readRecordSQL6);
        ps5.executeUpdate();
        ps5.close();
    }

 @Override
    public void getHora() {
        try {
            Calendar now = Calendar.getInstance();

            System.out.println("Current full date time is : " + (now.get(Calendar.MONTH) + 1) + "-"
                    + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR) + " "
                    + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":"
                    + now.get(Calendar.SECOND) + "." + now.get(Calendar.MILLISECOND));
        } catch (Exception e) {
            throw e;
        }

        try {
            Calendar now = Calendar.getInstance();

            String hora = ("" + (now.get(Calendar.MONTH) + 1) + "-"
                    + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR) + " "
                    + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":"
                    + now.get(Calendar.SECOND) + "." + now.get(Calendar.MILLISECOND));
            Reportes rep = new Reportes();
            rep.agregarTextoAlfinal(hora);
        } catch (Exception e) {
            throw e;
        }
    }


}


