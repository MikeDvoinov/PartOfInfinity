import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Михаил on 01.10.2016.
 */
public class Seq2 {


    public static void main(String[] args) {

        ArrayList<String> list = inputData();//просим ввести данные и создаем из них список
        processingList(list); //обрабатываем список
    }


    public static void processingEntry(String entry){
        for (int patternLength = 1; patternLength <= entry.length(); patternLength++){
            ArrayList<String> answers = new ArrayList<String>();

            boolean tru=true;
            boolean matchEntry=false;

            for (int digitsCounter = 0; digitsCounter < patternLength; digitsCounter++){
                String pattern="";

                if ( !(entry.substring(digitsCounter,digitsCounter+1).equals("0"))){
                    if (digitsCounter+patternLength <= entry.length()){
                        pattern = entry.substring(digitsCounter, digitsCounter+patternLength);
                    }
                    else{
                        pattern = extractPattern(entry,patternLength,digitsCounter);
                    }
                    String matchString=pattern;
                    String counter=pattern;
                    if (digitsCounter==0){
                        while (tru){
                            if (entry.length()>matchString.length()){
                                if (matchString.equals(entry.substring(digitsCounter,matchString.length()))){
                                    tru=true;
                                }
                                else tru=false;
                            }
                            else if (entry.length()==matchString.length()){
                                if (entry.equals(matchString)){
                                    answers.add(pattern);
                                    tru=false;
                                    matchEntry=true;
                                }
                                else tru=false;
                            }
                            else if (entry.length() < matchString.length()){

                                String s1=matchString.substring(0,entry.length());
                                String s2 = entry.substring(entry.length()-counter.length());
                                if (entry.equals(matchString.substring(0,entry.length()))&& entry.substring(entry.length()-counter.length()).equals(counter) ){
                                    answers.add(pattern);
                                    tru=false;
                                    matchEntry=true;
                                }
                                else if (entry.equals(matchString.substring(digitsCounter,entry.length()))){
                                    answers.add(counter);
                                    tru=false;
                                    matchEntry=true;
                                }
                                else tru=false;
                            }
                            counter=addOne(counter);
                            matchString=matchString+counter;
                        }
                    }
                    else{
                        tru=true;
                        while (tru){
                            if (patternLength>entry.length()/2 && patternLength!=entry.length()/2+1) {//если паттерн длинее половины ентри, будем брать числа слева от начала паттерна
                                if (matchString.length()==pattern.length()){
                                    counter=minusOne(counter);
                                    matchString=counter.substring(counter.length()-digitsCounter)+matchString;
                                    counter=pattern;
                                }
                                else{
                                    counter=addOne(counter);
                                    matchString=matchString+counter;
                                }
                                if (entry.length()>matchString.length()){
                                    if (matchString.equals(entry.substring(0,matchString.length()))){
                                        tru=true;
                                    }
                                    else tru=false;
                                }
                                else if (entry.length()==matchString.length()){
                                    if (entry.equals(matchString)){
                                        answers.add(pattern);
                                        tru=false;
                                        matchEntry=true;
                                    }
                                    else tru=false;
                                }
                                else if (entry.length() < matchString.length()){
                                    if (entry.equals(matchString.substring(0,entry.length()))){
                                        answers.add(counter);
                                        tru=false;
                                        matchEntry=true;
                                    }
                                    else tru=false;
                                }
                            }
                            else{//если паттерн короче половины ентри или равен ентри, будем просто вычитать и накладывать
                                if (matchString.length()==pattern.length()){
                                    counter=minusOne(counter);
                                    matchString=counter.substring(counter.length()-digitsCounter)+matchString;
                                    counter=pattern;
                                }
                                else{
                                    counter=addOne(counter);
                                    matchString=matchString+counter;
                                }

                            }
                            if (entry.length()>matchString.length()){
                                if (matchString.equals(entry.substring(0,matchString.length()))){
                                    tru=true;
                                }
                                else tru=false;
                            }
                            else if (entry.length()==matchString.length()){
                                if (entry.equals(matchString)){
                                    answers.add(counter);
                                    tru=false;
                                    matchEntry=true;
                                }
                                else tru=false;
                            }
                            else if (entry.length() < matchString.length()){
                                if (entry.equals(matchString.substring(0,entry.length()))){
                                    answers.add(counter);
                                    tru=false;
                                    matchEntry=true;
                                }
                                else tru=false;
                            }
                        }
                    }

                }

            }

            if (matchEntry){
                patternLength=entry.length()+1;
                System.out.println(getMinimumAnswerFromList(answers));
            }
        }


    }

    public static String extractPattern(String entry, int patternLength, int digitsCounter){
        String patternStart = entry.substring(digitsCounter,entry.length());
        String patternLeftPart = ""+1+entry.substring(0,digitsCounter);
        patternLeftPart = addOne(patternLeftPart);

        String returnString = patternStart+patternLeftPart.substring(patternLeftPart.length()-(patternLength-patternStart.length()));

        return returnString;
    }

    public static String getMinimumAnswerFromList(ArrayList<String> list){

        BigInteger minimum = new BigInteger(list.get(0));
        BigInteger temp;

        for (int i = 0; i < list.size(); i++) {
            temp=new BigInteger(list.get(i));
            minimum = temp.min(minimum);
        }

        return minimum.toString();
    }

    public static String addOne(String partOfEntry)    {
        BigInteger s = new BigInteger(partOfEntry);
        s = s.add(BigInteger.ONE);
        return s.toString();
    }

    public static String minusOne(String partOfEntry)    {
        BigInteger s = new BigInteger(partOfEntry);
        s = s.subtract(BigInteger.ONE);
        return s.toString();
    }

    public static void processingList(ArrayList<String> list){
        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                try{
                    String listEntry = list.get(i);
                    if (listEntry.length()>50) throw new Exception();
                    processingEntry(list.get(i));
                }
                catch (Exception e){
                    System.out.println("Эта последовательность не удовлетворяет условиям для входных данных и не может быть протестирована");
                }
            }
        }
        else System.out.println("Список пуст, обрабатывать нечего");
    }

    public static ArrayList<String> inputData() {
        ArrayList<String> list = new ArrayList<String>();
        boolean inputTrue=true;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите последовательности цифр для поиска (до 50 символов) или end для завершения ввода");

        try {
            while (inputTrue) {
                String string = reader.readLine();
                if (string.equals("end")) break;
                list.add(string);
            }
            reader.close();
        }
        catch (IOException e) {
            System.out.println("Ошибка ввода: "+ e.getMessage());
        }
        return list;
    }
}

