/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sic.Dominio.InterfacesDAO;

import java.util.Date;
import java.util.Vector;
import sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto;
import sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos;

/**
 *
 * @author Usuario1
 */
public interface IEstructuraDeCostosDAO {
   void Persistir(EstructuraDeCostos e);    
   long UltimaEstrucutraDeCostos();
   long UltimoCentroDeCostos();
   Vector<sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos> BusquedaIncremental(String busqueda);
   Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> ObtenerCentrosDeCostos(long idEstructuraDeCostos);
   Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> ObtenerCentrosDeCostos(long idEstructuraDeCostos,String busqueda);
   sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto ObtenerCentroDeCosto(long id);
   sic.Dominio.Core.EstructuraDeCostos.EstructuraDeCostos ObtenerEstructuraDeCostos(long id);
   void Modificar(long id,EstructuraDeCostos e);
   void Persistir(CentroDeCosto obj);
   void Modificar(long id,CentroDeCosto obj);
   Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> ObtenerHijos(long idCentroDeCostos);
   Vector<sic.Dominio.Core.EstructuraDeCostos.CentroDeCosto> ObtenerPadres(long idEstructuraDeCostos);
   double ObtenerSaldo(long idCentroDeCosto,long idSubcontabilidad,Date fecha_inicial,Date fecha_final);
   double ObtenerSaldoEstructuraDeCosto(long idEstructuraDeCosto,long idSubcontabilidad,Date fecha_inicial,Date fecha_final);
   Vector<sic.Dominio.Core.EstructuraDeCostos.dto.CentroDeCostoDTO_Saldo> ObtenerSaldosDeCentroDeCostoPorCuentaT(long idCentroDeCosto,long idSubcontabilidad,Date fecha_inicial,Date fecha_final);
   Vector<sic.Dominio.Core.EstructuraDeCostos.dto.ReporteAsientoPorCentroDeCosto> ObtenerReporte(long idCentroDeCosto,long idSubcontabilidad,long id_cuenta_t,Date fecha_inicial,Date fecha_final);
   
}
