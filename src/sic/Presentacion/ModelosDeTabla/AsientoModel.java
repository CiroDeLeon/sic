/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Presentacion.ModelosDeTabla;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import sic.Dominio.Core.Documento.Asiento;
import sic.Dominio.Core.Documento.otros.RegistroDeCausacion;
import sic.Dominio.Core.PUC.Cta_PUC;
import sic.Dominio.Core.PUC.PucService;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Core.Usuario.UsuarioService;
import sic.Dominio.Servicios.Dian.DianService;
import sic.Dominio.Servicios.Dian.RetencionDian;


/**
 *
 * @author FANNY BURGOS
 */
public class AsientoModel extends AbstractTableModel{
    private Vector <Asiento> lista;
    String columns[]={"Cod.Cta","Denominacion","Detalle","C. Costo","Debito","Credito",};

    @Override
    public int getRowCount() {
        return this.getLista().size();
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         Asiento a=this.getLista().get(rowIndex);
         switch(columnIndex){
             case 0: return a.getCtaPuc().getId();
             case 1: if(a.getCtaPuc().getId().toString().length()>8){
                        return "("+new PucService().ObtenerCtaPuc(a.getCtaPuc().getId().toString().substring(0,8)).getDenominacion().toUpperCase()+") "+new PucService().ObtenerCtaPuc(a.getCtaPuc().getId().toString()).getDenominacion();
                    }else{
                        if(a.getCtaPuc().getId().toString().length()==8){
                           return new PucService().ObtenerCtaPuc(a.getCtaPuc().getId().toString().substring(0,8)).getDenominacion().toUpperCase();
                        }else{
                           return new PucService().ObtenerCtaPuc(a.getCtaPuc().getId()).getDenominacion().toUpperCase(); 
                        }
                    }
             case 2: return a.getDetalle();
             case 3: if(a.getCentro_de_costos()!=null){
                        return a.getCentro_de_costos().getDescripcion();
                     }else{
                        return ""; 
                     }
             case 4: return a.getDebito();
             case 5: return a.getCredito();                 
             default: return"";    
         }
    }
     @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 4 : return Double.class;
            case 5 : return Double.class;                             
            default: return String.class;    
        }
    }    
     @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    public Asiento getRow(int rowIndex){
        return getLista().get(rowIndex);
    }
    public void setLista(Vector <Asiento> lista) {
        this.lista = lista;
    }
    public Vector<Asiento> ObtenerResumen(int nivel){
        int n=this.getLista().size();
        int v[];
        v=new int[n];
        for(int i=0;i<n;i++){
            v[i]=0;
        }
        Vector<Asiento> respuesta=new Vector<Asiento>();
        for(int i=0;i<n;i++){
            if(v[i]==0){
               v[i]=1;                
               String actual=getLista().get(i).getCtaPuc().getId().toString().substring(0,nivel);
               double sdebito=getLista().get(i).getDebito();
               double scredito=getLista().get(i).getCredito();
               
               for(int j=i+1;j<n;j++){                
                  String siguiente=getLista().get(j).getCtaPuc().getId().toString().substring(0,nivel);
                  if(actual.equals(siguiente)){
                      v[j]=1;
                      sdebito+=getLista().get(j).getDebito();
                      scredito+=getLista().get(j).getCredito();
                  }
               }                             
               if(sdebito>0){
                  Asiento a;
                  a=new Asiento();
                  a.setCtaPuc(new PucService().ObtenerCtaPuc(actual));
                  a.setBaseIVA(0);
                  a.setBaseRTF(0);
                  a.setEntradas(0);
                  a.setSalidas(0);
                  a.setDetalle("Resumido");
                  a.setNoFactura(0);
                  a.setNoFacturaCompra(0);
                  a.setDebito(sdebito);
                  a.setCredito(0);
                  respuesta.add(a);
               }
               if(scredito>0){
                  Asiento a;
                  a=new Asiento();
                  a.setCtaPuc(new PucService().ObtenerCtaPuc(actual));
                  a.setBaseIVA(0);
                  a.setBaseRTF(0);
                  a.setEntradas(0);
                  a.setSalidas(0);
                  a.setDetalle("Resumido");
                  a.setNoFactura(0);
                  a.setNoFacturaCompra(0);
                  a.setDebito(0);
                  a.setCredito(scredito);
                  respuesta.add(a);                   
               }
            }
        }
        return respuesta;
    }
    public Vector<Asiento> AsignarRegistrosCausables(Vector<RegistroDeCausacion> lista,boolean isAutoretenedor){
        Iterator <RegistroDeCausacion> it=lista.iterator();
        while(it.hasNext()){
            RegistroDeCausacion rc=it.next();
            System.out.println("Tipo : "+rc.getTipo());
            int pos=ExisteRegistroCausable(rc.getTipo());
            if(pos==-1){
                Vector<Asiento> l=rc.ObtenerAsientos();
                this.getLista().add(l.get(0));
                this.getLista().add(l.get(1));
                this.getLista().add(l.get(2));   
            }else{
                Object idAsientoDebito=this.getLista().get(pos).getId();
                Object idAsientoIva=this.getLista().get(pos+1).getId();
                Object idAsientoRTF=this.getLista().get(pos+2).getId();
                Vector<Asiento> l=rc.ObtenerAsientos(idAsientoDebito,idAsientoIva,idAsientoRTF);
                this.getLista().set(pos,l.get(0));
                this.getLista().set(pos+1,l.get(1));
                this.getLista().set(pos+2,l.get(2));
            }
        }
        return (Vector<Asiento>)this.getLista().clone();
    }
    private int ExisteRegistroCausable(int tiporegistro){
        for(int i=0;i<this.getLista().size();i++){
            if(getLista().get(i).getTiporegistro()==tiporegistro && tiporegistro%2==0){
                return i;
            }
        }
        return -1;
    }
    public Vector<RegistroDeCausacion> ObtenerRegistrosCausables(){
        Vector <RegistroDeCausacion> l=new Vector <RegistroDeCausacion>();
        for(int i=0;i<this.getLista().size();i++){
            if(getLista().get(i).getTiporegistro()%2==0){
                UsuarioService us=new UsuarioService();
                DianService ds=new DianService();
                RegistroDeCausacion rc=new RegistroDeCausacion();                
                Asiento debito=getLista().get(i);
                Asiento iva=getLista().get(i+1);
                Asiento rtf=getLista().get(i+2);
                rc.setIdcuenta(debito.getCtaPuc().getId());
                rc.setDebito(debito.getDebito());
                rc.setAux_iva(""+iva.getCtaPuc().getId());
                rc.setAux_rtf(""+rtf.getCtaPuc().getId());
                rc.setP_iva((iva.getDebito()*100)/debito.getDebito());
                rc.setCantidad(debito.getEntradas());
                rc.setTipo(debito.getTiporegistro());
                rc.setNofactura(debito.getNoFacturaCompra());
                rc.setP_rtf((rtf.getCredito()*100)/debito.getDebito());
                String striva=iva.getCtaPuc().getId().toString();
                System.out.println("iva="+striva+" rtf="+rc.getAux_rtf());
                String idUsuario=striva.substring(8,striva.toString().length());
                System.out.println("idUsuario="+idUsuario);
                Usuario u=us.getDao().ObtenerUsuario(idUsuario);                
                rc.setUsuario(u);                
                String idAuxiliar=rtf.getCtaPuc().getId().toString().substring(0,8);
                System.out.println("idAuxiliar="+idAuxiliar+" %="+rc.getP_rtf());
                double porcentage=rc.getP_rtf();
                RetencionDian rd=ds.getDao().ObtenerRetencionDian(idAuxiliar);
                if(rd!=null){
                    rc.setItemtablartf(rd);
                    System.out.println("NO ES NULO JAJAJA");
                }else{
                    System.out.println("SI ES NULO JAJAJA");
                    rd=new RetencionDian();
                    rd.setAuxiliar_retencion(idAuxiliar);
                    rd.setPorcentage(porcentage);
                    rc.setItemtablartf(rd);
                }
                l.add(rc);
                i=i+2;
            }
        }
        return l;
    }    
    public Vector <Asiento> getLista() {
        return lista;
    }
    public void ModificarDetalle(String detalle){
        for(int i=0;i<this.lista.size();i++){
            this.lista.get(i).setDetalle(detalle);
        }
    }
}
