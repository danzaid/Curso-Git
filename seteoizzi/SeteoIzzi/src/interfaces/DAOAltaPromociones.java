/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.siebel.data.SiebelBusComp;
import com.siebel.data.SiebelException;
import java.util.List;
import objetos.AltaPromociones;

/**
 *
 * @author alexis.alamilla
 */
public interface DAOAltaPromociones {
    public void inserta(List <AltaPromociones> altaPromociones, String it, String fechaIni, String fechaTer,String url,String usuarioconn,String passw,String usuario,String version,String ambienteInser,String ambienteExtra) throws Exception;
    public List <AltaPromociones> consultaPadre(String fechaI, String fechaT,String usuario,String version,String ambienteInser,String ambienteExtra)throws Exception;
    public void getHora();   
}
