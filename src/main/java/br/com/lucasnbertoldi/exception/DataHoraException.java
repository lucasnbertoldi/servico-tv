package br.com.lucasnbertoldi.exception;

/**
 *
 * @author Argus Computação
 */
public class DataHoraException extends RuntimeException {

    public DataHoraException(String dataInicial, String dataFinal) {
        super("A data final \""+ dataFinal+"\" é maior que a data inicial \""+dataInicial+"\".");
    }
    public DataHoraException(String mensagem) {
        super(mensagem);
    }
    public DataHoraException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
