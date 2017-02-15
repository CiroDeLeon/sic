/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sic.Dominio.Servicios.Dian;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import sic.Dominio.Core.Usuario.Usuario;
import sic.Dominio.Core.Usuario.UsuarioService;
import sic.Infraestructura.JDBC.Impl.Mysql.DianDAO;
import sic.Dominio.InterfacesDAO.IDianDAO;
import sic.Dominio.Servicios.Dian.exogena.Formato1001;
import sic.Dominio.Servicios.Dian.exogena.helper.Formato1001Helper;

/**
 *
 * @author FANNY BURGOS
 */
public class DianService {
    private IDianDAO dao;
    Vector<Actividad> v;
    private String mensaje="";
    public DianService() {
        dao=new DianDAO();
        v=new Vector<Actividad>();
    }    
    public  boolean InsertarTipoDocumento(Object id,String descripcion,String abreviatura){
        if(getDao().ObtenerTipoDocumento(id)==null){
            if(getDao().ObtenerTipoDocumento(abreviatura)==null){
                if(getDao().ObtenerTipoDocumento(abreviatura)==null){
                   TipoDocumento td=new TipoDocumento();
                   td.setId(id);
                   td.setAbreviatura(abreviatura);
                   td.setDescripcion(descripcion);
                   if(getDao().PersistirTipoDocumento(td)!=null){
                      mensaje="Tipo de Documento Ingresado Con EXITO"; 
                      return true;    
                   }else{
                      mensaje="Hubo Un Error";  
                      return false;
                   }                   
                }else{
                    mensaje="La abreviatura debe ser de Maximo dos letras";
                    return false;
                 }   
            }else{
                mensaje="Esa Abreviatura ya esta en Uso";
                return false;
            }
        }else{
            mensaje="Ya Existe Ese Tipo de Documento";
            return false;
        }
    }
    public  boolean InsertarMunicipio(Object id,String descripcion,int iddpto,String desdpto,int idpais,String despais){
        if(getDao().ObtenerMunicipio(id)==null){
            Municipio m=new Municipio();
            m.setId(id);
            m.setDescripcion(descripcion);
            m.setIddpto(iddpto);
            m.setDescripciondpto(desdpto);
            m.setIdpais(idpais);
            m.setDescripcionpais(despais);
            if(getDao().PersistirMunicipio(m)!=null){
                mensaje="Ingresado el Municipio con EXITO";
                return true;
            }else{
                mensaje="Hubo un Error";
                return false;
            }
        }else{
            mensaje="Ya Existe Ese Municipio";
            return false;
        } 
    }
    public Vector<TipoDocumento> ObtenerTiposDeDocumentos(String busqueda){        
        return getDao().ObtenerTipoDocumentos(busqueda);
    }     
    public Vector<Municipio> ObtenerMunicipios(String busqueda){
        return getDao().ObtenerMunicipios(busqueda);
    }
    public Vector<NomenclaturaDeDireccion> ObtenerNomenclaturasDeDireccion(){
       Vector <NomenclaturaDeDireccion> lista=new Vector <NomenclaturaDeDireccion>();
       lista.add(new NomenclaturaDeDireccion("AP","Apartamento"));
       lista.add(new NomenclaturaDeDireccion("AV","Avenidad"));
       lista.add(new NomenclaturaDeDireccion("AUT","Autopista"));
       lista.add(new NomenclaturaDeDireccion("BRR","Barrio"));
       lista.add(new NomenclaturaDeDireccion("CL","Calle"));
       lista.add(new NomenclaturaDeDireccion("CR","Carrera"));       
       lista.add(new NomenclaturaDeDireccion("DG","Diagonal"));       
       lista.add(new NomenclaturaDeDireccion("ED","Edificio"));       
       lista.add(new NomenclaturaDeDireccion("NORTE","Norte"));       
       lista.add(new NomenclaturaDeDireccion("SUR","Sur"));       
       lista.add(new NomenclaturaDeDireccion("TV","Transversal")); 
       lista.add(new NomenclaturaDeDireccion("C","Corregimiento"));       
       lista.add(new NomenclaturaDeDireccion("KM","Kilometro"));       
       
       lista.add(new NomenclaturaDeDireccion("AC","Avenidad Calle"));
       lista.add(new NomenclaturaDeDireccion("AD","Administracion"));       
       lista.add(new NomenclaturaDeDireccion("ADL","Adelante"));       
       lista.add(new NomenclaturaDeDireccion("AER","Aeropuerto"));       
       lista.add(new NomenclaturaDeDireccion("AG","Agencia"));       
       lista.add(new NomenclaturaDeDireccion("AGP","Agrupacion"));       
       lista.add(new NomenclaturaDeDireccion("AK","Avenidad Carrera"));       
       lista.add(new NomenclaturaDeDireccion("AL","Altillo"));       
       lista.add(new NomenclaturaDeDireccion("ALD","Al Lado"));       
       lista.add(new NomenclaturaDeDireccion("ALM","Almacen"));       
       lista.add(new NomenclaturaDeDireccion("APTDO","Apartado"));       
       lista.add(new NomenclaturaDeDireccion("ATR","Atras"));       
       lista.add(new NomenclaturaDeDireccion("AVIAL","Anillo Vial"));       
       lista.add(new NomenclaturaDeDireccion("BG","Bodega"));       
       lista.add(new NomenclaturaDeDireccion("BL","Bloque"));       
       lista.add(new NomenclaturaDeDireccion("BLV","Boulevard"));       
       lista.add(new NomenclaturaDeDireccion("CA","Casa"));       
       lista.add(new NomenclaturaDeDireccion("CAS","Caserio"));       
       lista.add(new NomenclaturaDeDireccion("CC","Centro Comercial"));       
       lista.add(new NomenclaturaDeDireccion("CD","Ciudadela"));       
       lista.add(new NomenclaturaDeDireccion("CEL","Celula"));       
       lista.add(new NomenclaturaDeDireccion("CEN","Centro"));       
       lista.add(new NomenclaturaDeDireccion("CIR","Circular"));       
       lista.add(new NomenclaturaDeDireccion("CLJ","Callejon"));       
       lista.add(new NomenclaturaDeDireccion("CN","Camino"));       
       lista.add(new NomenclaturaDeDireccion("CON","Conjunto Residencial"));       
       lista.add(new NomenclaturaDeDireccion("CONJ","Conjunto"));       
       lista.add(new NomenclaturaDeDireccion("CRT","Carretera"));       
       lista.add(new NomenclaturaDeDireccion("CRV","Circunvalar"));       
       lista.add(new NomenclaturaDeDireccion("CS","Consultorio"));       
       lista.add(new NomenclaturaDeDireccion("DP","Deposito"));       
       lista.add(new NomenclaturaDeDireccion("DPTO","Departamento"));       
       lista.add(new NomenclaturaDeDireccion("DS","Deposito Sotano"));       
       lista.add(new NomenclaturaDeDireccion("EN","Entrada"));       
       lista.add(new NomenclaturaDeDireccion("ES","Escalera"));       
       lista.add(new NomenclaturaDeDireccion("ESQ","Esquina"));       
       lista.add(new NomenclaturaDeDireccion("ESTE","Este"));       
       lista.add(new NomenclaturaDeDireccion("ET","Etapa"));       
       lista.add(new NomenclaturaDeDireccion("EX","Exterior"));       
       lista.add(new NomenclaturaDeDireccion("FCA","Finca"));       
       lista.add(new NomenclaturaDeDireccion("GJ","Garaje"));       
       lista.add(new NomenclaturaDeDireccion("GS","Garaje Sotano"));       
       lista.add(new NomenclaturaDeDireccion("GT","Glorieta"));       
       lista.add(new NomenclaturaDeDireccion("HC","Hacienda"));       
       lista.add(new NomenclaturaDeDireccion("HG","Hangar"));       
       lista.add(new NomenclaturaDeDireccion("IN","Interior"));       
       lista.add(new NomenclaturaDeDireccion("IP","Inspeccion de Policia"));       
       lista.add(new NomenclaturaDeDireccion("IPD","Inspeccion Departamental"));       
       lista.add(new NomenclaturaDeDireccion("IPM","Inspeccion Municipal"));       
       lista.add(new NomenclaturaDeDireccion("LC","Local"));       
       lista.add(new NomenclaturaDeDireccion("LM","Local Mezzanine"));       
       lista.add(new NomenclaturaDeDireccion("LT","Lote"));       
       lista.add(new NomenclaturaDeDireccion("MD","Modulo"));       
       lista.add(new NomenclaturaDeDireccion("MJ","Mojon"));       
       lista.add(new NomenclaturaDeDireccion("MLL","Muelle"));       
       lista.add(new NomenclaturaDeDireccion("MN","Mezzanine"));       
       lista.add(new NomenclaturaDeDireccion("MZ","Manzana"));       
       lista.add(new NomenclaturaDeDireccion("O","Oriente"));       
       lista.add(new NomenclaturaDeDireccion("OCC","Occidente"));       
       lista.add(new NomenclaturaDeDireccion("OESTE","Oeste"));       
       lista.add(new NomenclaturaDeDireccion("OF","Oficina"));       
       lista.add(new NomenclaturaDeDireccion("P","Piso"));       
       lista.add(new NomenclaturaDeDireccion("PA","Parcela"));       
       lista.add(new NomenclaturaDeDireccion("PAR","Parque"));       
       lista.add(new NomenclaturaDeDireccion("PD","Predio"));       
       lista.add(new NomenclaturaDeDireccion("PH","Penthouse"));       
       lista.add(new NomenclaturaDeDireccion("PJ","Pasaje"));       
       lista.add(new NomenclaturaDeDireccion("PL","Planta"));       
       lista.add(new NomenclaturaDeDireccion("PN","Puente"));       
       lista.add(new NomenclaturaDeDireccion("POR","Porteria"));       
       lista.add(new NomenclaturaDeDireccion("POS","Poste"));       
       lista.add(new NomenclaturaDeDireccion("PQ","Parqueadero"));       
       lista.add(new NomenclaturaDeDireccion("PRJ","Paraje"));       
       lista.add(new NomenclaturaDeDireccion("PS","Paseo"));       
       lista.add(new NomenclaturaDeDireccion("PT","Puesto"));       
       lista.add(new NomenclaturaDeDireccion("PW","Park Way"));       
       lista.add(new NomenclaturaDeDireccion("RP","Round Point"));       
       lista.add(new NomenclaturaDeDireccion("SA","Salon"));       
       lista.add(new NomenclaturaDeDireccion("SC","Salon Comunal"));       
       lista.add(new NomenclaturaDeDireccion("SD","Salida"));       
       lista.add(new NomenclaturaDeDireccion("SEC","Sector"));       
       lista.add(new NomenclaturaDeDireccion("SL","Solar"));       
       lista.add(new NomenclaturaDeDireccion("SM","Super Manzana"));       
       lista.add(new NomenclaturaDeDireccion("SS","Semisotano"));       
       lista.add(new NomenclaturaDeDireccion("ST","Sotano"));       
       lista.add(new NomenclaturaDeDireccion("SUITE","Suite"));       
       lista.add(new NomenclaturaDeDireccion("TER","Terminal"));       
       lista.add(new NomenclaturaDeDireccion("TERPLN","Terraplen"));       
       lista.add(new NomenclaturaDeDireccion("TO","Torre"));       
       lista.add(new NomenclaturaDeDireccion("TZ","Terraza"));       
       lista.add(new NomenclaturaDeDireccion("UN","Unidad"));       
       lista.add(new NomenclaturaDeDireccion("UR","Unidad Residencial"));       
       lista.add(new NomenclaturaDeDireccion("URB","Urbanizacion"));       
       lista.add(new NomenclaturaDeDireccion("VRD","Vereda"));       
       lista.add(new NomenclaturaDeDireccion("VTE","Variante"));       
       lista.add(new NomenclaturaDeDireccion("ZF","Zona Franca"));       
       lista.add(new NomenclaturaDeDireccion("ZN","Zona"));       
       return lista;
   }    
    public Vector<Formato1001> ObtenerFormato1001(Date fecha_inicial,Date fecha_final){
        Iterator <Formato1001Helper> it=dao.ObtenerFormato1001Helper(fecha_inicial, fecha_final).iterator();
        Vector<Formato1001> lista=new Vector();
        UsuarioService service=new UsuarioService();
        while(it.hasNext()){
            Formato1001Helper f1=it.next();
            Formato1001 f=new Formato1001();
            Usuario u=service.getDao().ObtenerUsuario(f1.getId_cuenta_t());
            if(u!=null){
                f.setTipo_documento(u.getTipodocumento().getId().toString());
                f.setNit(u.getNoDocumento());
                f.setDv(u.getDigitoDIAN());
                f.setP_apellido(u.getApellido());
                f.setS_apellido(u.getsApellido());
                f.setP_nombre(u.getNombre());
                f.setS_nombre(u.getsNombre());
                f.setRazon_social(u.getRazonSocial());
                f.setDireccion(u.getDireccion());
                f.setId_departamento(""+u.getMunicipio().getIddpto());
                f.setId_municipio(u.getMunicipio().getId().toString());
                f.setId_pais(""+u.getMunicipio().getIdpais());
                f.setValor(f1.getValor());
                lista.add(f);
            }else{
                f.setTipo_documento("0");
                f.setNit(0);
                f.setDv("");
                f.setP_apellido("");
                f.setS_apellido("");
                f.setP_nombre("");
                f.setS_nombre("");
                f.setRazon_social(f1.getDescripcion());
                f.setDireccion("CUENTA T");
                f.setId_departamento("====");
                f.setId_municipio("====");
                f.setId_pais("===>");
                f.setValor(f1.getValor());
                lista.add(f);
            }
        }
       return lista; 
    }
    public IDianDAO getDao() {
        return dao;
    }
    public String getMensaje() {
        return mensaje;
    }
}