package projetoestacionamento;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Estadia {

    private int id;
    private Veiculo veiculo;
    private Cliente dono;
    private double valor;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private boolean encerrado;

    public Estadia(Veiculo veiculo, Cliente dono, double valor, LocalDateTime dataEntrada) {
        this.veiculo = veiculo;
        this.dono = dono;
        this.valor = valor;
        this.dataEntrada = dataEntrada;
    }

    public boolean getEncerrado() {
        return encerrado;
    }

    public void setEncerrado(boolean encerrado) {
        this.encerrado = encerrado;
    }

    public Estadia(Veiculo veiculo, Cliente dono, LocalDateTime dataEntrada, double valor, boolean encerrado) {
        this.veiculo = veiculo;
        this.dono = dono;
        this.dataEntrada = dataEntrada;
        this.valor = valor;
        this.encerrado = encerrado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estadia() {
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void finalizarEstadia() {
        String sql = "update estadia set " + " dataSaida = ' " + getDataSaida() + "', encerrado = " + getEncerrado() + ", valor = " + getValor() + " where idEstadia = ' " + getId() + "' ";
        Conexao.executar(sql);
    }

    //Pronto
    public void cadastrar() {
        String sql = "INSERT INTO estadia (codCliente, codVeiculo, dataEntrada, valor, encerrado) VALUES ( " + " '" + this.getDono().getId() + "' ,  " + " '" + this.getVeiculo().getId() + "' ,  " + "  '" + this.getDataEntrada() + "' , " + this.getValor() + " , " + this.getEncerrado() + ")";
        String sql2 = "INSERT INTO cliente_veiculo (codCliente, codVeiculo ) VALUES ( " + " '" + this.getDono().getId() + "' ,  " + " '" + this.getVeiculo().getId() + "' ) ";
        Conexao.executar(sql);
        Conexao.executar(sql2);
    }

    //Pronto
    public static ArrayList<Estadia> getEstadias() {
        ArrayList<Estadia> lista = new ArrayList<>();
        String sql = "select idEstadia, dataEntrada, codCliente, codVeiculo, valor, encerrado from estadia";
        ResultSet rs = Conexao.consultar(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    Veiculo veiculo = new Veiculo();
                    LocalDateTime dataEntrada = rs.getTimestamp(2).toLocalDateTime();
                    cliente.setId(rs.getInt(3));
                    veiculo.setId(rs.getInt(4));
                    double valor = rs.getDouble(5);
                    boolean encerrado = rs.getBoolean(6);
                    Estadia estadia = new Estadia(veiculo, cliente, dataEntrada, valor, encerrado);
                    estadia.setId(rs.getInt("idEstadia"));
                    lista.add(estadia);
                }
            } catch (Exception e) {

            }
        }
        return lista;
    }

    public void editar() {
        String sql = "UPDATE estadia SET  " + " codVeiculo    = '" + this.getVeiculo().getId() + "' ,  " + " codCliente   = '" + this.getDono().getId() + "' ,  " + " valor   = '" + this.getValor() + "' ,  " + " dataEntrada =  '" + this.getDataEntrada() + "'     " + " WHERE idEstadia = " + this.getId();
        Conexao.executar(sql);
    }

    public static void excluir(int idEstadia) {
        String sql = "DELETE FROM estadia WHERE idEstadia = " + idEstadia;
        Conexao.executar(sql);
    }

    public static ArrayList<Estadia> abertas() {
        ArrayList<Estadia> listaAberta = new ArrayList<>();
        String sql = "select idEstadia, dataEntrada, codCliente, codVeiculo, valor from estadia where encerrado = 0";
        ResultSet rs = Conexao.consultar(sql);
        if (rs != null) {

            try {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    Veiculo veiculo = new Veiculo();
                    LocalDateTime dataEntrada = rs.getTimestamp(2).toLocalDateTime();
                    cliente.setId(rs.getInt(3));
                    veiculo.setId(rs.getInt(4));
                    double valor = rs.getDouble(5);
                    Estadia estadia = new Estadia(veiculo, cliente, valor, dataEntrada);
                    estadia.setId(rs.getInt("idEstadia"));
                    listaAberta.add(estadia);
                }
            } catch (Exception e) {

            }
        }
        return listaAberta;
    }

    public void calcularValor() {
        String sql = "select timestampdiff(MINUTE, dataEntrada, dataSaida) as tempo, dataEntrada, dataSaida from estadia where idEstadia = " + this.getId();
        ResultSet rs = Conexao.consultar(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    double minutos = rs.getDouble(1);
                    if (minutos <= 60) {
                        setValor(20);
                    } else if (minutos > 60) {
                        double normal = minutos - 60;
                        double h = normal / 60;
                        double valor = (5 * Math.ceil(h)) + 20;
                        setValor(valor);
                    }

                    System.out.println("O valor da Estadia ficou R$ " + getValor());
                }

            } catch (Exception e) {

            }
        }
    }

    public void dataSaida() {
        String sql = "update estadia set " + " dataSaida = ' " + getDataSaida() + "'";
        Conexao.executar(sql);
    }
}
