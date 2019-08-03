/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelException;
import java.util.List;
import objetos.SeqDescAgregacionHijo;
import objetos.SeqDescAgregacionPadre;
import objetos.SeqDescAgregacionSoloHijo;

/**
 *
 * @author Felipe Gutierrez
 */
public interface DAOSeqDescAgregacion {
    public void inserta(List <SeqDescAgregacionPadre> seqDescAgregacion, String it, String fechaIni, String fechaTer,String url,String usuarioconn,String passw, String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception;
    public List <SeqDescAgregacionPadre> consultaPadre(String fechaI, String fechaT,String usuario,String version,String ambienteInser,String ambienteExtra)throws Exception; 
    public List <SeqDescAgregacionHijo> consultaHijo(String IdPadre, String usuario,String version,String ambienteInser,String ambienteExtra)throws Exception;   
    public List <SeqDescAgregacionSoloHijo> consultaSoloHijo(String fechaI, String fechaT,String usuario,String version,String ambienteInser,String ambienteExtra)throws Exception;
    public void cargaBC(SiebelBusComp BC, SiebelBusComp oBCPick,String RowID, String IdPadre, String usuario,String version,String ambienteInser,String ambienteExtra)throws SiebelException;
    public void getHora();
}
