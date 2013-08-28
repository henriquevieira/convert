package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * @version 0.3.1.1
 * @author henrique
 * 10/09/2011 
 */
public class convert {

    String file_name;
    String separador;
    String special = "";

    /*
     * Input a csv file and create a XML file
     */
    public convert(String separador, String file_name, String special) {

        this.separador = separador;
        this.file_name = file_name;
        this.special = special;

    }
    
    public convert(String separador, String file_name) {

        this.separador = separador;
        this.file_name = file_name;
    }

    public ArrayList read_converted_file() {


        ArrayList p = new ArrayList();

        try {

            File arquivo = new File(file_name);

            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            StringBuffer out = new StringBuffer();

            String line;
            if (separador.equals("-t")) {
                while ((line = reader.readLine()) != null) {

                    String[] split_line = line.split("\t");
                    p.add(split_line);

                }
                reader.close();
            }

            if (separador.equals("-v")) {
                while ((line = reader.readLine()) != null) {

                    String[] split_line = line.split(",");
                    p.add(split_line);

                }
                reader.close();
            }

            if (separador.equals("-p")) {
                while ((line = reader.readLine()) != null) {

                    String[] split_line = line.split("\\|");
                    p.add(split_line);

                }
                reader.close();
            }

            if (separador.equals("-s")) {
                while ((line = reader.readLine()) != null) {

                    String[] split_line = line.split(special);
                    p.add(split_line);

                }
                reader.close();
            }

        } catch (Exception e) {

            System.out.print(e);
        }

        return p;

    }

    /*
     * create a list with questions
     */
    public String question_list() {

        ArrayList list = read_converted_file();
        ArrayList list_question = new ArrayList();
        ArrayList count_first_col = new ArrayList();
        ArrayList count_question = new ArrayList();
        String list_out = "";

        int m = 0;
        int count = 0;
        for (int n = 0; n < list.size(); n = m) {
            for (int k = 0; k < list.size(); k++) {
                if (((String[]) list.get(n))[0].equals(((String[]) list.get(k))[0])) {
                    count++;
                    m++;
                }
                if ((n == (list.size() - 1)) && (n == m)) {
                    m++;
                }
            }
            count_first_col.add(((String[]) list.get(n))[0]);
            count_question.add(count);
            count = 0;
        }

        int z = 0;
        for (int i = 0; i < count_first_col.size(); i++) {

            list_question.add("<grouping>\n"
                    + "<instruction>"
                    + count_first_col.get(i)
                    + "</instruction>\n"
                    + "<borders>"
                    + "0"
                    + "</borders>\n"
                    + "<rows>"
                    + count_question.get(i).toString()
                    + "</rows>\n"
                    + "<cols>"
                    + "1"
                    + "</cols>\n");

            for (int j = 0; j < Integer.parseInt(count_question.get(i).toString()); j++) {

                // Mount initial area for question
                list_question.add("<question>\n"
                        + "<row>"
                        + j
                        + "</row>\n"
                        + "<col>"
                        + "0"
                        + "</col>\n"
                        + "<description>"
                        + ((String[]) list.get(z))[1]
                        + "</description>\n"
                        + "<label>"
                        + ((String[]) list.get(z))[2]
                        + "</label>\n");

                String option = ((String[]) list.get(z))[3].trim();

                if (option.equals("texto")) {

                    list_question.add("<type>"
                            + "TEXT"
                            + "</type>\n");
                }

                if (option.equals("area_texto")) {

                    list_question.add("<type>"
                            + "TEXTAREA"
                            + "</type>\n");
                }

                if (option.equals("numerico")) {

                    list_question.add("<type>"
                            + "NUMBER"
                            + "</type>\n");
                }

//                if (option.equals("numerico")) {
//
//                        list_question.add("<min>"
//                                + "0"
//                                + "</min>\n"
//                                + "<max>"
//                                + ((String[]) list.get(z))[4]
//                                + "</max>\n");
//                }

                if (option.equals("data")) {

                    list_question.add("<type>"
                            + "DATE"
                            + "</type>\n");
                }

                if (option.equals("hora")) {

                    list_question.add("<type>"
                            + "TIME"
                            + "</type>\n");
                }

                if (option.equals("nota")) {

                    list_question.add("<type>"
                            + "NOTE"
                            + "</type>\n");
                }

                if (option.equals("uma_opção")) {

                    list_question.add("<type>"
                            + "SELECT"
                            + "</type>\n"
                            + "<horizontalDisplay>"
                            + "true"
                            + "</horizontalDisplay>\n");
                }

                if (option.equals("uma_opção")) {

                    String[] split_alternatives = ((String[]) list.get(z))[4].split(";");

                    for (int l = 0; l < split_alternatives.length; l++) {
                        list_question.add("<alternative>\n"
                                + "<description>"
                                + split_alternatives[l]
                                + "</description>\n"
                                + "<value>"
                                + l
                                + "</value>\n"
                                + "<checked>"
                                + "false"
                                + "</checked>\n"
                                + "</alternative>\n");
                    }
                }

                if (option.equals("multiplas_opções")) {

                    list_question.add("<type>"
                            + "CHECK"
                            + "</type>\n"
                            + "<horizontalDisplay>"
                            + "true"
                            + "</horizontalDisplay>\n");
                }

                if (option.equals("multiplas_opções")) {

                    String[] split_alternatives = ((String[]) list.get(z))[4].split(";");

                    for (int l = 0; l < split_alternatives.length; l++) {
                        list_question.add("<alternative>\n"
                                + "<description>"
                                + split_alternatives[l]
                                + "</description>\n"
                                + "<value>"
                                + l
                                + "</value>\n"
                                + "<checked>"
                                + "false"
                                + "</checked>\n"
                                + "</alternative>\n");
                    }
                }

                if (option.equals("opções")) {

                    list_question.add("<type>"
                            + "RADIO"
                            + "</type>\n"
                            + "<horizontalDisplay>"
                            + "true"
                            + "</horizontalDisplay>\n");
                }

                if (option.equals("opções")) {

                    String[] split_alternatives = ((String[]) list.get(z))[4].split(";");

                    for (int l = 0; l < split_alternatives.length; l++) {
                        list_question.add("<alternative>\n"
                                + "<description>"
                                + split_alternatives[l]
                                + "</description>\n"
                                + "<value>"
                                + l
                                + "</value>\n"
                                + "<checked>"
                                + "false"
                                + "</checked>\n"
                                + "</alternative>\n");
                    }
                }

                z++;
                list_question.add("</question>\n");

            }
            list_question.add("</grouping>\n");

        }

        for (int i = 0; i < list_question.size(); i++) {

            //    System.out.println(list_question.get(i));
            list_out += list_question.get(i);
        }


        return list_out;

    }

    /*
     * With method question_list, create_XML can create complete XML file
     */
    public void create_XML() {

        ArrayList list = read_converted_file();
        String[] name = file_name.split("\\.csv");

        try {
//            FileWriter in = new FileWriter(System.getProperty("user.home") + "/"+ name[0] +"_out.xml");
            FileWriter in = new FileWriter("./" + name[0] + "_out.xml");
            in.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<!DOCTYPE form SYSTEM \"CRF.dtd\">\n"
                    + "<form>\n"
                    + "<name>"
                    + name[0]
                    + "</name>\n"
                    + question_list()
                    + "</form>");

            in.close();

        } catch (Exception e) {

            System.out.print("Erro ao criar o arquivo. Informe o separador:\n"
                    + " -t  separado por tabulaçao.\n"
                    + " -v  separado por virgula.\n"
                    + " -p  separado por pipe.\n"
                    + " -s  separador definido pelo usuario.\n"
                    + "exemplo: java -jar convert -p arquivo.csv \n");
            System.out.println(e);
        }

    }

    public static void main(String[] args) {


        if (args.length == 3) {

            convert c = new convert(args[0], args[1], args[2]);
            c.create_XML();
        }
            
        if (args.length == 2) {
            
            convert c = new convert(args[0], args[1]);
            c.create_XML();
        }

    }
}
