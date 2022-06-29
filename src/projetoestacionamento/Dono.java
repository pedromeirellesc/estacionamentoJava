package projetoestacionamento;

import java.sql.ResultSet;

public class Dono extends Funcionario {

    public Dono(String user, String senha) {
        super(user, senha);
    }

    public void relatorioAndamento() {
        String sql = "select count(*) from estadia where encerrado = 0;";
        ResultSet rs = Conexao.consultar(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    int somaEst = rs.getInt(1);
                    System.out.println("Existe(m), no momento, " + somaEst + " estadia(s) em andamento!");
                }
            } catch (Exception e) {
            }
        }
    }

    public void relatorioValor() {
        String sql = "SELECT SUM(valor) as somaValor FROM estadia";
        ResultSet rs = Conexao.consultar(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    double somaValor = rs.getDouble(1);
                    System.out.println("Até o momento, a soma do valor de todas as estadias resulta em: R$ " + somaValor);
                }
            } catch (Exception e) {
            }
        }
    }

    public void relatorioEstadia() {
        String sql = "select count(*) from estadia;";
        ResultSet rs = Conexao.consultar(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    int somaEst = rs.getInt(1);
                    System.out.println("Existem um total de " + somaEst + " estadias cadastradas no total");
                }
            } catch (Exception e) {
            }
        }
    }
}
