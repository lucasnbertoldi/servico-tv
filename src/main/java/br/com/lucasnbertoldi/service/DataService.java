package br.com.lucasnbertoldi.service;

import br.com.lucasnbertoldi.exception.DataHoraException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Bertoldi Engenharia
 */
public class DataService {

    private final String formatoPadrao = "dd/MM/yyyy";

    public Date getDataSistema() {

        Calendar calendario = new GregorianCalendar();
        Date dataSistema = calendario.getTime();
        return dataSistema;

    }

    public Date getDataMesFinal(int mes, int ano) {

        Calendar calendario = new GregorianCalendar();
        calendario.set(GregorianCalendar.MONTH, mes - 1);
        calendario.set(GregorianCalendar.YEAR, ano);
        calendario.set(GregorianCalendar.DAY_OF_MONTH, calendario.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        return calendario.getTime();

    }

    public Date getDataMesInicial(int mes, int ano) {

        Calendar calendario = new GregorianCalendar();
        calendario.set(GregorianCalendar.MONTH, mes - 1);
        calendario.set(GregorianCalendar.YEAR, ano);
        calendario.set(GregorianCalendar.DAY_OF_MONTH, calendario.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));

        return calendario.getTime();
    }

    public Date getDataAnoInicial(int ano) {

        Calendar calendario = new GregorianCalendar();
        calendario.set(GregorianCalendar.MONTH, 0);
        calendario.set(GregorianCalendar.YEAR, ano);
        calendario.set(GregorianCalendar.DAY_OF_MONTH, calendario.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));

        return calendario.getTime();
    }

    public Date getDataAnoFinal(int ano) {

        Calendar calendario = new GregorianCalendar();
        calendario.set(GregorianCalendar.MONTH, 11);
        calendario.set(GregorianCalendar.YEAR, ano);
        calendario.set(GregorianCalendar.DAY_OF_MONTH, calendario.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        return calendario.getTime();

    }

    public Date getData(int dia, int mes, int ano) {

        Calendar calendario = new GregorianCalendar();
        calendario.set(GregorianCalendar.MONTH, mes - 1);
        calendario.set(GregorianCalendar.YEAR, ano);
        calendario.set(GregorianCalendar.DAY_OF_MONTH, dia);
        return calendario.getTime();
    }

    public int getQuantidadeMesesEntre(Date dataInicio, Date dataFinal) throws IllegalArgumentException {
        if (dataFinal.before(dataInicio)) {
            throw new DataHoraException(formatarData(dataInicio, "dd/MM/yyyy"), formatarData(dataFinal, "dd/MM/yyyy"));
        } else {
            Calendar dInicio = new GregorianCalendar();
            Calendar dFinal = new GregorianCalendar();
            dInicio.setTime(dataInicio);
            dFinal.setTime(dataFinal);
            int inicio = (dInicio.get(Calendar.YEAR) * 12) + dInicio.get(Calendar.MONTH);
            int fim = (dFinal.get(Calendar.YEAR) * 12) + dFinal.get(Calendar.MONTH);
            return fim - inicio;

        }
    }

    public int getQuantidadeDiasEntre(Date dataInicio, Date dataFinal) throws IllegalArgumentException {
        if (dataFinal.before(dataInicio)) {
            throw new DataHoraException(formatarData(dataInicio, "dd/MM/yyyy"), formatarData(dataFinal, "dd/MM/yyyy"));
        } else {
            Calendar dInicio = new GregorianCalendar();
            Calendar dFinal = new GregorianCalendar();
            dInicio.setTime(dataInicio);
            dFinal.setTime(dataFinal);

            DateTime inicio = new DateTime(dInicio.get(Calendar.YEAR),
                    dInicio.get(Calendar.MONTH) + 1,
                    dInicio.get(Calendar.DAY_OF_MONTH),
                    0, 0);
            DateTime fim = new DateTime(dFinal.get(Calendar.YEAR),
                    dFinal.get(Calendar.MONTH) + 1,
                    dFinal.get(Calendar.DAY_OF_MONTH),
                    0, 0);

            return Days.daysBetween(inicio, fim).getDays() + 1;
        }
    }

    public Date diminuirDias(Date data, int dias) {
        Calendar d = new GregorianCalendar();
        d.setTime(data);
        d.set(Calendar.DAY_OF_MONTH, d.get(Calendar.DAY_OF_MONTH) - dias);
        return d.getTime();
    }

    public List<Date> dividirDiasPeriodo(Date dataInicio, Date dataFinal, int vezes) throws IllegalArgumentException {
        if (getQuantidadeDiasEntre(dataInicio, dataFinal) < vezes) {
            throw new IllegalArgumentException("Número de dias menor que o numero de vezes");
        }
        int diasPeriodo
                = Math.round(
                        Float.parseFloat(getQuantidadeDiasEntre(dataInicio, dataFinal) + "")
                        / Float.parseFloat(vezes + ""));
        List<Date> datas = new ArrayList<>();
        datas.add(dataInicio);
        for (int i = 1; i <= vezes - 2; i++) {

            datas.add(somarDias(dataInicio, diasPeriodo * i));
        }
        datas.add(dataFinal);

        return datas;
    }

    public Date somarDias(Date data, int dias) {
        Calendar d = new GregorianCalendar();
        d.setTime(data);
        d.set(Calendar.HOUR_OF_DAY, 0);
        d.set(Calendar.MINUTE, 0);
        d.set(Calendar.SECOND, 0);
        d.set(Calendar.DAY_OF_MONTH, d.get(Calendar.DAY_OF_MONTH) + dias);
        return d.getTime();
    }

    public Date somarMeses(Date data, int meses) {
        Calendar d = new GregorianCalendar();
        d.setTime(data);
        d.add(Calendar.MONTH, meses);
        return d.getTime();
    }

    public String formatarData(Date data, String formato) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(formato);
            return f.format(data);
        } catch (IllegalArgumentException e) {
            throw new DataHoraException("Impossivel converter a data.", e);
        }
    }

    public String formatarData(Date data) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(formatoPadrao);
            return f.format(data);
        } catch (IllegalArgumentException e) {
            throw new DataHoraException("Impossivel converter a data.", e);
        }
    }

    public boolean dataIgual(Date data1, Date data2) {
        return formatarData(data2, "yyyy-MM-dd").equals(formatarData(data1, "yyyy-MM-dd"));
    }

    public boolean dataHoraIgual(Date data1, Date data2) {
        return formatarData(data2, "yyyy-MM-dd HH:mm:ss").equals(formatarData(data2, "yyyy-MM-dd HH:mm:ss"));
    }

    public boolean dataEntre(Date data, Date dataInicial, Date dataFinal) {
        Calendar dataC = new GregorianCalendar();
        dataC.setTime(data);
        dataC.set(Calendar.HOUR_OF_DAY, 0);
        dataC.set(Calendar.MINUTE, 0);
        dataC.set(Calendar.SECOND, 0);
        Calendar dataInicialC = new GregorianCalendar();
        dataInicialC.setTime(dataInicial);
        dataInicialC.set(Calendar.HOUR_OF_DAY, 0);
        dataInicialC.set(Calendar.MINUTE, 0);
        dataInicialC.set(Calendar.SECOND, 0);
        Calendar dataFinalC = new GregorianCalendar();
        dataFinalC.setTime(dataFinal);
        dataFinalC.set(Calendar.HOUR_OF_DAY, 0);
        dataFinalC.set(Calendar.MINUTE, 0);
        dataFinalC.set(Calendar.SECOND, 0);

        return somarDias(dataC.getTime(), 1).after(dataInicialC.getTime()) && dataC.getTime().before(dataFinalC.getTime());
    }

    public int getMes(Date data) {
        Calendar dataC = new GregorianCalendar();
        dataC.setTime(data);
        return dataC.get(Calendar.MONTH) + 1;
    }

    public int getDia(Date data) {
        Calendar dataC = new GregorianCalendar();
        dataC.setTime(data);
        return dataC.get(Calendar.DAY_OF_MONTH);
    }

    public int getAno(Date data) {
        Calendar dataC = new GregorianCalendar();
        dataC.setTime(data);
        return dataC.get(Calendar.YEAR);
    }

    public Date converterStringDate(String data, String formato) {
        SimpleDateFormat f = new SimpleDateFormat(formato);
        try {
            return f.parse(data);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public boolean dataMenor(Date dataReferencia, Date dataVerificar) {
        Calendar dataV = new GregorianCalendar();
        dataV.setTime(dataVerificar);
        dataV.set(Calendar.HOUR_OF_DAY, 0);
        dataV.set(Calendar.MINUTE, 0);
        dataV.set(Calendar.SECOND, 0);
        Calendar dataR = new GregorianCalendar();
        dataR.setTime(dataReferencia);
        dataR.set(Calendar.HOUR_OF_DAY, 0);
        dataR.set(Calendar.MINUTE, 0);
        dataR.set(Calendar.SECOND, 0);
        return dataV.getTime().before(dataR.getTime());
    }

    public boolean dataHoraMenor(Date dataReferencia, Date dataVerificar) {
        Calendar dataV = new GregorianCalendar();
        dataV.setTime(dataVerificar);
        Calendar dataR = new GregorianCalendar();
        dataR.setTime(dataReferencia);
        return dataV.getTime().before(dataR.getTime());
    }

    public Date getData(Date data) {
        Calendar dataC = new GregorianCalendar();
        dataC.setTime(data);
        dataC.set(Calendar.HOUR_OF_DAY, 0);
        dataC.set(Calendar.MINUTE, 0);
        dataC.set(Calendar.SECOND, 0);
        return dataC.getTime();
    }

    public boolean dataMaior(Date dataReferencia, Date dataVerificar) {
        Calendar dataV = new GregorianCalendar();
        dataV.setTime(dataVerificar);
        dataV.set(Calendar.HOUR_OF_DAY, 0);
        dataV.set(Calendar.MINUTE, 0);
        dataV.set(Calendar.SECOND, 0);
        Calendar dataR = new GregorianCalendar();
        dataR.setTime(dataReferencia);
        dataR.set(Calendar.HOUR_OF_DAY, 0);
        dataR.set(Calendar.MINUTE, 0);
        dataR.set(Calendar.SECOND, 0);
        return dataV.getTime().after(dataR.getTime());
    }

    public boolean dataMaiorIgual(Date dataReferencia, Date dataVerificar) {
        Calendar dataV = new GregorianCalendar();
        dataV.setTime(dataVerificar);
        dataV.set(Calendar.HOUR_OF_DAY, 0);
        dataV.set(Calendar.MINUTE, 0);
        dataV.set(Calendar.SECOND, 0);
        Calendar dataR = new GregorianCalendar();
        dataR.setTime(dataReferencia);
        dataR.set(Calendar.HOUR_OF_DAY, 0);
        dataR.set(Calendar.MINUTE, 0);
        dataR.set(Calendar.SECOND, 0);
        if (dataIgual(dataR.getTime(), dataV.getTime())) {
            return true;
        }
        return dataV.getTime().after(dataR.getTime());
    }
    
    public int getDiaSemana(Date data) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        return gc.get(GregorianCalendar.DAY_OF_WEEK);
    }
    
    public List<Date> getListaDiasMes(int mes, int ano) {
        
        List<Date> listaDatas = new ArrayList();
        
        Date diaInicioMes = getDataMesInicial(mes, ano);
        Date diaFinalMes = getDataMesFinal(mes, ano);
        
        int intInicio = getDia(diaInicioMes);
        int intFinal = getDia(diaFinalMes);
        
        for (int i = intInicio; i <= intFinal; i++) {
            listaDatas.add(getData(i, mes, ano));
        }
        
        return listaDatas;
        
    }
}
