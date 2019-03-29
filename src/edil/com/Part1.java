package edil.com;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Part1 {
    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);


    }

    public static void main(String[] args) throws IOException {

        // map for char & int
        Map<Character,Integer> charNums = new HashMap<Character, Integer>();

        //map for char & double
        Map<Character,Double> charDouble = new HashMap<Character, Double>();


        //open the input file, save as a string
        String s = readFile("/Users/edil/Desktop/text.txt", StandardCharsets.UTF_8);

        //get the length of the string
        int n = s.length();

        //read and count each character from string
        for (int i = 0; i < n; i++) {

            char c = s.charAt(i);

            if(!charNums.containsKey(c)){

                charNums.put(c,1);
            }else{

                charNums.put(c, charNums.get(c) + 1);
            }
        }


        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        for (Map.Entry<Character, Integer> entry : charNums.entrySet()) {
            char c = entry.getKey();
            double num = entry.getValue();
            charDouble.put(c, Double.valueOf(df.format(num * 1.0/n)));
        }


        for (Map.Entry<Character, Double> entry : charDouble.entrySet()) {
            char c = entry.getKey();
            double num = entry.getValue();
            System.out.println(c+": " + num);
        }

//        System.out.println(charDouble);

    }
}
