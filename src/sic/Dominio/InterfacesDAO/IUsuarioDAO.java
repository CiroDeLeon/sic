/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Dominio.Servicios.Dian.Municipio;
import sic.Dominio.Servicios.Dian.TipoDocumento;
import sic.Dominio.Core.Usuario.Usuario;

/**
 *
 * @author FANNY BURGOS
 */
public interface IUsuarioDAO {
    Usuario PersistirUsuario(Usuario usuario);
    Usuario ObtenerUsuario(long nodocumento,Object idTipoDocumento);    
    Usuario ObtenerUsuario(Object idUsuario);    
    Usuario ModificarUsuario(Object id,Usuario u);
    Vector<Usuario> ObtenerUsuarios(String busqueda);    
}
