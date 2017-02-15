/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.InterfacesDAO;

import java.util.Vector;
import sic.Dominio.Core.GeneradorDeInformes.Columna;
import sic.Dominio.Core.GeneradorDeInformes.Consepto;
import sic.Dominio.Core.GeneradorDeInformes.ConseptoEnCuenta;
import sic.Dominio.Core.GeneradorDeInformes.Informe;

/**
 *
 * @author Usuario1
 */
public interface Interface_InformeDao {
    void PersistirInforme(Informe i);
    void PersistirColumna(Columna col);
    void PersistirConsepto(Consepto con);
    void PersistirConseptoEnCuenta(ConseptoEnCuenta cc);
    void ModificarInforme(Informe i);
    void ModificarColumna(Columna col);
    void ModificarConsepto(Consepto con);    
    void ModificarConseptoEnCuenta(ConseptoEnCuenta cc);    
    void EliminarInforme(Informe i);
    void EliminarColumna(Columna col);
    void EliminarConsepto(Consepto con);    
    void EliminarConseptoEnCuenta(ConseptoEnCuenta cc);
    Vector<Consepto> ObtenerConseptos(long id_informe);
    Vector<ConseptoEnCuenta> ObtenerCuentas(long id_consepto);
    Vector<Columna> ObtenerColumnas(long id_informe);
    Vector<Informe> ObtenerInformes(String busqueda);
    Informe ObtenerUltimoInforme();    
}
